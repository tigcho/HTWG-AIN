#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

int main(int argc, char *argv[]) {
    if (argc != 2) {
        fprintf(stderr, "usage: memory-user <MEGABYTES>\n");
        exit(1);
    }

    int bytes = atoi(argv[1]) * 1024 * 1024;

    int *array = (int*) malloc(bytes);

    while (1) {
        for (int i = 0; i < bytes/sizeof(array); i++) {
            array[i] = i;
        }
    }

    free(array);
    return 0;
}