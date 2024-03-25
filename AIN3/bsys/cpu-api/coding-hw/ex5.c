#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>

int main() {
    int rc = fork();

    if (rc < 0) {
	fprintf(stderr, "fork failed\n");
	exit(1);
	
    } else if (rc == 0) {
	printf("Child process\n");
	int wc = wait(NULL);
	printf("wait() returns: %d\n", wc);

    } else {
	//int wc = wait(NULL);
	printf("Parent process\n");
	//printf("wait() returns: %d\n", wc);
    }
    return 0;
}
