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

    int memory = atoi(argv[1]);
    int time = atoi(argv[2]);

    int *array = malloc(memory * 1024 * 1024);
    if (array == NULL)
    {
	printf("Failed to allocate memory\n");
	return 1;
    }

    while (1)
    {
	for (int i = 0; i < memory * 1024 * 1024 / sizeof(int); i++)
	{
	    array[i] = i;
	}
	sleep(1);
    }

    return 0;
}
