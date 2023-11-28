#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <fcntl.h>
#include <assert.h>
#include <sys/wait.h>

int main(int argc, char *argv[])
{
    int rc = fork();
    if (rc < 0) {
        // fork failed; exit
        fprintf(stderr, "fork failed\n");
        exit(1);
    } else if (rc == 0) {
        // child: 
        printf("Hello.\n");
    } else {
        // parent goes down this path (original process)
        sleep(1);
        printf("Goodbye.\n");       
    }
    return 0;
}

/*
    Lässt sich leicht mit sleep(1) erreichen, weil so ein kleines
    Programm deutlich schneller beim ausführen ist als eine Sekunde.
    (Eleganter wäre wait(NULL))
*/
