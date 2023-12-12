#ifndef FILEINFO_H
#define FILEINFO_H

#include <limits.h>
#include <stdlib.h>

/* TODO: Aliasnamen fileinfo für Ihren struct-Typ */
typedef struct fileinfo fileinfo;

/* TODO: Mit der zugeordneten enum unterscheiden Sie zwischen den Dateitypen */
enum filetype {
    filetype_regular,
    filetype_directory,
    filetype_other
};

/* TODO: struct-Typ fileinfo mit eingebetteter anonymer union und zugeordnetem enum-Typ filetype */
struct fileinfo {

/* TODO: Array-Größe die symbolische Konstante NAME_MAX aus limits.h */
    char name[NAME_MAX];

/* TODO: Listenverkettung in Form eines Zeigers auf struct fileinfo
    und ein Array von Zeichen für den Dateinamen enthalten */
    enum filetype type;
    fileinfo* next;

/* TODO: Mit der eingebetteten union speichern Sie bei regulären Dateien die Dateigröße in Byte */
    union  {
        size_t size;
        fileinfo* list;
    };
};

fileinfo* fileinfo_create(char* name);
void fileinfo_print(fileinfo* info);
void fileinfo_destroy(fileinfo*);

#endif
