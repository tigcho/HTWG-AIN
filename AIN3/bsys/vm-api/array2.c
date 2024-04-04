#include <stdio.h>
#include <stdlib.h>

int main() {
    int *arr = malloc(10 * sizeof(int));
    free(arr);
    printf("%d\n", arr[0]);
    return 0;
}

