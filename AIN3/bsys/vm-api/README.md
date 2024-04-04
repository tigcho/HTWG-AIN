# Questions

1. First, write a simple program called null.c that creates a pointer
   to an integer, sets it to NULL, and then tries to dereference it. 
   Compile this into an executable called null. What happens when you
   run this program?

- Segmentation fault is a specific kind of error caused by accessing memory that “does not belong to you.” 
  For example, if you did not request memory from the operating system and you try to access it, you’ll get 
  a segmentation fault. It’s a signal that the operating system sends to the program. Every process in the 
  system has its own virtual address space. This means that a process will use addresses starting from zero 
  and go up to some maximum which is dependent on the architecture. A process is not allowed to access memory 
  at address 0 and below. The operating system will not allow you to dereference a null pointer because the 
  memory address 0 is reserved by the operating system. It’s a flag that indicates that the pointer is not 
  supposed to point anywhere. The term "segmentation" has various uses in computing; in the context of 
  "segmentation fault", it refers to the address space of a program. With memory protection, only the program's 
  own address space is readable, and of this, only the stack and the read/write portion of the data segment of 
  a program are writable, while read-only data allocated in the const segment and the code segment are not. 
  Thus attempting to read outside of the program's address space, or writing to a read-only segment of the 
  address space, results in a segmentation fault.
-------------------------------------

2. Next, compile this program with symbol information included (with
   the -g flag). Doing so let’s put more information into the exe-
   cutable, enabling the debugger to access more useful information
   about variable names and the like. Run the program under the de-
   bugger by typing gdb null and then, once gdb is running, typing
   run. What does gdb show you?

Output:
```sh
Program received signal SIGSEGV, Segmentation fault.
0x0000555555555161 in main () at null.c:5
5           printf("%d\n", *p);
```
-----------------------------------

3. Finally, use the valgrind tool on this program. We’ll use memcheck
   that is a part of valgrind to analyze what happens. Run this by
   typing in the following: valgrind --leak-check=yes null.
   What happens when you run this? Can you interpret the output
   from the tool?

- The output tells me that we expected to read 4 Bytes due to the 
  int pointer but it was a NULL pointer which wasn't mallocd or freed.

Output:
```sh
❯ valgrind --leak-check=yes ./null
==31227== Memcheck, a memory error detector
==31227== Copyright (C) 2002-2017, and GNU GPL'd, by Julian Seward et al.
==31227== Using Valgrind-3.18.1 and LibVEX; rerun with -h for copyright info
==31227== Command: ./null
==31227== 
==31227== Invalid read of size 4
==31227==    at 0x109161: main (null.c:5)
==31227==  Address 0x0 is not stack'd, malloc'd or (recently) free'd
==31227== 
==31227== 
==31227== Process terminating with default action of signal 11 (SIGSEGV)
==31227==  Access not within mapped region at address 0x0
==31227==    at 0x109161: main (null.c:5)
==31227==  If you believe this happened as a result of a stack
==31227==  overflow in your program's main thread (unlikely but
==31227==  possible), you can try to increase the size of the
==31227==  main thread stack using the --main-stacksize= flag.
==31227==  The main thread stack size used in this run was 8388608.
==31227== 
==31227== HEAP SUMMARY:
==31227==     in use at exit: 0 bytes in 0 blocks
==31227==   total heap usage: 0 allocs, 0 frees, 0 bytes allocated
==31227== 
==31227== All heap blocks were freed -- no leaks are possible
==31227== 
==31227== For lists of detected and suppressed errors, rerun with: -s
==31227== ERROR SUMMARY: 1 errors from 1 contexts (suppressed: 0 from 0)
fish: Job 1, 'valgrind --leak-check=yes ./null' terminated by signal SIGSEGV (Address boundary error)
```
-----------------------------------

4. Write a simple program that allocates memory using malloc() but
   forgets to free it before exiting. What happens when this program
   runs? Can you use gdb to find any problems with it? How about
   valgrind (again with the --leak-check=yes flag)?

