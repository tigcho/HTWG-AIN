#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <string.h> 

int main() {
    int file = open("ex2.txt", O_CREAT | O_RDWR, S_IRWXU);
    if (file < 0) {
        fprintf(stderr, "Failed to open file\n");
        exit(1);
    }
    
    int rc = fork();
    if (rc < 0) {
        fprintf(stderr, "fork failed\n");
        exit(1);
    
    } else if (rc == 0) {
        printf("Child process\n");
        char *child = "Child process\n";
        write(file, child, strlen(child));

    } else {
        printf("Parent process\n");
        char *parent = "Parent process\n";
        write(file, parent, strlen(parent));
    }
    
    close(file);
    return 0;
}
