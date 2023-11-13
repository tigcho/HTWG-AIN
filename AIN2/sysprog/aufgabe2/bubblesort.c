#include <stdio.h>
#include <stdlib.h> // malloc und free
#include <time.h>

/*
 * Kopf des Hauptprogramms muss int main(int argc, char *argv[]) lauten.
 * argc der C-Ersatz für die Instanzvariable args.length
 * argc muss != 2 sein, sonst zu wenig/viele Argumente
 * return 1 bedeutet, der Fehler tritt auf (ist wahr)
 */
int main(int argc, char *argv[])
{
    if (argc != 2)
    {
        fprintf(stderr, "Aufruf: ./Bubblesort Anzahl\n");
        return 1;
    }

    // Java-Ausdruck Integer.parseInt(args[0]) ist der C-Ausdruck atoi(argv[1]).
    int n = atoi(argv[1]);

    /*
     * Reservieren des Speichers
     * Typ *Zeigername = (Typ*) malloc(sizeof (Typ));
     * if (Zeigername == NULL) { ... Fehlerbehandlung ... }
     */
    int *a = (int*) malloc(n * sizeof(int));

    // Fehlerbehandlung nach der Speicherreservierung
    if (a == NULL)
    {
        fprintf(stderr, "Speicherreservierung fehlgeschlagen\n");
        return 1;
    }

    /*
     * wenn die Adresse des i-ten Elements als Zahl eingescannt wurde -> 1
     * n ist die Anzahl an eingegeben Zahlen
     */
    printf("Bitte %d ganze Zahlen eingeben: ", n);
    int k = 0;
    for (int i = 0; i < n; i++)
    {
        if (scanf("%d", &a[i]) == 1)
        {
            ++k;
        }
    }
    /*
     * Den Zufallsgenerator der C-Standardbibliothek initialisieren Sie mit
     * srand((unsigned int) time(NULL)) und die nächste Zufallszahl
     * erhalten Sie mit dem Funktionsaufruf rand().
     */
    srand((unsigned int) time(NULL));
    for (int i = k; i < n; i++)
    {
        a[i] = rand();
        printf("%d\n", a[i]);
    }

    for (int i = n; i > 1; --i)
    {
        // groessten Wert nach hinten schieben
        for (int j = 0; j < i - 1; ++j)
        {
            if (a[j] > a[j + 1])
            {
                // Werte tauschen
                int tmp = a[j + 1];
                a[j + 1] = a[j];
                a[j] = tmp;
            }
        }
    }

    printf("Sortierte Zahlenfolge:\n");
    for (int i = 0; i < n; i++)
    {
        printf("%d\n", a[i]);
    }

    // Freigabe des Speichers
    free(a);
    return 0;
}