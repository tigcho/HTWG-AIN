#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

int main(int argc, char *argv[]) {
    int x = 100;
    int rc = fork();

    if (rc < 0) {
	fprintf(stderr, "fork failed\n");

    } else if (rc == 0) {
	printf("Child process before changing x: %d\n", x);
	x = 200;
	printf("Child process after changing x: %d\n", x);

    } else {
	printf("Parent process before changing x: %d\n", x);
	x = 300;
	printf("Parent process after changing x: %d\n", x);
    }
    return 0;
}
