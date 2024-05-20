# Questions

##### 1. Let’s examine a simple program, “loop.s”. First, just read and understand it. Then, run it with these arguments (`./x86.py -t 1 -p loop.s -i 100 -R dx`) This specifies a single thread, an interrupt every 100 instructions, and tracing of register %dx. What will %dx be during the run? Use the -c flag to check your answers; the answers, on the left, show the value of the register (or memory value) after the instruction on the right has run.

```sh
❯ python3 x86.py -t 1 -p loop.s  -i 100 -R dx -c
   dx          Thread 0         
    0   
   -1   1000 sub  $1,%dx
   -1   1001 test $0,%dx
   -1   1002 jgte .top
   -1   1003 halt
```
- First, dx is 0 and then it is decremented by 1. Upon testing if dx is 0, and if it is greater than or equal to 0, it will jump to the top of the loop. Since -1 is not greater than or equal to 0, it will halt.

-----

##### 2. Same code, different flags: (`./x86.py -p loop.s -t 2 -i 100 -a dx=3,dx=3 -R dx`) This specifies two threads, and initializes each %dx to 3. What values will %dx see? Run with -c to check. Does the presence of multiple threads affect your calculations? Is there a race in this code?

```sh
❯ python3 x86.py -p loop.s -t 2 -i 100 -a dx=3,dx=3 -R dx -c
   dx          Thread 0                Thread 1         
    3   
    2   1000 sub  $1,%dx
    2   1001 test $0,%dx
    2   1002 jgte .top
    1   1000 sub  $1,%dx
    1   1001 test $0,%dx
    1   1002 jgte .top
    0   1000 sub  $1,%dx
    0   1001 test $0,%dx
    0   1002 jgte .top
   -1   1000 sub  $1,%dx
   -1   1001 test $0,%dx
   -1   1002 jgte .top
   -1   1003 halt
    3   ----- Halt;Switch -----  ----- Halt;Switch -----  
    2                            1000 sub  $1,%dx
    2                            1001 test $0,%dx
    2                            1002 jgte .top
    1                            1000 sub  $1,%dx
    1                            1001 test $0,%dx
    1                            1002 jgte .top
    0                            1000 sub  $1,%dx
    0                            1001 test $0,%dx
    0                            1002 jgte .top
   -1                            1000 sub  $1,%dx
   -1                            1001 test $0,%dx
   -1                            1002 jgte .top
   -1                            1003 halt
```

- There is no race in this code because the threads are not sharing any resources. There can be multiple threads running at the same time because they have their own set of private registers, isolated from the other threads.

-----

##### 3. Run this: ./x86.py -p loop.s -t 2 -i 3 -r -R dx -a dx=3,dx=3 This makes the interrupt interval small/random; use different seeds (-s) to see different interleavings. Does the interrupt frequency change anything?

```sh

❯ python3 x86.py -p loop.s -t 2 -i 3 -r -R dx -a dx=3,dx=3 -c
  dx          Thread 0                Thread 1         
    3   
    2   1000 sub  $1,%dx
    2   1001 test $0,%dx
    2   1002 jgte .top
    3   ------ Interrupt ------  ------ Interrupt ------  
    2                            1000 sub  $1,%dx
    2                            1001 test $0,%dx
    2                            1002 jgte .top
    2   ------ Interrupt ------  ------ Interrupt ------  
    1   1000 sub  $1,%dx
    1   1001 test $0,%dx
    2   ------ Interrupt ------  ------ Interrupt ------  
    1                            1000 sub  $1,%dx
    1   ------ Interrupt ------  ------ Interrupt ------  
    1   1002 jgte .top
    0   1000 sub  $1,%dx
    1   ------ Interrupt ------  ------ Interrupt ------  
    1                            1001 test $0,%dx
    1                            1002 jgte .top
    0   ------ Interrupt ------  ------ Interrupt ------  
    0   1001 test $0,%dx
    0   1002 jgte .top
   -1   1000 sub  $1,%dx
    1   ------ Interrupt ------  ------ Interrupt ------  
    0                            1000 sub  $1,%dx
   -1   ------ Interrupt ------  ------ Interrupt ------  
   -1   1001 test $0,%dx
   -1   1002 jgte .top
    0   ------ Interrupt ------  ------ Interrupt ------  
    0                            1001 test $0,%dx
    0                            1002 jgte .top
   -1   ------ Interrupt ------  ------ Interrupt ------  
   -1   1003 halt
    0   ----- Halt;Switch -----  ----- Halt;Switch -----  
   -1                            1000 sub  $1,%dx
   -1                            1001 test $0,%dx
   -1   ------ Interrupt ------  ------ Interrupt ------  
   -1                            1002 jgte .top
   -1                            1003 halt
```

