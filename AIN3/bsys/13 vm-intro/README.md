# Questions

2.  Now, run free, perhaps using some of the arguments that might
    be useful (e.g., -m, to display memory totals in megabytes). How
    much memory is in your system? How much is free? 

Output:
```sh
❯ free -t -m
               total        used        free      shared  buff/cache   available
Mem:           15907        1347       12762          22        1797       14223
Swap:           2047           0        2047
Total:         17955        1347       14810
```
-----------------------------

3. `❯ ./memory-user 100 5`

Output:
```sh
❯ free -t -m
               total        used        free      shared  buff/cache   available
Mem:           15907        1500       12608          24        1799       14069
Swap:           2047           0        2047
Total:         17955        1500       14656
```
-----------------------------

4. Now, while running your memory-user program run the free
   tool. How do the memory usage totals change when your program
   is running? Try this for different amounts of memory usage. 
   What happens when you use really large amounts of memory?

- When the program is running, the memory usage totals change. 
  When I use large amounts, it just prints out my error message.
-----------------------------

7. Now run pmap on some of these processes, using various flags 
   to reveal many details about the process. How many different 
   entities make up a modern address space, as opposed to our 
   simple conception of code/stack/heap?

- It tells me which memory sections have read, write and execute permissions.
  It shows me which instructions are loaded from the memory-user binary file or
  system libraries like libc.so.6 and ld-linux-x86-64.so.2.
  The heap is indicated by the [anon] section and the stack by the [stack] section.
  The resident set size (RSS) is the amount of memory that is actually in the main memory.
  The dirty memory is the amount of memory that has been modified since it was last written to the disk.
  The total memory usage is the sum of the RSS and the dirty memory.
  Entities that make up a modern address space are the code, stack, heap, shared libraries, and the memory  -mapped files in the example of the -x flag. The -XX flag has even more.

Output:
```sh
❯ pmap -x -p 26630
26630:   ./memory-user 100 5
Address           Kbytes     RSS   Dirty Mode  Mapping
0000564927d35000       4       4       0 r---- /home/selin/HTWG-AIN/AIN3/bsys/vm-intro/memory-user
0000564927d36000       4       4       0 r-x-- /home/selin/HTWG-AIN/AIN3/bsys/vm-intro/memory-user
0000564927d37000       4       4       0 r---- /home/selin/HTWG-AIN/AIN3/bsys/vm-intro/memory-user
0000564927d38000       4       4       4 r---- /home/selin/HTWG-AIN/AIN3/bsys/vm-intro/memory-user
0000564927d39000       4       4       4 rw--- /home/selin/HTWG-AIN/AIN3/bsys/vm-intro/memory-user
0000564929776000     132       4       4 rw---   [ anon ]
00007ff902ead000  409616  102412  102412 rw---   [ anon ]
00007ff91beb1000     160     160       0 r---- /usr/lib/x86_64-linux-gnu/libc.so.6
00007ff91bed9000    1620    1032       0 r-x-- /usr/lib/x86_64-linux-gnu/libc.so.6
00007ff91c06e000     352     124       0 r---- /usr/lib/x86_64-linux-gnu/libc.so.6
00007ff91c0c6000       4       0       0 ----- /usr/lib/x86_64-linux-gnu/libc.so.6
00007ff91c0c7000      16      16      16 r---- /usr/lib/x86_64-linux-gnu/libc.so.6
00007ff91c0cb000       8       8       8 rw--- /usr/lib/x86_64-linux-gnu/libc.so.6
00007ff91c0cd000      52      20      20 rw---   [ anon ]
00007ff91c0ef000       8       4       4 rw---   [ anon ]
00007ff91c0f1000       8       8       0 r---- /usr/lib/x86_64-linux-gnu/ld-linux-x86-64.so.2
00007ff91c0f3000     168     168       0 r-x-- /usr/lib/x86_64-linux-gnu/ld-linux-x86-64.so.2
00007ff91c11d000      44      40       0 r---- /usr/lib/x86_64-linux-gnu/ld-linux-x86-64.so.2
00007ff91c129000       8       8       8 r---- /usr/lib/x86_64-linux-gnu/ld-linux-x86-64.so.2
00007ff91c12b000       8       8       8 rw--- /usr/lib/x86_64-linux-gnu/ld-linux-x86-64.so.2
00007ffc2f3d3000     132      12      12 rw---   [ stack ]
00007ffc2f3fa000      16       0       0 r----   [ anon ]
00007ffc2f3fe000       8       4       0 r-x--   [ anon ]
ffffffffff600000       4       0       0 --x--   [ anon ]
---------------- ------- ------- ------- 
total kB          412384  104048  102500
```
