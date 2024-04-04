#include <stdio.h>

int main() {
    int *p = NULL;
    printf("%d\n", *p);
    return 0;
}

// Segmentation fault is a specific kind of error caused by accessing memory that “does not belong to you.”
// For example, if you did not request memory from the operating system and you try to access it, you’ll get a segmentation fault. 
// It’s a signal that the operating system sends to the program.
// Every process in the system has its own virtual address space. This means that a process will use addresses starting from zero and go up to some maximum which is dependent on the architecture. A process is not allowed to access memory at address 0 and below
// The operating system will not allow you to dereference a null pointer because the memory address 0 is reserved by the operating system. It’s a flag that indicates that the pointer is not supposed to point anywhere. It’s a good practice to initialize a pointer to NULL if you don’t have an exact address to be assigned and then assign the address when you have one.
