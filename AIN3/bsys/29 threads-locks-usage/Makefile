CC = gcc
CFLAGS = -Wall -Wextra

SRCS = $(wildcard *.c)
TARGS = $(patsubst %.c,%,$(SRCS))

all: $(TARGS)

%: %.c
	$(CC) $(CFLAGS) $< -o $@

clean:
	rm -f $(TARGS)
