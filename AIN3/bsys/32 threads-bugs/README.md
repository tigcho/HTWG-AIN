# Questions

##### Now, run `./vector-deadlock -n 2 -l 1 -v`, which instantiates two threads (-n 2), each of which does one vector add (-l 1), and does so in verbose mode (-v). Make sure you understand the output. How does the output change from run to run?

```fish
AIN3/bsys/threads-bugs [ main][!?][C v11.4.0-gcc]
❯ ./vector-deadlock -n 2 -l 1 -v
->add(0, 1)
<-add(0, 1)
              ->add(0, 1)
              <-add(0, 1)

AIN3/bsys/threads-bugs [ main][!?][C v11.4.0-gcc]
❯ ./vector-deadlock -n 2 -l 1 -v
->add(0, 1)
              ->add(0, 1)
              <-add(0, 1)
<-add(0, 1)
```

- The output changes from run to run because the order of the threads is not guaranteed. The threads are racing to acquire the locks.

---

##### 2. Now add the -d flag, and change the number of loops (-l) from 1 to higher numbers. What happens? Does the code (always) deadlock?

- With a higher number of loops, a deadlock occurs more often. 

--- 

##### 3. How does changing the number of threads (-n) change the outcome of the program? Are there any values of -n that ensure no deadlock occurs?

- Only when -n 1 is used, the program does not deadlock. There is no deadlock when there is only one thread. When we use more, the possibility of a deadlock increases. But it has to be a very high number.

---

##### 4. Now examine the code in `vector-global-order.c`. First, make sure you understand what the code is trying to do; do you understand why the code avoids deadlock? Also, why is there a special case in this vector add() routine when the source and destination vectors are the same?

```c
void vector_add(vector_t *v_dst, vector_t *v_src) {
    if (v_dst < v_src) {
	Pthread_mutex_lock(&v_dst->lock);
	Pthread_mutex_lock(&v_src->lock);
    } else if (v_dst > v_src) {
	Pthread_mutex_lock(&v_src->lock);
	Pthread_mutex_lock(&v_dst->lock);
    } else {
	// special case: src and dst are the same
	Pthread_mutex_lock(&v_src->lock);
    }
    int i;
    for (i = 0; i < VECTOR_SIZE; i++) {
	v_dst->values[i] = v_dst->values[i] + v_src->values[i];
    }
    Pthread_mutex_unlock(&v_src->lock);
    if (v_dst != v_src) 
	Pthread_mutex_unlock(&v_dst->lock);
}
```

- Here you can see that the code avoids deadlock by locking the vectors in a specific order. The order is determined by the memory address of the vectors. If the destination vector has a lower memory address than the source vector, the destination vector is locked first. If the source vector has a lower memory address than the destination vector, the source vector is locked first. If the vectors are the same, only the source vector is locked. 
- The special case is there because if the source and destination vectors are the same, there is no need to lock the destination vector. Without this special case, the code would deadlock when the source and destination vectors are the same because the destination vector would be locked twice.

---

##### 5. Now run the code with the following flags: `-t -n 2 -l 100000 -d`. How long does the code take to complete? How does the total time change when you increase the number of loops, or the number of threads?

- With those flags it finishes in 0.7 seconds. The total time increases when the number of loops or the number of threads is increased by a few seconds, varying from 4 to 5 seconds.

---

##### 6. What happens if you turn on the parallelism flag (-p)? How much would you expect performance to change when each thread is working on adding different vectors (which is what -p enables) versus working on the same ones?

- When the parallelism flag is turned on, the performance increases significantly. The time it takes to complete the program is reduced by a few seconds. When each thread is working on adding different vectors, the performance is better because the threads are not competing for the same resources.
- In my case, at 99 threads, the total time is 0.17 seconds. It grows very slowly, almost constant.

---

##### 7. Now let’s study `vector-try-wait.c`. First make sure you understand the code. Is the first call to pthread mutex trylock() really needed? Now run the code. How fast does it run compared to the global order approach? How does the number of retries, as counted by the code, change as the number of threads increases?

```c
void vector_add(vector_t *v_dst, vector_t *v_src) {
  top:
    if (pthread_mutex_trylock(&v_dst->lock) != 0) {
	goto top;
    }
    if (pthread_mutex_trylock(&v_src->lock) != 0) {
	retry++;
	Pthread_mutex_unlock(&v_dst->lock);
	goto top;
    }
    int i;
    for (i = 0; i < VECTOR_SIZE; i++) {
	v_dst->values[i] = v_dst->values[i] + v_src->values[i];
    }
    Pthread_mutex_unlock(&v_dst->lock);
    Pthread_mutex_unlock(&v_src->lock);
}
```

- This code tries to lock the destination vector first. If it fails, it retries until it succeeds. 
- It runs slightly slower than the global order approach. The number of retries increases as the number of threads increases.

---

##### 8. Now let’s look at `vector-avoid-hold-and-wait.c`. What is the main problem with this approach? How does its performance compare to the other versions, when running both with -p and without it?

```c
void vector_add(vector_t *v_dst, vector_t *v_src) {
    // put GLOBAL lock around all lock acquisition...
    Pthread_mutex_lock(&global);
    Pthread_mutex_lock(&v_dst->lock);
    Pthread_mutex_lock(&v_src->lock);
    Pthread_mutex_unlock(&global);
    int i;
    for (i = 0; i < VECTOR_SIZE; i++) {
	v_dst->values[i] = v_dst->values[i] + v_src->values[i];
    }
    Pthread_mutex_unlock(&v_dst->lock);
    Pthread_mutex_unlock(&v_src->lock);
}
```

- The main problem with this approach is that it uses a global lock to lock all the vectors. This means that only one thread can access the vectors at a time, which defeats the purpose of using threads. The performance is worse than the other versions because of this. Concurrent threads are not able to work on different vectors at the same time.

---

##### 9. Finally, let’s look at `vector-nolock.c`. This version doesn’t use locks at all; does it provide the exact same semantics as the other versions? Why or why not?

```c
int fetch_and_add(int * variable, int value) {
    asm volatile("lock; xaddl %%eax, %2;"
		 :"=a" (value)                  
		 :"a" (value), "m" (*variable)  
		 :"memory");
    return value;
}

void vector_add(vector_t *v_dst, vector_t *v_src) {
    int i;
    for (i = 0; i < VECTOR_SIZE; i++) {
	fetch_and_add(&v_dst->values[i], v_src->values[i]);
    }
}
```

- This version does not provide the exact same semantics as the other versions because it does not use locks. This means that there is no guarantee that the vectors will be updated correctly. There is a possibility that two threads will try to update the same vector at the same time, which could result in incorrect values. This version is not thread-safe. It uses atomic operations to update the vectors.

---

##### 10. Now compare its performance to the other versions, both when threads are working on the same two vectors (no -p) and when each thread is working on separate vectors (-p). How does this no-lock version perform?

- The no-lock version performs better than the other versions when threads are working on separate vectors. When threads are working on the same vectors, the performance is worse than the other versions because there is no guarantee that the vectors will be updated correctly.
