CC = gcc
CFLAGS = -Wall -pedantic -Werror -g
LDFLAGS = -lm
RM = rm -f

SOURCES = $(wildcard *.c)
OBJECTS = $(SOURCES:.c=.o)
TARGETS = $(SOURCES:.c=)

.PHONY: all clean

all: test_stack

%: %.c
	$(CC) $(CFLAGS) $(LDFLAGS) $< -o $@

%.o: %.c
	$(CC) $(CFLAGS) -c -o $@  $< 

test_stack: ulstack.o test_stack.o
	$(CC) $(CFLAGS) $^ -o $@

clean:
	$(RM) $(OBJECTS) $(TARGETS)
