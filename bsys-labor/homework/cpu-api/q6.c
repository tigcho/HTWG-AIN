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
        printf("parent return value: %d\n", waitpid(-1, NULL, 0));
    } else {
        // parent goes down this path (original process)
        int wc = waitpid(-1, NULL, 0);
	    assert(wc >= 0);

        printf("parent return value: %d\n", wc);
    }
    return 0;
}


/*
    waitpid() wartet im Normalfall nur auf terminierte Prozesse.
    kann aber angepasst werden. 
    Mit waitpid() kann angepasst werden, auf welchen Prozess gewartet werden soll
    und in welchem Fall waitpid() returnen soll.
    wait() ist nur eine Kurzschreibweise.
    (wait(&wstatus) == waitpid(-1, &wstatus, 0))


*/
