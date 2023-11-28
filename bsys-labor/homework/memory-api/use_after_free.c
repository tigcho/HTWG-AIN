#include <stdio.h>
#include <stdlib.h>

int main() {
    int *array = (int*) malloc(100 * sizeof(int));

    array[0] = 0;

    free(array);

    printf("%d\n", array[0]);
    return 0;
}