- The program runs fine but when we run valgrind with --leak-check=yes flag, it tells us that we have a memory leak. 
  The memory allocated by malloc() is not freed before exiting the program.
  It still works fine because the operating system will reclaim the memory when the program exits.

gdb:
```sh
Starting program: /home/selin/HTWG-AIN/AIN3/bsys/vm-api/forgetfree 
[Thread debugging using libthread_db enabled]
Using host libthread_db library "/lib/x86_64-linux-gnu/libthread_db.so.1".
10
[Inferior 1 (process 46729) exited normally]
```
valgrind:
```sh
==47078== Memcheck, a memory error detector
==47078== Copyright (C) 2002-2017, and GNU GPL'd, by Julian Seward et al.
==47078== Using Valgrind-3.18.1 and LibVEX; rerun with -h for copyright info
==47078== Command: ./forgetfree
==47078== 
10
==47078== 
==47078== HEAP SUMMARY:
==47078==     in use at exit: 4 bytes in 1 blocks
==47078==   total heap usage: 2 allocs, 1 frees, 1,028 bytes allocated
==47078== 
==47078== 4 bytes in 1 blocks are definitely lost in loss record 1 of 1
==47078==    at 0x4848899: malloc (in /usr/libexec/valgrind/vgpreload_memcheck-amd64-linux.so)
==47078==    by 0x10917E: main (forgetfree.c:5)
==47078== 
==47078== LEAK SUMMARY:
==47078==    definitely lost: 4 bytes in 1 blocks
==47078==    indirectly lost: 0 bytes in 0 blocks
==47078==      possibly lost: 0 bytes in 0 blocks
==47078==    still reachable: 0 bytes in 0 blocks
==47078==         suppressed: 0 bytes in 0 blocks
==47078== 
==47078== For lists of detected and suppressed errors, rerun with: -s
==47078== ERROR SUMMARY: 1 errors from 1 contexts (suppressed: 0 from 0)
```
-----------------------------------

5. Write a program that creates an array of integers called data of size
   100 using malloc; then, set data[100] to zero. What happens
   when you run this program? What happens when you run this
   program using valgrind? Is the program correct?

- The program runs fine but when we run valgrind with --leak-check=yes flag, it tells us that we have an invalid write of size 4. 
  The program is not correct because we are trying to access an element outside the array bounds.

valgrind:
```sh
==47882== Memcheck, a memory error detector
==47882== Copyright (C) 2002-2017, and GNU GPL'd, by Julian Seward et al.
==47882== Using Valgrind-3.18.1 and LibVEX; rerun with -h for copyright info
==47882== Command: ./array
==47882== 
==47882== Invalid write of size 4
==47882==    at 0x10918D: main (array.c:6)
==47882==  Address 0x4a9c1d0 is 0 bytes after a block of size 400 alloc'd
==47882==    at 0x4848899: malloc (in /usr/libexec/valgrind/vgpreload_memcheck-amd64-linux.so)
==47882==    by 0x10917E: main (array.c:5)
==47882== 
==47882== 
==47882== HEAP SUMMARY:
==47882==     in use at exit: 0 bytes in 0 blocks
==47882==   total heap usage: 1 allocs, 1 frees, 400 bytes allocated
==47882== 
==47882== All heap blocks were freed -- no leaks are possible
==47882== 
==47882== For lists of detected and suppressed errors, rerun with: -s
==47882== ERROR SUMMARY: 1 errors from 1 contexts (suppressed: 0 from 0)
```
-----------------------------------

6. Create a program that allocates an array of integers (as above), frees
   them, and then tries to print the value of one of the elements of
   the array. Does the program run? What happens when you use
   valgrind on it?

- The program runs fine but when we run valgrind with --leak-check=yes flag, it tells us that we have an invalid read of size 4. 
  The program is not correct because we are trying to access an element of the array after freeing it.

valgrind:
```sh







