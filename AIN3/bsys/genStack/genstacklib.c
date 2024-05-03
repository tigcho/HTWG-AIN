#include "genstacklib.h"
#include <stdlib.h>
#include <string.h>

#define DEFAULT_SIZE 4

void genStackNew (genStack *s, int elementSize, void (*freefn)(void*)) {
    s->elementSize = elementSize;
    s->stackSize = 0;
    s->allocSize = DEFAULT_SIZE;
    s->elementPtr = calloc(s->allocSize, elementSize);
    s->freefn = freefn;
}

void genStackDispose (genStack *s) {
    if (s->freefn != NULL) {
        for (int i = 0; i < s->stackSize; i++) {
            s->freefn((char *)s->elementPtr + i * s->elementSize);
        }
    }
    free(s->elementPtr);
}

bool genStackIsEmpty (const genStack *s) {
    return s->stackSize == 0;
}

void genStackPush (genStack *s, const void *elementAddr) {
    if (s->stackSize == s->allocSize) {
        s->allocSize *= 2;
        s->elementPtr = realloc(s->elementPtr, s->allocSize * s->elementSize);
    }
    void *target = (char *)s->elementPtr + s->stackSize * s->elementSize;
    memcpy(target, elementAddr, s->elementSize);
    s->stackSize++;
}

void genStackPop (genStack *s, void *elementAddr) {
    s->stackSize--;
    void *source = (char *)s->elementPtr + s->stackSize * s->elementSize;
    memcpy(elementAddr, source, s->elementSize);
}

unsigned int genStackSize (genStack *s) {
    return s->stackSize;
}
