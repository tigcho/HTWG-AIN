#include <stdio.h>
#include <sys/time.h>
#include <pthread.h>
#include <stdlib.h>

typedef struct __node_t
{
    int key;
    struct __node_t *left;
    struct __node_t *right;
    pthread_mutex_t lock;
} node_t;

typedef struct __tree_t
{
    node_t *root;
} tree_t;

void Tree_Init(tree_t *T)
{
    T->root = NULL;
}

int Tree_Insert(tree_t *T, int key)
{
    node_t *new = malloc(sizeof(node_t));
    if (new == NULL)
    {
        printf("Error allocating memory\n");
        return 1;
    }
    new->key = key;
    new->left = NULL;
    new->right = NULL;
    pthread_mutex_init(&new->lock, NULL);

    if (T->root == NULL)
    {
        T->root = new;
        return 0;
    }

    node_t *curr = T->root;
    pthread_mutex_lock(&curr->lock);
    while (curr != NULL)
    {
        if (key == curr->key)
        {
            pthread_mutex_unlock(&curr->lock);
            return 1;
        }
        if (key < curr->key)
        {
            if (curr->left == NULL)
            {
                curr->left = new;
                pthread_mutex_unlock(&curr->lock);
                return 0;
            }
            node_t *temp = curr;
            curr = curr->left;
            pthread_mutex_unlock(&temp->lock);
            pthread_mutex_lock(&curr->lock);
        }
        else
        {
            if (curr->right == NULL)
            {
                curr->right = new;
                pthread_mutex_unlock(&curr->lock);
                return 0;
            }
            node_t *temp = curr;
            curr = curr->right;
            pthread_mutex_unlock(&temp->lock);
            pthread_mutex_lock(&curr->lock);
        }
    }
    return 0;
}

int lookup_bfs(node_t *curr, int key)
{
    if (curr == NULL)
    {
        return -1;
    }
    pthread_mutex_lock(&curr->lock);
    if (key == curr->key)
    {
        pthread_mutex_unlock(&curr->lock);
        return 0;
    }
    if (key < curr->key)
    {
        pthread_mutex_unlock(&curr->lock);
        return lookup_bfs(curr->left, key);
    }
    pthread_mutex_unlock(&curr->lock);
    return lookup_bfs(curr->right, key);
}

int Tree_Lookup(tree_t *T, int key)
{
    return lookup_bfs(T->root, key);
}


void delete_bfs(node_t *curr)
{
    if (curr == NULL)
    {
        return;
    }
    delete_bfs(curr->left);
    delete_bfs(curr->right);
    pthread_mutex_destroy(&curr->lock);
    free(curr);
}

void Tree_Delete(tree_t *T)
{
    delete_bfs(T->root);
    T->root = NULL;
}

typedef struct __args_t
{
    tree_t *T;
    int threads;
    int ops;
} args_t;

void *thread_lookup(void *args)
{
    args_t *arg = (args_t *)args;
    tree_t *T = arg->T;
    for (int i = 0; i < arg->ops / arg->threads; i++)
    {
        Tree_Lookup(T, (int)pthread_self() + i);
    }
    pthread_exit(0);
}

void *thread_insert(void *args)
{
    args_t *arg = (args_t *)args;
    tree_t *T = arg->T;
    for (int i = 0; i < arg->ops / arg->threads; i++)
    {
        Tree_Insert(T, (int)pthread_self() + i);
    }
    pthread_exit(0);
}

void do_tree_things(int threads, int ops)
{
    tree_t T;
    Tree_Init(&T);

    pthread_t *tid = malloc(threads * sizeof(pthread_t));
    if (tid == NULL)
    {
        printf("Error allocating memory with %d threads and %d operations\n", threads, ops);
        return;
    }
    struct timeval start, insert, end;
    args_t args;
    args.T = &T;
    args.threads = threads;
    args.ops = ops;

    gettimeofday(&start, NULL);

    for (int i = 0; i < threads; i++)
    {
        pthread_create(&tid[i], NULL, &thread_insert, &args);
    }

    for (int i = 0; i < threads; i++)
    {
        pthread_join(tid[i], NULL);
    }

    gettimeofday(&insert, NULL);

    for (int i = 0; i < threads; i++)
    {
        pthread_create(&tid[i], NULL, &thread_lookup, &args);
    }

    for (int i = 0; i < threads; i++)
    {
        pthread_join(tid[i], NULL);
    }

    gettimeofday(&end, NULL);

    printf("Number of threads: %d\n", threads);
    printf("Number of operations: %d\n", ops);
    printf("Time taken to insert: %ld microseconds\n", ((insert.tv_sec * 1000000 + insert.tv_usec) - (start.tv_sec * 1000000 + start.tv_usec)));
    printf("Time taken to lookup: %ld microseconds\n\n", ((end.tv_sec * 1000000 + end.tv_usec) - (insert.tv_sec * 1000000 + insert.tv_usec)));

    free(tid);
    Tree_Delete(&T);

}


int main()
{
    for (int i = 1; i <= 32; i <<= 1)
    {
        for (int j = i; j <= 32; j <<= 1)
        {
            do_tree_things(i, j);
        }
    }
}
