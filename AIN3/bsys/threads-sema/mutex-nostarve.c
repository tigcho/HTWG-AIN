#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <pthread.h>
#include <semaphore.h>
#include "common_threads.h"

typedef struct __ns_mutex_t {
    int guard;
    sem_t sem;
    sem_t guard_sem;
    sem_t next;
    int next_count;
} ns_mutex_t;

void ns_mutex_init(ns_mutex_t *m) {
    m->guard = 0;
    m->next_count = 0;
    Sem_init(&m->sem, 1);
    Sem_init(&m->guard_sem, 1);
    Sem_init(&m->next, 0);
}

void ns_mutex_acquire(ns_mutex_t *m) {
    Sem_wait(&m->guard_sem); 
    m->guard++;
    Sem_post(&m->guard_sem);

    Sem_wait(&m->sem); 
    Sem_wait(&m->guard_sem); 
    m->guard--;
    if (m->guard > 0) {
        m->next_count++;
        Sem_post(&m->next);
        Sem_post(&m->guard_sem); 
        Sem_wait(&m->next); 
        Sem_wait(&m->guard_sem);
        m->next_count--;
    }
    Sem_post(&m->guard_sem); 
}

void ns_mutex_release(ns_mutex_t *m) {
    Sem_wait(&m->guard_sem); 
    if (m->guard > 0) {
        Sem_post(&m->next);
    } else {
        Sem_post(&m->sem);
    }
    Sem_post(&m->guard_sem);
}

void *worker(void *arg) {
    ns_mutex_t *m = (ns_mutex_t *) arg;
    ns_mutex_acquire(m);
    printf("child\n");
    ns_mutex_release(m);
    return NULL;
}

int main(int argc, char *argv[]) {
    ns_mutex_t m;
    ns_mutex_init(&m);
    pthread_t p;
    Pthread_create(&p, NULL, worker, &m);
    ns_mutex_acquire(&m);
    printf("parent\n");
    ns_mutex_release(&m);
    Pthread_join(p, NULL);
    printf("parent: begin\n");
    printf("parent: end\n");
    return 0;
}

