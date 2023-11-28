#include <stdlib.h>

int main() {
    int *array = (int*) malloc(100 * sizeof(int));

    free(array[52]);
    free(array);
    return 0;
}