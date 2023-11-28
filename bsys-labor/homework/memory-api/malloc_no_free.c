#include <stdio.h>
#include <stdlib.h>

int main() {
    int *n = (int*) malloc(sizeof(int));
    printf("%p\n", n);
    //free(n);
    return 0;
}