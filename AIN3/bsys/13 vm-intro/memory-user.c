#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

int main(int argc, char *argv[])
{
    if (argc != 3)
    {
    printf("Usage: %s <memory in mb> <time in seconds>\n", argv[0]);
    return 1;
    }

    printf("PID: %d\n", getpid());

    size_t memory = atoi(argv[1]);
    int time = atoi(argv[2]);

    int *array = (int*) malloc(memory * 1024 * 1024 * sizeof(int));
    if (array == NULL)
    {
    printf("Failed to allocate memory\n");
    return 1;
    }

    int i = 0;
    while (1)
    {
        if (i == memory * 1024 * 1024 / sizeof(int))
	{
	    i = 0;
	}
	array[i] = i;
	i++;
    }
    return 0;
}
