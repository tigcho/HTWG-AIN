all: null malloc_no_free field use_after_free funny_free vector

clean:
	rm -f null.o null malloc_no_free.o malloc_no_free field.o field use_after_free.o use_after_free vector.o vector

null: null.c
	gcc -o null null.c -Wall -g

malloc_no_free: malloc_no_free.c
	gcc -o malloc_no_free malloc_no_free.c -Wall -g

field: field.c
	gcc -o field field.c -Wall -g

use_after_free: use_after_free.c
	gcc -o use_after_free use_after_free.c -Wall -g

funny_free: funny_free.c
	gcc -o funny_free funny_free.c -Wall -g

vector: vector.c
	gcc -o vector vector.c -Wall -g