#include <stdio.h>
#include <sys/time.h>
#include <pthread.h>
#include <stdlib.h>

typedef struct __node_t
{
    int key;
    struct __node_t *next;
    pthread_mutex_t lock;
} node_t;

typedef struct __list_t
{
    node_t *head;
    pthread_mutex_t lock;
} list_t;

void List_Init(list_t *L)
{
    L->head = NULL;
    pthread_mutex_init(&L->lock, NULL);
}

int List_Insert(list_t *L, int key)
{
    node_t *new = malloc(sizeof(node_t));
    if (new == NULL)
    {
        return 1;
    }
    new->key = key;
    pthread_mutex_init(&new->lock, NULL);

    pthread_mutex_lock(&L->lock);
    new->next = L->head;
    L->head = new;
    pthread_mutex_unlock(&L->lock);

    return 0;
}

int List_Lookup(list_t *L, int key)
{
    int rv = -1;
    pthread_mutex_lock(&L->lock);
    node_t *curr = L->head;
    while (curr)
    {
        if (curr->key == key)
        {
            rv = 0;
            break;
        }
        curr = curr->next;
    }
    pthread_mutex_unlock(&L->lock);
    return rv;
}

void List_Delete(list_t *L)
{
    node_t *curr = L->head;
    if (curr == NULL)
    {
        return;
    }
    pthread_mutex_lock(&curr->lock);
    while (curr)
    {
        node_t *tmp = curr;
        curr = curr->next;
        if (curr)
        {
            pthread_mutex_lock(&curr->lock);
            pthread_mutex_unlock(&tmp->lock);
        }
        free(tmp);
    }
    free(L);
}

void *thread(void *list)
{
    list_t *L = (list_t *)list;
    List_Lookup(L, 0);
    pthread_exit(0);    
}

int do_list_things(int threads, int list_length)
{
    list_t *L = malloc(sizeof(list_t));
    if (L == NULL)
    {
        return 1;
    }
    List_Init(L);

    for (int i = 0; i < list_length; i++)
    {
        if (List_Insert(L, i) == 1)
        {
            return 1;
        }
    }

    pthread_t *tid = malloc(threads * sizeof(pthread_t));
    if (tid == NULL)
    {
        return 1;
    }

    struct timeval start, end;
    gettimeofday(&start, NULL);

    for (int i = 0; i < threads; i++)
    {
        pthread_create(&tid[i], NULL, &thread, L);
    }

    for (int i = 0; i < threads; i++)
    {
        pthread_join(tid[i], NULL);
    }

    gettimeofday(&end, NULL);

    printf("Number of threads: %d\n", threads);
    printf("List length: %d\n", list_length);
    printf("Time taken: %ld microseconds\n\n", ((end.tv_sec * 1000000 + end.tv_usec) - (start.tv_sec * 1000000 + start.tv_usec)));

    free(tid);
    List_Delete(L);
    return 0;
}

int main()
{
    for (int i = 1; i <= 32; i <<= 1)
    {
        for (int j = 1; j <= 32; j <<= 1)
        {
            if (do_list_things(j, i) == 1)
            {
                return 1;
            }
        }
    }
    return 0;
}
