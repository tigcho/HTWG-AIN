#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <fcntl.h>
#include <assert.h>
#include <sys/wait.h>

int main(int argc, char *argv[])
{
    int x = 100;
    int rc = fork();
    if (rc < 0) {
        // fork failed; exit
        fprintf(stderr, "fork failed\n");
        exit(1);
    } else if (rc == 0) {
        // child: 
        x = 200;
        printf("child: x is %d\n", x);

    } else {
        // parent goes down this path (original process)

        x = 50;
        printf("parent: x is %d\n", x);
    }
    return 0;
}


/*
    Wenn der Wert nicht verändert wird, haben child und parent denselben Wert 100.
    Die beiden Prozesse nehmen jeweils den Wert an, den sie der Variable zuweisen.
*/
