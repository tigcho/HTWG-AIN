#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

void bubblesort(void *ptr, size_t count, size_t size,
    int (*comp)(const void*, const void*));


int main(int argc, char *argv[]) {
    //--------------------------------------------- Arraygroesse bestimmen
    if (argc != 2) {
        fprintf(stderr, "Aufruf: ./stringsort Anzahl\n");
        return 1;
    }

    /* TODO:
        Bestimmen Sie die Array-Größe n wie in Aufgabe 2 mit
        der Funktion atoi aus dem Kommandozeilenargument argv[1].
     */
    int n = atoi(argv[1]);
    if (n < 1) {
        fprintf(stderr, "Anzahl muss mindestens 1 sein\n");
        return 1;
    }
    /* TODO:
        Geben Sie als Länge m des Speicherstücks die Länge
        des Strings argv[1] inklusive Stringendezeichen '\0' an.
    */
    int m = strlen(argv[1]) + 1;

    /* TODO:
        Verwenden Sie die Bibliotheksfunktion malloc zum
        Reservieren des Speichers für das Array.
     */
    char* a = malloc(n * m * sizeof(char));
    if (a == NULL) {
        fprintf(stderr, "Memory Allocation Error!\n");
        return 1;
    }

    //--------------------------------------------------- Strings wuerfeln
    /* TODO:
        Verwenden Sie wie in Aufgabe 2 die Bibliotheksfunktionen
        srand und rand zum Würfeln.
     */
    srand(time(NULL));

    printf("Unsortiertes Array:\n");
    for (int i = 0; i < n; ++i) {
        int r = rand() % n;
        sprintf(a + i * m, "%d", r);
        printf("%s ", a + i * m);
    }

    //-------------------------------------------------- Strings sortieren
    bubblesort(a, n, m, (int (*)(const void*, const void*)) strcmp);

    //--------------------------------------------------- Strings ausgeben
    printf("\nSortiertes Feld:\n");
    char *stringBuilderButNotReally = malloc(n * m * sizeof(char));
    if (stringBuilderButNotReally == NULL) {
        fprintf(stderr, "Memory Allocation Error!\n");
        return 1;
    }
    strcpy(stringBuilderButNotReally, a);
    for (int i = 1; i < n; ++i) {
        if (strcmp(a + i * m, a + (i - 1) * m) == 0) {
            strcat(stringBuilderButNotReally, "*");
        } else {
            strcat(stringBuilderButNotReally, " ");
            strcat(stringBuilderButNotReally, a + i * m);
        }
    }

    printf("%s\n", stringBuilderButNotReally);
    free(a);
    free(stringBuilderButNotReally);
    return 0;
}

void bubblesort(void *ptr, size_t count, size_t size,
    int (*cmp)(const void*, const void*)) {

    void* tmp = malloc(size);

    for (int i = count; i > 1; --i) {
        for (int j = 0; j < i - 1; ++j) {
            void *a = (char*) ptr + j * size;
            void *b = (char*) ptr + (j + 1) * size;

            if (cmp(a, b) > 0) {
                memcpy(tmp, b, size);
                memcpy(b, a, size);
                memcpy(a, tmp, size);
            }
        }
    }

    free(tmp);
}