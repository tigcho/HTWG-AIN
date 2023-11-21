#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

void bubblesort(char **a, int n) {
    for (int i = n; i > 1; --i) {
        // größten Wert nach hinten schieben
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
    //--------------------------------------------- Arraygröße bestimmen
    if (argc != 2) {
        fprintf(stderr, "Aufruf: ./stringsort Anzahl\n");
        return 1;
    }

    int n = atoi(argv[1]);
    if (n < 1) {
        fprintf(stderr, "Anzahl muss mindestens 1 sein\n");
        return 1;
    }

    int m = strlen(argv[1]) + 1;

    char **a = malloc(n * sizeof(char*));
    if (a == NULL) {
        fprintf(stderr, "Memory Allocation Error!\n");
        return 1;
    }

    char *stringMemory = malloc(n * m * sizeof(char));
    if (stringMemory == NULL) {
        fprintf(stderr, "Memory Allocation Error!\n");
        return 1;
    }

    //--------------------------------------------------- Strings würfeln
    srand(time(NULL));

    printf("Unsortiertes Array:\n");
    for (int i = 0; i < n; ++i) {
        a[i] = stringMemory + i * m;
        int r = rand() % n;
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

    printf("%s\n", stringBuilderButNotReally);

    free(a);
    free(stringMemory);
    free(stringBuilderButNotReally);
    return 0;
}
