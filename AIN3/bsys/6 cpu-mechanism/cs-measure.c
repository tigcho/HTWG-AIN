#define _GNU_SOURCE
#include <fcntl.h>
#include <sched.h>
#include <stdio.h>
#include <stdlib.h>
#include <sys/time.h>
#include <sys/wait.h>
#include <unistd.h>

#define ITER 1000000

int main() {
    // Measuring the system call
    int fd = open("./1.txt", O_CREAT | O_RDONLY, S_IRWXU);

    struct timeval start, end;
    gettimeofday(&start, NULL);
    for (size_t i = 0; i < ITER; i++) {
        read(fd, NULL, 0);
    }
    gettimeofday(&end, NULL);
    printf("Time for system call: %f microseconds\n", (double)(end.tv_sec * 1000000 + end.tv_usec - start.tv_sec * 1000000 - start.tv_usec) / ITER);
    close(fd);

    // Measuring the context switch
    cpu_set_t set;
    CPU_ZERO(&set);
    CPU_SET(sched_getcpu(), &set);
    if (sched_setaffinity(0, sizeof(cpu_set_t), &set) == -1) {
        perror("sched_setaffinity");
        exit(EXIT_FAILURE);
    }

    int pipe1[2], pipe2[2];

    if (pipe(pipe1) == -1 || pipe(pipe2) == -1) {
        perror("pipe");
        exit(EXIT_FAILURE);
    }

    int cpid = fork();
    if (cpid == -1) {
        perror("fork");
        exit(EXIT_FAILURE);
    } 
    else if (cpid == 0) { // child
        char buffer = '0';

        close(pipe1[1]);
        close(pipe2[0]);

        for (int i = 0; i < ITER; i++) {
            if (read(pipe1[0], &buffer, 1) == -1) {
                perror("child read");
                exit(EXIT_FAILURE);
            }
            
            if (write(pipe2[1], &buffer, 1) == -1) {
                perror("child write");
                exit(EXIT_FAILURE);
            }
        }

        exit(EXIT_SUCCESS);
    } 
    else { // parent
        gettimeofday(&start, NULL);
        char buffer = '0';

        close(pipe1[0]);
        close(pipe2[1]);

        for (int i = 0; i < ITER; i++) {
            if (write(pipe1[1], &buffer, 1) == -1) {
                perror("parent write");
                exit(EXIT_FAILURE);
            }

            if (read(pipe2[0], &buffer, 1) == -1) {
                perror("parent read");
                exit(EXIT_FAILURE);
            }
        }

        gettimeofday(&end, NULL);
        
        wait(NULL);
        printf("Time for context switch: %f microseconds\n", (double)(end.tv_sec * 1000000 + end.tv_usec - start.tv_sec * 1000000 - start.tv_usec) / ITER / 2);

        exit(EXIT_SUCCESS);
    }

    return 0;
}
