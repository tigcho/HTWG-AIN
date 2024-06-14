#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include "common_threads.h"

//
// Your code goes in the structure and functions below
//

typedef struct __rwlock_t {
    sem_t lock;
	sem_t writelock;
	int readers;
	int writers;
	int waiting_writers;
	int waiting_readers;
} rwlock_t;


void rwlock_init(rwlock_t *rw) {
    rw->readers = 0;
	rw->writers = 0;
	rw->waiting_writers = 0;
	rw->waiting_readers = 0;
	Sem_init(&rw->lock, 1);
	Sem_init(&rw->writelock, 1);
}

// a starvation can occur here because a reader thread increases the waiting readers
// and then waits while there are active writers
// the reader might keep waiting forever because each new writer will acquire the lock
// and the reader will never get the chance to acquire the lock
void rwlock_acquire_readlock(rwlock_t *rw) {
    Sem_wait(&rw->lock);
	rw->waiting_readers++;
	while(rw->writers > 0 || rw->waiting_writers > 0){
		Sem_post(&rw->lock);
		Sem_wait(&rw->lock);
	}
	rw->waiting_readers--;
	rw->readers++;
	Sem_post(&rw->lock);
}

void rwlock_release_readlock(rwlock_t *rw) {
    Sem_wait(&rw->lock);
	rw->readers--;
	if(rw->readers == 0 && rw->waiting_writers > 0){
		Sem_post(&rw->writelock);
	}
	Sem_post(&rw->lock);
}

// a starvation can occur here because a writer thread increases the waiting writes
// and then waits while there are active readers and writers
// the writer might keep waiting forever because each new reader will acquire the lock
// and the writer will never get the chance to acquire the lock
void rwlock_acquire_writelock(rwlock_t *rw) {
    Sem_wait(&rw->lock);
	rw->waiting_writers++;
	while(rw->readers > 0 || rw->writers > 0){
		Sem_post(&rw->lock);
		Sem_wait(&rw->writelock);
		Sem_wait(&rw->lock);
	}
	rw->waiting_writers--;
	rw->writers++;
	Sem_post(&rw->lock);
}

void rwlock_release_writelock(rwlock_t *rw) {
	Sem_wait(&rw->lock);
	rw->writers--;
	if(rw->waiting_writers > 0){
		Sem_post(&rw->writelock);
	}
	else{
		while(rw->readers > 0){
			Sem_post(&rw->lock);
			Sem_wait(&rw->lock);
		}
	}
	Sem_post(&rw->lock);
}

//
// Don't change the code below (just use it!)
// 

int loops;
int value = 0;

rwlock_t lock;

void *reader(void *arg) {
    int i;
    for (i = 0; i < loops; i++) {
	rwlock_acquire_readlock(&lock);
	printf("read %d\n", value);
	rwlock_release_readlock(&lock);
    }
    return NULL;
}

void *writer(void *arg) {
    int i;
    for (i = 0; i < loops; i++) {
	rwlock_acquire_writelock(&lock);
	value++;
	printf("write %d\n", value);
	rwlock_release_writelock(&lock);
    }
    return NULL;
}

int main(int argc, char *argv[]) {
    assert(argc == 4);
    int num_readers = atoi(argv[1]);
    int num_writers = atoi(argv[2]);
    loops = atoi(argv[3]);

    pthread_t pr[num_readers], pw[num_writers];

    rwlock_init(&lock);

    printf("begin\n");

    int i;
    for (i = 0; i < num_readers; i++)
	Pthread_create(&pr[i], NULL, reader, NULL);
    for (i = 0; i < num_writers; i++)
	Pthread_create(&pw[i], NULL, writer, NULL);

    for (i = 0; i < num_readers; i++)
	Pthread_join(pr[i], NULL);
    for (i = 0; i < num_writers; i++)
	Pthread_join(pw[i], NULL);

    printf("end: value %d\n", value);

    return 0;
}

