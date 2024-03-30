#include <sys/time.h>
#include <stdio.h>
#include <unistd.h>
#include <fcntl.h>

#define ITER 1000000

int main() {
    struct timeval tv;
   int fd = open("./1.txt", O_CREAT | O_RDONLY, S_IRWXU), nloops = 1000000;

    gettimeofday(&tv, NULL);
    double start = tv.tv_sec + (tv.tv_usec / 1000000.0);
    for (int i = 0; i < ITER; i++)
    {
      read(fd, NULL, 0);
    }
    gettimeofday(&tv, NULL);
    double end = tv.tv_sec + (tv.tv_usec / 1000000.0);
    printf("Time taken: %f s for %d syscalls\n", end - start, ITER);
    printf("Time taken: %f ms (%f μs) for 1 context switch\n",
        1000 * (end - start) / (double)ITER,
        1000000 * (end - start) / (double)ITER);

    close(fd);   return 0;
}
