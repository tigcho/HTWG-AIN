#include <stdio.h>
#include <stdlib.h>

int main() {
    int *arr = malloc(10 * sizeof(int));
    //free(arr);
    arr[50] = 10;
    int *funny = &arr[50];
    printf("%d\n", arr[50]);
    return 0;
}
