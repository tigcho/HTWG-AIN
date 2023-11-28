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
        execlp("/bin/ls", "-a", NULL);        
    } else {
        // parent goes down this path (original process)
        int wc = wait(NULL);
	    assert(wc >= 0);

        execl("/bin/ls", "-a", NULL);
    }
    return 0;
}


/*
    Die verschiedenen exec() Funktionen unterscheiden sich,
    indem sie verschiedene Arten von Parametern für die Parameter
    der auszuführenden Befehls benutzen.
*/