- The interrupt frequency influences the interleaving of the instructions. Since the interrupt timer is small, context switches are more frequent. They still do not change the value of register dx because the threads are not sharing any resources.

-----

##### 4. Now, a different program, looping-race-nolock.s, which accesses a shared variable located at address 2000; we’ll call this variable value. Run it with a single thread to confirm your understanding: `./x86.py -p looping-race-nolock.s -t 1 -M 2000`. What is value (i.e., at memory address 2000) throughout the run? Use -c to check.

```sh
❯ python3 x86.py -p looping-race-nolock.s -t 1 -M 2000 -c
 2000          Thread 0         
    0   
    0   1000 mov 2000, %ax
    0   1001 add $1, %ax
    1   1002 mov %ax, 2000
    1   1003 sub  $1, %bx
    1   1004 test $0, %bx
    1   1005 jgt .top
    1   1006 halt
```

- Move the value at addres 2000 into ax, increment it by 1, move it back to address 2000, decrement bx by 1, test if bx is greater than 0, and jump to the top of the loop. The value at address 2000 is 1.

-----

##### 5. Run with multiple iterations/threads: `./x86.py -p looping-race-nolock.s -t 2 -a bx=3 -M 2000`.  Why does each thread loop three times? What is final value of value?

```sh
❯ python3 x86.py -p looping-race-nolock.s -t 2 -a bx=3 -M 2000 -c
 2000          Thread 0                Thread 1         
    0   
    0   1000 mov 2000, %ax
    0   1001 add $1, %ax
    1   1002 mov %ax, 2000
    1   1003 sub  $1, %bx
    1   1004 test $0, %bx
    1   1005 jgt .top
    1   1000 mov 2000, %ax
    1   1001 add $1, %ax
    2   1002 mov %ax, 2000
    2   1003 sub  $1, %bx
    2   1004 test $0, %bx
    2   1005 jgt .top
    2   1000 mov 2000, %ax
    2   1001 add $1, %ax
    3   1002 mov %ax, 2000
    3   1003 sub  $1, %bx
    3   1004 test $0, %bx
    3   1005 jgt .top
    3   1006 halt
    3   ----- Halt;Switch -----  ----- Halt;Switch -----  
    3                            1000 mov 2000, %ax
    3                            1001 add $1, %ax
    4                            1002 mov %ax, 2000
    4                            1003 sub  $1, %bx
    4                            1004 test $0, %bx
    4                            1005 jgt .top
    4                            1000 mov 2000, %ax
    4                            1001 add $1, %ax
    5                            1002 mov %ax, 2000
    5                            1003 sub  $1, %bx
    5                            1004 test $0, %bx
    5                            1005 jgt .top
    5                            1000 mov 2000, %ax
    5                            1001 add $1, %ax
    6                            1002 mov %ax, 2000
    6                            1003 sub  $1, %bx
    6                            1004 test $0, %bx
    6                            1005 jgt .top
    6                            1006 halt
```

- Each thread loops three times because the initial value of bx is 3 and it runs until bx reached 0. The final value of value is 6 because the threads are sharing the same memory location and they are not synchronized (mutex, only one thread can access the shared resource at a time). The critical section is at lines 1000 to 1002.

-----

##### 6. Run with random interrupt intervals: `./x86.py -p looping-race-nolock.s -t 2 -M 2000 -i 4 -r -s 0` with different seeds (-s 1, -s 2, etc.) Can you tell by looking at the thread interleaving what the final value of value will be? Does the timing of the interrupt matter? Where can it safely occur? Where not? In other words, where is the critical section exactly?

