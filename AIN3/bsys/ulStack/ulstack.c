#include "ulstack.h"
#include <stdlib.h>
#define DEFAULT_SIZE 4
void ulStackNew(ulstack *s) {
    s->stackSize = 0;
    s->allocSize = DEFAULT_SIZE;
    s->elementPtr = (unsigned long*) calloc(DEFAULT_SIZE, sizeof(long));
}

void ulStackDispose(ulstack *s) {
    free(s->elementPtr);
    s->stackSize = 0;
    s->allocSize = 0;
}

void ulStackPush(ulstack *s, unsigned long value) {
    if (s->stackSize == s->allocSize) {
        s->allocSize *= 2;
        s->elementPtr = (unsigned long*) realloc(s->elementPtr, s->allocSize * sizeof(long));
    }
    s->elementPtr[s->stackSize] = value;
    s->stackSize += 1;
}

unsigned long ulStackPop(ulstack *s) {
    if (s->stackSize != 0) {
        s->stackSize -= 1;
        return s->elementPtr[s->stackSize];
    } else {
        return (unsigned long) NULL;
    }
}

unsigned int ulStackSize(ulstack *s) {
    return s->stackSize;
}
