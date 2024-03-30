#include <sys/time.h>
#include <stdio.h>
#include <unistd.h>

#define ITER 1000000

int main() {
    struct timeval tv;
    gettimeofday(&tv, NULL);
    double start = tv.tv_sec + (tv.tv_usec / 1000000.0);
    for (int i = 0; i < ITER; i++)
    {
        read(0, NULL, 0);
    }
    gettimeofday(&tv, NULL);
    double end = tv.tv_sec + (tv.tv_usec / 1000000.0);
    printf("Time taken: %f s for %d syscalls\n", end - start, ITER);
    printf("Time taken: %f ms (%f μs) for 1 context switch\n",
        1000 * (end - start) / (double)ITER,
        1000000 * (end - start) / (double)ITER);

    return 0;
}
