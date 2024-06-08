#include <stdio.h>
#include <sys/time.h>
#include <pthread.h>
#include <stdlib.h>

int counter = 0;
pthread_mutex_t lock;

void *thread()
{
    for (int i = 0; i < 1000000; i++)
    {
        pthread_mutex_lock(&lock);
        counter++;
        pthread_mutex_unlock(&lock);
    }
    pthread_exit(0);
}

void count(int threads)
{
    struct timeval start, end;
    pthread_mutex_init(&lock, NULL);
    pthread_t *tid = malloc(threads * sizeof(pthread_t));

    gettimeofday(&start, NULL);

    for (int i = 0; i < threads; i++)
    {
        pthread_create(&tid[i], NULL, &thread, NULL);
    }

    for (int i = 0; i < threads; i++)
    {
        pthread_join(tid[i], NULL);
    }

    gettimeofday(&end, NULL);
    
    printf("Number of threads: %d\n", threads);
    printf("Counter value: %d\n", counter);
    printf("Time taken: %ld microseconds\n\n", ((end.tv_sec * 1000000 + end.tv_usec) - (start.tv_sec * 1000000 + start.tv_usec)));

    free(tid);
}

int main()
{
    for (int i = 0; i < 5; i++)
    {
        count(1 << i);
    }
}
