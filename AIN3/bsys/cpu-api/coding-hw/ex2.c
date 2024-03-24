#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>

int main() {
    int file = open("ex2.txt", O_CREAT);
    int rc = fork();
    
    if (rc < 0) {
	fprintf(stderr, "fork failed\n");
    
    } else if (rc == 0) {
	char *child = "Child process\n";
	write(file, child, sizeof(child));
    
    } else {
	char *parent = "Parent process\n";
	write(file, parent, sizeof(parent));
    }
    close(file);
    return 0;
}
