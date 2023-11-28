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
        close(STDOUT_FILENO);
        printf("Hello, I am a child.\n");
    } else {
        // parent goes down this path (original process)
        //int wc = wait(NULL);
	    //assert(wc >= 0);
        printf("Go to your room, I am your parent!\n");    
    }
    return 0;
}


/*
    Nachdem der Child-Prozess close() aufruft, wird printf nicht mehr in die Standardausgabe geschrieben.
    Der Parent-Prozess ist davon nicht betroffen.
*/
