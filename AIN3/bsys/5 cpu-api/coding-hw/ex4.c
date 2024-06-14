#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>

int main()
{
    int rc = fork();
    if (rc < 0)
    {
        fprintf(stderr, "fork failed\n");
        exit(1);
    }
    else if (rc == 0)
    {
        printf("Child process\n");
        char *args[] = {"/bin/ls", "ls", "-l", NULL};
        execvp("ls", args);
    }
    else
    {
        int wc = waitpid(rc, NULL, 0);
        printf("Parent process\n");
    }
    return 0;
}
