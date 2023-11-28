#include <stdio.h>
#include <stdlib.h>

int main() {
    int *vec = NULL;
    int* tmp = NULL;
    int length = 0;
    int input = 0;

    while (1) {
        printf("Please enter a whole number: ");
        if (scanf("%d", &input) != 1) {
            fprintf(stderr, "Error: input is no whole number\n");
            free(vec);
            exit(1);
        }

        tmp = (int*) realloc(vec, sizeof(int));
        if (tmp == NULL) {
            fprintf(stderr, "Error: failed to allocate more memory\n");
            free(vec);
            exit(1);
        }
        vec = tmp;

        length = length + 1;
        vec[length - 1] = input;

        printf("The number %d is saved at %p\n", vec[length - 1], &vec[length - 1]);
    }
    
    free(vec);
    return 0;
}