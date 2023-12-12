#include "fileinfo.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <linux/limits.h>
#include <sys/stat.h>
#include <dirent.h>
#include <unistd.h>
#include <errno.h>

/* TODO: Schreiben Sie dafür eine private Hilfsfunktion list_directory,
    die das Verzeichnis mit Hilfe der POSIX-Funktionen opendir, readdir und closedir liest */
static fileinfo* list_dir(char* dir)
{
    DIR* d = opendir(dir);
    if (d == NULL)
    {
        return NULL;
    }

    fileinfo* files = NULL;
    fileinfo* dirs = NULL;

    /* TODO: Die Funktion muss das aktuelle Arbeitsverzeichis mit der POSIX-Funktion
        chdir auf den den Namen des bearbeiteten Verzeichnisses setzen... */
    chdir(dir);

    struct dirent* e;
    while ((e = readdir(d)) != NULL)
    {

/* TODO: Achten Sie darauf, die Verzeichniseinträge mit Namen "." und
    ".." zu überspringen, um eine endlose Rekursion zu verhindern */
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

/* TODO: ...und vor dem return mit ".." wieder zurücksetzen */
    chdir("..");
    return head;
}


fileinfo* fileinfo_create(char* path)
{

/* TODO: Im Fehlerfall soll die Funktion NULL zurückgeben. Ist der
    Fehler ein zu langer Dateinamen, soll außerdem die globale
    POSIX-Variable errno auf ENAMETOOLONG gesetzt werden */
    if (strlen(path) > NAME_MAX)
    {
        errno = ENAMETOOLONG;
        return NULL;
    }

    struct stat file;

/* TODO: Verwenden Sie die POSIX-Funktion lstat, um die Art
    der Datei und im Falle einer regulären Datei deren Größe
    in Byte festzustellen */
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

/* TODO: Im Falle eines Verzeichnisses müssen Sie eine Liste
    von fileinfo-Strukturen für alle Dateien im Verzeichnis erstellen */
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

/* TODO: zwei Eingabeparameter haben, einen für den Dateinamen und einen für die Dateigröße */
void print_regular(const char* name, int size)
{

    /* TODO: ie beiden Werte sollen auf die Standardausgabe geschrieben werden */
    printf("%s (regular, %d Byte)\n", name, size);
}


/* TODO: soll drei Eingabeparameter haben, einen für den Pfad bis zum auszugebenden
    Verzeichnis (beim Aufruf in fileinfo_print mit "" belegen), einen für Dateinamen
    des auszugebenden Verzeichnisse und einen für die Liste der Unterverzeichnisse */
static void print_directory(char const* path, char const* filename, fileinfo* files) {
    if (strcmp(path, "") != 0) {
        printf("\n%s/%s:\n", path, filename);
    }
    else {
        printf("\n%s:\n", filename);
    }

    if (files) {
        if (files->type == filetype_directory) {
            printf("%s (directory)\n", files->name);
            print_directory(filename, files->name, files->list);
        }
        else {
            fileinfo_print(files);
        }

        while (files->next != NULL) {
            if (files->next->type == filetype_directory) {
                printf("%s (directory)\n", files->next->name);
                print_directory(filename, files->next->name, files->next->list);
            }
            else {
                fileinfo_print(files->next);
            }
            files = files->next;
        }
    }
}


/* TODO: soll einen Eingabeparameter für den Dateinamen haben */
void print_other(const char* name)
{
    printf("%s (other)\n", name);
}

/* TODO: Sie soll je nach Dateityp eine von drei privaten Hilfsfunktionen
    mit den jeweiligen Werten aufrufen */
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


/* TODO: soll einen Eingabeparameter für einen Zeiger auf eine fileinfo-Struktur
    haben und keinen Rückgabewert. Sie soll den Speicher der übergebenen Struktur und im
    Falle des Dateityps Verzeichnis auch der Strukturen aller Unterverzeichnisse freigeben*/
void fileinfo_destroy(fileinfo* info) {
    if (info->type == filetype_directory) {
        fileinfo* current = info->list;

        while (current != NULL)
        {
            fileinfo *next = current->next;

            if (current->type == filetype_directory) {
                fileinfo_destroy(current);
            }
            else {
                free(current);
            }

            current = next;
        }
    }
    free(info);
}

