#include <stdlib.h>

int main() {
    int *array = (int*) malloc(100 * sizeof(int));

    array[100] = 0;

    free(array);
    return 0;
}