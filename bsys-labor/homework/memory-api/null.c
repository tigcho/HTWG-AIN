#include <stdio.h>
#include <stdlib.h>

int main() {
    int *n = (int*) malloc(sizeof(int));
    n = NULL;
    int m = *n;
    printf("%d", m);
    return 0;
}