```sh
❯ python3 x86.py -p looping-race-nolock.s -t 2 -M 2000 -i 4 -r -s 0 -c
 2000          Thread 0                Thread 1         
    0   
    0   1000 mov 2000, %ax
    0   1001 add $1, %ax
    1   1002 mov %ax, 2000
    1   1003 sub  $1, %bx
    1   ------ Interrupt ------  ------ Interrupt ------  
    1                            1000 mov 2000, %ax
    1                            1001 add $1, %ax
    2                            1002 mov %ax, 2000
    2                            1003 sub  $1, %bx
    2   ------ Interrupt ------  ------ Interrupt ------  
    2   1004 test $0, %bx
    2   1005 jgt .top
    2   ------ Interrupt ------  ------ Interrupt ------  
    2                            1004 test $0, %bx
    2                            1005 jgt .top
    2   ------ Interrupt ------  ------ Interrupt ------  
    2   1006 halt
    2   ----- Halt;Switch -----  ----- Halt;Switch -----  
    2                            1006 halt
```

- The critical section is at lines 1000 to 1002 because a shared resource (address 2000) is being accessed by both threads. The timing of the interrupt would be inconvenient if it occurs between lines 1001 and 1002 because the value of the shared resource would be updated by one thread and then another thread would read the old value. The final value of value is 2 because the threads are not synchronized and each of them increment the value by 1. The interrupt can occur safely before or after the critical section.

-----

##### 7. Now examine fixed interrupt intervals: `./x86.py -p looping-race-nolock.s -a bx=1 -t 2 -M 2000 -i 1`.  What will the final value of the shared variable value be? What about when you change -i 2, -i 3, etc.? For which interrupt intervals does the program give the “correct” answer?

```sh

❯ python3 x86.py -p looping-race-nolock.s -a bx=1 -t 2 -M 2000 -i 3 -c
 2000          Thread 0                Thread 1         
    0   
    0   1000 mov 2000, %ax
    0   1001 add $1, %ax
    1   1002 mov %ax, 2000
    1   ------ Interrupt ------  ------ Interrupt ------  
    1                            1000 mov 2000, %ax
    1                            1001 add $1, %ax
    2                            1002 mov %ax, 2000
    2   ------ Interrupt ------  ------ Interrupt ------  
    2   1003 sub  $1, %bx
    2   1004 test $0, %bx
    2   1005 jgt .top
    2   ------ Interrupt ------  ------ Interrupt ------  
    2                            1003 sub  $1, %bx
    2                            1004 test $0, %bx
    2                            1005 jgt .top
    2   ------ Interrupt ------  ------ Interrupt ------  
    2   1006 halt
    2   ----- Halt;Switch -----  ----- Halt;Switch -----  
    2                            1006 halt
```

- The final value of the shared variable value is 2. The program gives the correct answer when the interrupt interval is 3 because the threads are synchronized and the shared resource is accessed by only one thread at a time. In that case the critical section is executed atomically, which means that the shared resource is not accessed by another thread until the first thread finishes its execution.

-----

##### 8. Run the same for more loops (e.g., set -a bx=100). What interrupt intervals (-i) lead to a correct outcome? Which intervals are surprising?

- I noticed that the correct output appears at 3, 6, 9 etc. This is because the critical section is executed atomically and the shared resource is accessed by only one thread at a time. There are some surprises where different intervals deliver different outputs, with no clear pattern.

-----

##### 9. One last program: `wait-for-me.s`. Run: `./x86.py -p wait-for-me.s -a ax=1,ax=0 -R ax -M 2000`. This sets the %ax register to 1 for thread 0, and 0 for thread 1, and watches %ax and memory location 2000. How should the code behave? How is the value at location 2000 being used by the threads? What will its final value be?

```sh
❯ python3 x86.py -p wait-for-me.s -a ax=1,ax=0 -R ax -M 2000 -c
 2000      ax          Thread 0                Thread 1         
    0       1   
    0       1   1000 test $1, %ax
    0       1   1001 je .signaller
    1       1   1006 mov  $1, 2000
    1       1   1007 halt
    1       0   ----- Halt;Switch -----  ----- Halt;Switch -----  
    1       0                            1000 test $1, %ax
    1       0                            1001 je .signaller
    1       0                            1002 mov  2000, %cx
    1       0                            1003 test $1, %cx
    1       0                            1004 jne .waiter
    1       0                            1005 halt
```

