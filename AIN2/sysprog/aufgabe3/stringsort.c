#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

void bubblesort(char **a, int n) {
    for (int i = n; i > 1; --i) {
        // groessten Wert nach hinten schieben
        for (int j = 0; j < i - 1; ++j) {
            if (strcmp(a[j], a[j + 1]) > 0) {
                // Werte tauschen
                char *tmp = a[j + 1];
                a[j + 1] = a[j];
                a[j] = tmp;
            }
        }
    }
} // bubblesort

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
    char** a = malloc(n * sizeof(char*));
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

        /* TODO:
            Vor jedem Aufruf von sprintf müssen Sie mit malloc
            ein eigenes Speicherstück für den jeweils zu
            erstellenden String reservieren.
         */
        a[i] = malloc(m * sizeof (char));
        if (a[i] == NULL) {
            fprintf(stderr, "Memory Allocation Error!\n");
            return 1;
        }

        /* TODO:
            Mit der Bibliotheksfunktion sprintf können
            Sie die gewürfelten Zahlen in Strings umwandeln.
         */
        sprintf(a[i], "%d", r);
        printf("%s ", a[i]);
    }

    //-------------------------------------------------- Strings sortieren
    bubblesort(a, n);

    //--------------------------------------------------- Strings ausgeben
    printf("\nSortiertes Feld:\n");

    char *stringBuilderButNotReally = malloc(n * m * sizeof(char));
    if (stringBuilderButNotReally == NULL) {
        fprintf(stderr, "Memory Allocation Error!\n");
        return 1;
    }

    strcpy(stringBuilderButNotReally, a[0]);
    for (int i = 1; i < n; ++i) {
        if (strcmp(a[i], a[i - 1]) == 0) {
            strcat(stringBuilderButNotReally, "*");
        } else {
            strcat(stringBuilderButNotReally, " ");
            strcat(stringBuilderButNotReally, a[i]);
        }
    }

    /* TODO:
        Schreiben Sie wie in der Java-Vorlage das
        sortierte Array als einen einzigen String auf die Standardausgabe
     */
    printf("%s\n", stringBuilderButNotReally);

    for (int i = 0; i < n; ++i) {
        free(a[i]);
    }

    free(stringBuilderButNotReally);
    free(a);
    return 0;
} // main