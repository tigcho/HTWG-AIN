# Questions

##### 1. Our first question focuses on `main-two-cvs-while.c` (the working solution). First, study the code. Do you think you have an understanding of what should happen when you run the program?

- `do_fill()` checks if it is empty first, then it updates the `fill_ptr` with the new value and the number of items in the buffer.
- `do_get()` checks of the position in the buffer is not empty before taking the value. Then it sets the position in the buffer to EMPTY and updates the getter pointer `use_ptr` and the number of items in the buffer.
- `producer(void *arg)` is a thread that produces unique items based on their ID. It waits in case the buffer is full and waits for the signal that the buffer is empty.
- `consumer(void *arg)` is a thread that consumes items from the buffer. It counts the number of consumed items and signals that the buffer is empty.
- When the program is run, the threads should run alternately. The producer thread should insert items into the buffer until it is full, then it waits until a consumer thread consumes an item. If the buffer is empty, the consumer thread will wait.

---

##### 4. Let’s look at some timings. How long do you think the following execution, with one producer, three consumers, a single-entry shared buffer, and each consumer pausing at point c3 for a second, will take?

- It will take 10 seconds because the producer will produce an item and place it in the buffer, then the consumer will consume the item and wait for 1 second. This will happen 10 times. Since each wait costs 1 second, and 10 items are produced and consumed, the total time will be 10 seconds. The result was 12.01 seconds and only the first consumer was able to consume the item. The other two consumers were not able to consume the item because the buffer can only hold one item at a time.

---

##### 5. Now change the size of the shared buffer to 3 (-m 3). Will this make any difference in the total time?

- The total time will be the same because even though it can hold more items, the producer will still produce an item and place it in the buffer, then the consumer will consume the item and wait for 1 second. This will happen 10 times. Since each wait costs 1 second, and 10 items are produced and consumed, the total time will be 10 seconds. The result was 11.01 seconds and only the first consumer was able to consume the item.

---

##### 6. Now change the location of the sleep to c6 (this models a consumer taking something off the queue and then doing something with it), again using a single-entry buffer. What time do you predict in this case?

- The total time are 5 seconds because at c6 the consumer will unlock. While one consumer sleeps and unlocks, another consumer will consume the item. This will happen 10 times and the workload os pretty evenly distributed on the three consumers.

---

##### 7. Finally, change the buffer size to 3 again (-m 3). What time do you predict now?

- The total time will be 5 seconds again because the buffer size does not influence the time it takes to consume the items. It depends on when the consumer unlocks and other consumers can consume the item.

---

##### 8. Now let’s look at main-one-cv-while.c. Can you configure a sleep string, assuming a single producer, one consumer, and a buffer of size 1, to cause a problem with this code?

- The only potential issue might be a missed signal but this is very unlikely since this is very timing-sensitive. Other than that there cannot be any issues because the threads are synchronized. The threads have no other option that to wake each other up.

---

##### 9. Now change the number of consumers to two. Can you construct sleep strings for the producer and the consumers so as to cause a problem in the code?

- Unlike the question above, when we have two consumers, there is a potential issue that the wrong thread is woken up. 

`./main-one-cv-while -c 2 -v -P 0,0,0,0,0,0,1`

```fish
✦2 ❯ ./main-one-cv-while -c 2 -v -P 0,0,0,0,0,0,1
 NF        P0 C0 C1 
  0 [*--- ] p0
  0 [*--- ]    c0
  0 [*--- ]       c0
  0 [*--- ] p1
  1 [*  0 ] p4
  1 [*  0 ] p5
  1 [*  0 ] p6
  1 [*  0 ]    c1
  0 [*--- ]    c4
  0 [*--- ]    c5
  0 [*--- ]    c6
  0 [*--- ]       c1
  0 [*--- ]    c0
  0 [*--- ]       c2
  0 [*--- ]    c1
  0 [*--- ]    c2
  1 [*EOS ] [main: added end-of-stream marker]
  1 [*EOS ]       c3
  0 [*--- ]       c4
  0 [*--- ]       c5
  0 [*--- ]       c6
  0 [*--- ]    c3
  0 [*--- ]    c2
^Zfish: Job 3, './main-one-cv-while -c 2 -v -P…' has stopped
```

---

##### 10. Now examine main-two-cvs-if.c. Can you cause a problem to happen in this code? Again consider the case where there is only one consumer, and then the case where there is more than one.

`./main-two-cvs-if -m 1 -c 2 -p 1 -l 3 -v -C 0,0,0,1,0,0,0:0,0,0,0,0,0,0`

```fish
✦3 ❯ ./main-two-cvs-if -p 1 -c 2 -l 10 -m 1 -C 0,0,0,0,0,0,0:0,0,0,0,1,0,0 -v
 NF        P0 C0 C1 
  0 [*--- ] p0
  0 [*--- ]    c0
  0 [*--- ]       c0
  0 [*--- ] p1
  1 [*  0 ] p4
  1 [*  0 ] p5
  1 [*  0 ] p6
  1 [*  0 ]    c1
  1 [*  0 ] p0
  0 [*--- ]    c4
  0 [*--- ]    c5
  0 [*--- ]    c6
  0 [*--- ]       c1
  0 [*--- ]    c0
  0 [*--- ]       c2
  0 [*--- ] p1
  1 [*  1 ] p4
  1 [*  1 ] p5
  1 [*  1 ] p6
  1 [*  1 ]    c1
  1 [*  1 ] p0
  0 [*--- ]    c4
  0 [*--- ]    c5
  0 [*--- ]    c6
  0 [*--- ]       c3
  0 [*--- ]    c0
error: tried to get an empty buffer
```

---

##### 11. Finally, examine main-two-cvs-while-extra-unlock.c. What problem arises when you release the lock before doing a put or a get? Can you reliably cause such a problem to happen, given the sleep strings? What bad thing can happen?

- The threads are not synchronized in `do_get()` and `do_fill()` which can lead to a race condition for shared variables when two threads are executing at the same time.