- The code should behave as follows: thread 0 will test if ax is 1, and if it is not, it will move 1 to memory location 2000 and halt. Thread 1 will test if ax is 1, and if it is not, it will move the value at memory location 2000 to cx, test if cx is 1, and if it is not, it will wait. The final value at location 2000 will be 1.

- What we can take from this how threads can synchronize with each other. Thread 0 signals thread 1 by setting the value at location 2000 to 1. Thread 1 waits for the signal by checking the value at location 2000. When the value is 1, it will halt. The way the threads are synchronized is by using a shared resource (location 2000) and a condition variable (ax). The condition variable is used to signal the other thread that it can proceed or wait. The shared resource is used to store the signal. If both threads are waiting for each other, they will be in a deadlock. A deadlock is a situation where two or more threads are waiting for each other to release a resource, but they never do.

-----

##### 10. Now switch the inputs: `./x86.py -p wait-for-me.s -a ax=0,ax=1 -R ax -M 2000`. How do the threads behave? What is thread 0 doing? How would changing the interrupt interval (e.g., -i 1000, or perhaps to use random intervals) change the trace outcome? Is the program efficiently using the CPU?

```sh
❯ python3 x86.py -p wait-for-me.s -a ax=0,ax=1 -R ax -M 2000 -c
 2000      ax          Thread 0                Thread 1         
    0       0   
    0       0   1000 test $1, %ax
    0       0   1001 je .signaller
    0       0   1002 mov  2000, %cx
    0       0   1003 test $1, %cx
    0       0   1004 jne .waiter
    0       0   1002 mov  2000, %cx
    0       0   1003 test $1, %cx
    0       0   1004 jne .waiter
    0       0   1002 mov  2000, %cx
    0       0   1003 test $1, %cx
    0       0   1004 jne .waiter
    0       0   1002 mov  2000, %cx
    0       0   1003 test $1, %cx
    0       0   1004 jne .waiter
    0       0   1002 mov  2000, %cx
    0       0   1003 test $1, %cx
    0       0   1004 jne .waiter
    0       0   1002 mov  2000, %cx
    0       0   1003 test $1, %cx
    0       0   1004 jne .waiter
    0       0   1002 mov  2000, %cx
    0       0   1003 test $1, %cx
    0       0   1004 jne .waiter
    0       0   1002 mov  2000, %cx
    0       0   1003 test $1, %cx
    0       0   1004 jne .waiter
    0       0   1002 mov  2000, %cx
    0       0   1003 test $1, %cx
    0       0   1004 jne .waiter
    0       0   1002 mov  2000, %cx
    0       0   1003 test $1, %cx
    0       0   1004 jne .waiter
    0       0   1002 mov  2000, %cx
    0       0   1003 test $1, %cx
    0       0   1004 jne .waiter
    0       0   1002 mov  2000, %cx
    0       0   1003 test $1, %cx
    0       0   1004 jne .waiter
    0       0   1002 mov  2000, %cx
    0       0   1003 test $1, %cx
    0       0   1004 jne .waiter
    0       0   1002 mov  2000, %cx
    0       0   1003 test $1, %cx
    0       0   1004 jne .waiter
    0       0   1002 mov  2000, %cx
    0       0   1003 test $1, %cx
    0       0   1004 jne .waiter
    0       0   1002 mov  2000, %cx
    0       0   1003 test $1, %cx
    0       0   1004 jne .waiter
    0       1   ------ Interrupt ------  ------ Interrupt ------  
    0       1                            1000 test $1, %ax
    0       1                            1001 je .signaller
    1       1                            1006 mov  $1, 2000
    1       1                            1007 halt
    1       0   ----- Halt;Switch -----  ----- Halt;Switch -----  
    1       0   1002 mov  2000, %cx
    1       0   1003 test $1, %cx
    1       0   1004 jne .waiter
    1       0   1005 halt
```

- Thread 0 keeps looping, reading the value from 2000 and testing if it is 1. If it is not, it will wait. Thread 1 tests if ax is 1, and if it is not, it will move 1 to memory location 2000 and halt. The program is not efficiently using the CPU because thread 0 is in a busy-wait loop, waiting for Thread 1 to set the value at location 2000 to 1. Thread 0 will only end when the value at location 2000 is set to 1. Thread 1 will only end when ax is set to 1. In the scenario of both thread starting with ax set to 0, the program will be in a deadlock because neither thread's condition will be met.
