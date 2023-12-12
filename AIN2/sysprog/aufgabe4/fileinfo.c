#include "fileinfo.h"

#include <errno.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <linux/limits.h>
#include <sys/stat.h>
#include <dirent.h>
#include <unistd.h>

static fileinfo* list_dir(char* dir)
{
    DIR* d = opendir(dir);
    if (d == NULL)
    {
        return NULL;
    }

    fileinfo* files = NULL;
    fileinfo* dirs = NULL;

    chdir(dir);

    struct dirent* e;
    while ((e = readdir(d)) != NULL)
    {
        if (strcmp(e->d_name, ".") == 0 || strcmp(e->d_name, "..") == 0)
        {
            continue;
        }

        fileinfo* f = fileinfo_create(e->d_name);
        if (!f)
        {
            continue;
        }

        if (f->type == filetype_directory)
        {
            f->next = dirs;
            dirs = f;
        }
        else
        {
            f->next = files;
            files = f;
        }
    }
    fileinfo* head;
    if (!files)
    {
        head = dirs;
    }
    else
    {
        head = files;
        while (files->next)
        {
            files = files->next;
        }
        files->next = dirs;
    }
    closedir(d);
    chdir("..");
    return head;
}

fileinfo* fileinfo_create(char* path)
{
    if (strlen(path) > NAME_MAX)
    {
        return NULL;
    }

    struct stat file;
    if (lstat(path, &file) != 0)
    {
        return NULL;
    }

    fileinfo* info = malloc(sizeof(fileinfo));
    if (info == NULL)
    {
        return NULL;
    }

    strcpy(info->name, path);
    if (S_ISDIR(file.st_mode))
    {
        info->type = filetype_directory;
        info->list = list_dir(path);
        return info;
    }
    if (S_ISREG(file.st_mode))
    {
        info->type = filetype_regular;
        info->size = file.st_size;
        return info;
    }
    info->type = filetype_other;
    return info;
}

void print_regular(const char* name, int size)
{
    printf("%s (regular, %d Byte)\n", name, size);
}

void print_directory(const char* path, const char* name, fileinfo* files)
{
    if (strcmp(path, "") != 0)
    {
        printf("\n%s/%s:\n", path, name);
    }
    else
    {
        printf("\n%s:\n", name);
    }

    if (files != NULL)
    {
        if (files->type == filetype_directory)
        {
            printf("%s (directory)\n", files->name);
            print_directory(name, files->name, files->list);
        }
        else
        {
            fileinfo_print(files);
        }

        while (files->next != NULL)
        {
            if (files->next->type == filetype_directory)
            {
                printf("%s (directory)\n", files->name);
                print_directory(name, files->name, files->list);
            }
            else
            {
                fileinfo_print(files->next);
            }
            files = files->next;
        }
    }
}

void print_other(const char* name)
{
    printf("%s (other)\n", name);
}

void fileinfo_print(fileinfo* file)
{
    if (file->type == filetype_regular)
    {
        print_regular(file->name, file->size);
    }
    else if (file->type == filetype_directory)
    {
        print_directory("", file->name, file->list);
    }
    else if (file->type == filetype_other)
    {
        print_other(file->name);
    }
}

void fileinfo_destroy(fileinfo* file)
{
    if (file->type == filetype_directory)
    {
        fileinfo* current = file->list;

        while (current)
        {
            fileinfo* next = current->next;
            if (current->type == filetype_directory)
            {
                fileinfo_destroy(current->list);
            }
            free(current);
            current = next;
        }
    }
    free(file);
}

