#include <stdio.h>
#include <unistd.h>
#include <sys/wait.h>

int main() {
    int p[2];
    pipe(p);
    int rc = fork();
    
    if (rc < 0) {
        printf("fork() failed");
    } else if (rc == 0) {
        // child process
        // write in pipe
        write(p[1], "Message\n", 8);
        close(p[1]);
    } else {
        int rc2 = fork();
        if (rc2 < 0) {
            printf("fork 2 failed");
        } else if (rc2 == 0) {
            // second child
            // read from pipe
            char buf[8];
            read(p[0], buf, 8);
            close(p[0]);
            printf("%s", buf);
        } else {
            // parent
            wait(NULL);
        }
        // parent process
        wait(NULL);
    }

    return 0;
}

