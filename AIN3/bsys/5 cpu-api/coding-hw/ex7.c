#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

int main()
{
    int rc = fork();
    if (rc == 0)
    {
	close(STDOUT_FILENO);
	printf("Child process\n");
    }
    return 0;
}
