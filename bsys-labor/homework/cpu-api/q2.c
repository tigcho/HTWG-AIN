#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <fcntl.h>
#include <assert.h>
#include <sys/wait.h>

int main(int argc, char *argv[])
{
    // redirect standard output to q2.txt
    close(STDOUT_FILENO); 
    open("./q2.txt", O_CREAT|O_WRONLY|O_TRUNC, S_IRWXU);

    int rc = fork();
    if (rc < 0) {
        // fork failed; exit
        fprintf(stderr, "fork failed\n");
        exit(1);
    } else if (rc == 0) {
        // child: 
        printf("Hello, I am a child.\n");
        printf("Child: second printf\n");
    } else {
        // parent goes down this path (original process)
        //int wc = wait(NULL);
	    //assert(wc >= 0);
        printf("Go to your room, I am your parent!\n");
        printf("Parent: second printf\n");        
    }
    return 0;
}


/*
    Beide Prozesse können auf die Datei zugreifen und schreiben.
    Bei einer nicht-deterministischen Ausführung ohne wait() werden
    die Prozesse zwar in unterschiedlicher Reihenfolge ausgeführt,
    es entstehen aber keine Unleserlichen Sätze, weil die Prozesse
    nicht wild durcheinander schreiben.
*/
