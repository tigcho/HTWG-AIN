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
        //fork();
        printf("parent return value: %d\n", wait(NULL));
    } else {
        // parent goes down this path (original process)
        int wc = wait(NULL);
	    assert(wc >= 0);

        printf("parent return value: %d\n", wc);
    }
    return 0;
}


/*
    wait() gibt im Parent-Prozess die PID des Child-Prozesses zurück, 
    wenn alles geklappt hat, ansonsten -1.
    wait() im Child-Prozess gibt -1 zurück, wenn kein neuer Fork erstellt wird,
    ansonsten wird der Child-Prozess in diesem Zusammenhang zum Parent-Prozess.
    wait() hat einen Ausgabeparamenter, indem der Status gespeichert werden kann.
*/
