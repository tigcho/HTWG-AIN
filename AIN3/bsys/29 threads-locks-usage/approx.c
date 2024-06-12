#include <stdio.h>
#include <sys/time.h>
#include <pthread.h>
#include <stdlib.h>

#define NUM_CPUS 8

typedef struct __counter_t
{
    int global;
    pthread_mutex_t glock;
    int local[NUM_CPUS];
    pthread_mutex_t llock[NUM_CPUS];
    int threshold;
} counter_t;

typedef struct __args_t
{
    counter_t *c;
    int amt;
} args_t;

void init(counter_t *c, int threshold)
{
    c->threshold = threshold;
    c->global = 0;
    pthread_mutex_init(&c->glock, NULL);
    for (int i = 0; i < NUM_CPUS; i++)
    {
        c->local[i] = 0;
        pthread_mutex_init(&c->llock[i], NULL);
    }
}

void update(counter_t *c, int threadID, int amt)
{
    int thread = threadID % NUM_CPUS;
    pthread_mutex_lock(&c->llock[thread]);
    c->local[thread] += amt;
    if (c->local[thread] >= c->threshold)
    {
        pthread_mutex_lock(&c->glock);
        c->global += c->local[thread];
        pthread_mutex_unlock(&c->glock);
        c->local[thread] = 0;
    }
    pthread_mutex_unlock(&c->llock[thread]);
}

int get(counter_t *c)
{
    pthread_mutex_lock(&c->glock);
    int val = c->global;
    pthread_mutex_unlock(&c->glock);
    return val;
}

void *worker(void *arg)
{
    args_t *args = (args_t *)arg;
    pthread_t thread_ID = pthread_self();
    for (int i = 0; i < 1000000; i++)
    {
        update(args->c, (int)thread_ID, args->amt);
    }
    pthread_exit(0);
}

void count(int i, int j)
{
    int threshold = 1 << i;
    int num_threads = 1 << j;


    counter_t c;
    init(&c, threshold);

    struct timeval start, end;
    gettimeofday(&start, NULL);

    pthread_t *tid = malloc(num_threads * sizeof(pthread_t));

    args_t *args = malloc(num_threads * sizeof(args_t));

    for (int i = 0; i < num_threads; i++)
    {
        args[i].c = &c;
        args[i].amt = 1;
        pthread_create(&tid[i], NULL, &worker, &args[i]);
    }

    for (int i = 0; i < num_threads; i++)
    {
        pthread_join(tid[i], NULL);
    }

    gettimeofday(&end, NULL);
    printf("\nThreshold: %d, Threads: %d\n", threshold, num_threads);
    printf("Counter value: %d\n", get(&c));
    printf("Time taken: %ld microseconds\n", (end.tv_sec * 1000000 + end.tv_usec) - (start.tv_sec * 1000000 + start.tv_usec));

    free(args);
    free(tid);
}

int main()
{
    for (int i = 0; i < 4; i++)
    {
        for (int j = 1; j <= 4; j++)
        {
            count(i, j);
        }
    }
}
