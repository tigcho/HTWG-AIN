# Questions

##### 1. First build main-race.c. Examine the code so you can see the (hopefully obvious) data race in the code. Now run helgrind (by typing valgrind --tool=helgrind main-race) to see how it reports the race. Does it point to the right lines of code? What other information does it give to you?

```sh
❯ valgrind --tool=helgrind ./main-race
==51819== Helgrind, a thread error detector
==51819== Copyright (C) 2007-2017, and GNU GPL'd, by OpenWorks LLP et al.
==51819== Using Valgrind-3.18.1 and LibVEX; rerun with -h for copyright info
==51819== Command: ./main-race
==51819== 
==51819== ---Thread-Announcement------------------------------------------
==51819== 
==51819== Thread #1 is the program's root thread
==51819== 
==51819== ---Thread-Announcement------------------------------------------
==51819== 
==51819== Thread #2 was created
==51819==    at 0x499D9F3: clone (clone.S:76)
==51819==    by 0x499E8EE: __clone_internal (clone-internal.c:83)
==51819==    by 0x490C6D8: create_thread (pthread_create.c:295)
==51819==    by 0x490D1FF: pthread_create@@GLIBC_2.34 (pthread_create.c:828)
==51819==    by 0x4853767: ??? (in /usr/libexec/valgrind/vgpreload_helgrind-amd64-linux.so)
==51819==    by 0x109209: main (in /home/selin/HTWG-AIN/AIN3/bsys/threads-api/main-race)
==51819== 
==51819== ----------------------------------------------------------------
==51819== 
==51819== Possible data race during write of size 4 at 0x10C014 by thread #1
==51819== Locks held: none
==51819==    at 0x10923F: main (in /home/selin/HTWG-AIN/AIN3/bsys/threads-api/main-race)
==51819== 
==51819== This conflicts with a previous write of size 4 by thread #2
==51819== Locks held: none
==51819==    at 0x1091BE: worker (in /home/selin/HTWG-AIN/AIN3/bsys/threads-api/main-race)
==51819==    by 0x485396A: ??? (in /usr/libexec/valgrind/vgpreload_helgrind-amd64-linux.so)
==51819==    by 0x490CAC2: start_thread (pthread_create.c:442)
==51819==    by 0x499DA03: clone (clone.S:100)
==51819==  Address 0x10c014 is 0 bytes inside data symbol "balance"
==51819== 
==51819== 
==51819== ERROR SUMMARY: 2 errors from 2 contexts (suppressed: 0 from 0)
```

- Helgrind reports that there is a possible data race in line 0x10C014 by thread #1. It also reports if there were any locks held during the execution of the code. Above that, it gives information about where and how many threads were created and which writes and reads were conflicting with each other.

------

##### 2. What happens when you remove one of the offending lines of code? Now add a lock around one of the updates to the shared variable, and then around both. What does helgrind report in each of these cases?

- When I remove line 8 from the code and run helgrind, it does not report any errors. This is because the race condition is removed from the code.
```sh
❯ valgrind --tool=helgrind ./main-race
==55446== Helgrind, a thread error detector
==55446== Copyright (C) 2007-2017, and GNU GPL'd, by OpenWorks LLP et al.
==55446== Using Valgrind-3.18.1 and LibVEX; rerun with -h for copyright info
==55446== Command: ./main-race
==55446== 
==55446== 
==55446== Use --history-level=approx or =none to gain increased speed, at
==55446== the cost of reduced accuracy of conflicting-access information
==55446== For lists of detected and suppressed errors, rerun with: -s
==55446== ERROR SUMMARY: 0 errors from 0 contexts (suppressed: 0 from 0)
```

- When I add a lock around line 8 I receive two errors from helgrind. This happens because there is a possible race condition still because there is no lock around the balance variable in the main method.
```sh
❯ valgrind --tool=helgrind ./main-race
==67137== Helgrind, a thread error detector
==67137== Copyright (C) 2007-2017, and GNU GPL'd, by OpenWorks LLP et al.
==67137== Using Valgrind-3.18.1 and LibVEX; rerun with -h for copyright info
==67137== Command: ./main-race
==67137== 
==67137== ---Thread-Announcement------------------------------------------
==67137== 
==67137== Thread #1 is the program's root thread
==67137== 
==67137== ---Thread-Announcement------------------------------------------
==67137== 
==67137== Thread #2 was created
==67137==    at 0x499D9F3: clone (clone.S:76)
==67137==    by 0x499E8EE: __clone_internal (clone-internal.c:83)
==67137==    by 0x490C6D8: create_thread (pthread_create.c:295)
==67137==    by 0x490D1FF: pthread_create@@GLIBC_2.34 (pthread_create.c:828)
==67137==    by 0x4853767: ??? (in /usr/libexec/valgrind/vgpreload_helgrind-amd64-linux.so)
==67137==    by 0x10926B: main (in /home/selin/HTWG-AIN/AIN3/bsys/threads-api/main-race)
==67137== 
==67137== ----------------------------------------------------------------
==67137==  Lock at 0x10C060 was first observed
==67137==    at 0x4850CCF: ??? (in /usr/libexec/valgrind/vgpreload_helgrind-amd64-linux.so)
==67137==    by 0x109207: worker (in /home/selin/HTWG-AIN/AIN3/bsys/threads-api/main-race)
==67137==    by 0x485396A: ??? (in /usr/libexec/valgrind/vgpreload_helgrind-amd64-linux.so)
==67137==    by 0x490CAC2: start_thread (pthread_create.c:442)
==67137==    by 0x499DA03: clone (clone.S:100)
==67137==  Address 0x10c060 is 0 bytes inside data symbol "lock"
==67137== 
==67137== Possible data race during write of size 4 at 0x10C040 by thread #1
==67137== Locks held: none
==67137==    at 0x1092A1: main (in /home/selin/HTWG-AIN/AIN3/bsys/threads-api/main-race)
==67137== 
==67137== This conflicts with a previous write of size 4 by thread #2
==67137== Locks held: 1, at address 0x10C060
==67137==    at 0x109211: worker (in /home/selin/HTWG-AIN/AIN3/bsys/threads-api/main-race)
==67137==    by 0x485396A: ??? (in /usr/libexec/valgrind/vgpreload_helgrind-amd64-linux.so)
==67137==    by 0x490CAC2: start_thread (pthread_create.c:442)
==67137==    by 0x499DA03: clone (clone.S:100)
==67137==  Address 0x10c040 is 0 bytes inside data symbol "balance"
==67137== 
==67137== 
==67137== ERROR SUMMARY: 2 errors from 2 contexts (suppressed: 0 from 0)
```

- When I add a lock around both helgrind does not report any errors because both threads are now synchronized and the possibility of a race condition is removed.
```sh
❯ valgrind --tool=helgrind ./main-race
==68750== Helgrind, a thread error detector
==68750== Copyright (C) 2007-2017, and GNU GPL'd, by OpenWorks LLP et al.
==68750== Using Valgrind-3.18.1 and LibVEX; rerun with -h for copyright info
==68750== Command: ./main-race
==68750== 
==68750== 
==68750== Use --history-level=approx or =none to gain increased speed, at
==68750== the cost of reduced accuracy of conflicting-access information
==68750== For lists of detected and suppressed errors, rerun with: -s
==68750== ERROR SUMMARY: 0 errors from 0 contexts (suppressed: 7 from 7)
```

------

##### 3. Now let’s look at main-deadlock.c. Examine the code. This code has a problem known as deadlock (which we discuss in much more depth in a forthcoming chapter). Can you see what problem it might have?

```c
#include <stdio.h>

#include "common_threads.h"

pthread_mutex_t m1 = PTHREAD_MUTEX_INITIALIZER;
pthread_mutex_t m2 = PTHREAD_MUTEX_INITIALIZER;

void* worker(void* arg) {
    if ((long long) arg == 0) {
	Pthread_mutex_lock(&m1);
	Pthread_mutex_lock(&m2);
    } else {
	Pthread_mutex_lock(&m2);
	Pthread_mutex_lock(&m1);
    }
    Pthread_mutex_unlock(&m1);
    Pthread_mutex_unlock(&m2);
    return NULL;
}

int main(int argc, char *argv[]) {
    pthread_t p1, p2;
    Pthread_create(&p1, NULL, worker, (void *) (long long) 0);
    Pthread_create(&p2, NULL, worker, (void *) (long long) 1);
    Pthread_join(p1, NULL);
    Pthread_join(p2, NULL);
    return 0;
}
```

- The problem with the code is that there is a deadlock. The deadlock occurs when the first thread locks the first mutex and the second thread locks the second mutex. Then the first thread tries to lock the second mutex and the second thread tries to lock the first mutex. This causes a deadlock because both threads are waiting for each other to release the mutexes.

- More specifically referencing the code: main creates two threads, p1 and p2. The first thread locks m1 and then m2. The second thread locks m2 and then m1. Then both threads try to unlock the mutexes. But since they are waiting for each other to release the mutexes, the program gets stuck in a deadlock and does not terminate.

------

##### 4. Now run helgrind on this code. What does helgrind report?

```sh
❯ valgrind --tool=helgrind ./main-deadlock
==71298== Helgrind, a thread error detector
==71298== Copyright (C) 2007-2017, and GNU GPL'd, by OpenWorks LLP et al.
==71298== Using Valgrind-3.18.1 and LibVEX; rerun with -h for copyright info
==71298== Command: ./main-deadlock
==71298== 
==71298== ---Thread-Announcement------------------------------------------
==71298== 
==71298== Thread #3 was created
==71298==    at 0x499D9F3: clone (clone.S:76)
==71298==    by 0x499E8EE: __clone_internal (clone-internal.c:83)
==71298==    by 0x490C6D8: create_thread (pthread_create.c:295)
==71298==    by 0x490D1FF: pthread_create@@GLIBC_2.34 (pthread_create.c:828)
==71298==    by 0x4853767: ??? (in /usr/libexec/valgrind/vgpreload_helgrind-amd64-linux.so)
==71298==    by 0x1093F4: main (in /home/selin/HTWG-AIN/AIN3/bsys/threads-api/main-deadlock)
==71298== 
==71298== ----------------------------------------------------------------
==71298== 
==71298== Thread #3: lock order "0x10C040 before 0x10C080" violated
==71298== 
==71298== Observed (incorrect) order is: acquisition of lock at 0x10C080
==71298==    at 0x4850CCF: ??? (in /usr/libexec/valgrind/vgpreload_helgrind-amd64-linux.so)
==71298==    by 0x109288: worker (in /home/selin/HTWG-AIN/AIN3/bsys/threads-api/main-deadlock)
==71298==    by 0x485396A: ??? (in /usr/libexec/valgrind/vgpreload_helgrind-amd64-linux.so)
==71298==    by 0x490CAC2: start_thread (pthread_create.c:442)
==71298==    by 0x499DA03: clone (clone.S:100)
==71298== 
==71298==  followed by a later acquisition of lock at 0x10C040
==71298==    at 0x4850CCF: ??? (in /usr/libexec/valgrind/vgpreload_helgrind-amd64-linux.so)
==71298==    by 0x1092C3: worker (in /home/selin/HTWG-AIN/AIN3/bsys/threads-api/main-deadlock)
==71298==    by 0x485396A: ??? (in /usr/libexec/valgrind/vgpreload_helgrind-amd64-linux.so)
==71298==    by 0x490CAC2: start_thread (pthread_create.c:442)
==71298==    by 0x499DA03: clone (clone.S:100)
==71298== 
==71298== Required order was established by acquisition of lock at 0x10C040
==71298==    at 0x4850CCF: ??? (in /usr/libexec/valgrind/vgpreload_helgrind-amd64-linux.so)
==71298==    by 0x10920E: worker (in /home/selin/HTWG-AIN/AIN3/bsys/threads-api/main-deadlock)
==71298==    by 0x485396A: ??? (in /usr/libexec/valgrind/vgpreload_helgrind-amd64-linux.so)
==71298==    by 0x490CAC2: start_thread (pthread_create.c:442)
==71298==    by 0x499DA03: clone (clone.S:100)
==71298== 
==71298==  followed by a later acquisition of lock at 0x10C080
==71298==    at 0x4850CCF: ??? (in /usr/libexec/valgrind/vgpreload_helgrind-amd64-linux.so)
==71298==    by 0x109249: worker (in /home/selin/HTWG-AIN/AIN3/bsys/threads-api/main-deadlock)
==71298==    by 0x485396A: ??? (in /usr/libexec/valgrind/vgpreload_helgrind-amd64-linux.so)
==71298==    by 0x490CAC2: start_thread (pthread_create.c:442)
==71298==    by 0x499DA03: clone (clone.S:100)
==71298== 
==71298==  Lock at 0x10C040 was first observed
==71298==    at 0x4850CCF: ??? (in /usr/libexec/valgrind/vgpreload_helgrind-amd64-linux.so)
==71298==    by 0x10920E: worker (in /home/selin/HTWG-AIN/AIN3/bsys/threads-api/main-deadlock)
==71298==    by 0x485396A: ??? (in /usr/libexec/valgrind/vgpreload_helgrind-amd64-linux.so)
==71298==    by 0x490CAC2: start_thread (pthread_create.c:442)
==71298==    by 0x499DA03: clone (clone.S:100)
==71298==  Address 0x10c040 is 0 bytes inside data symbol "m1"
==71298== 
==71298==  Lock at 0x10C080 was first observed
==71298==    at 0x4850CCF: ??? (in /usr/libexec/valgrind/vgpreload_helgrind-amd64-linux.so)
==71298==    by 0x109249: worker (in /home/selin/HTWG-AIN/AIN3/bsys/threads-api/main-deadlock)
==71298==    by 0x485396A: ??? (in /usr/libexec/valgrind/vgpreload_helgrind-amd64-linux.so)
==71298==    by 0x490CAC2: start_thread (pthread_create.c:442)
==71298==    by 0x499DA03: clone (clone.S:100)
==71298==  Address 0x10c080 is 0 bytes inside data symbol "m2"
==71298== 
==71298== ERROR SUMMARY: 1 errors from 1 contexts (suppressed: 7 from 7)
```

- It reports that there is a lock order violation. It says that the lock at 0x10C040 was acquired before the lock at 0x10C080. But the required order was the opposite. This is the reason for the deadlock in the code.

------

##### 5. Now run helgrind on main-deadlock-global.c. Examine the code; does it have the same problem that `main-deadlock.c` has? Should helgrind be reporting the same error? What does this tell you about tools like helgrind?

```c
#include <stdio.h>

#include "common_threads.h"

pthread_mutex_t g = PTHREAD_MUTEX_INITIALIZER;
pthread_mutex_t m1 = PTHREAD_MUTEX_INITIALIZER;
pthread_mutex_t m2 = PTHREAD_MUTEX_INITIALIZER;

void* worker(void* arg) {
    Pthread_mutex_lock(&g);
    if ((long long) arg == 0) {
	Pthread_mutex_lock(&m1);
	Pthread_mutex_lock(&m2);
    } else {
	Pthread_mutex_lock(&m2);
	Pthread_mutex_lock(&m1);
    }
    Pthread_mutex_unlock(&m1);
    Pthread_mutex_unlock(&m2);
    Pthread_mutex_unlock(&g);
    return NULL;
}

int main(int argc, char *argv[]) {
    pthread_t p1, p2;
    Pthread_create(&p1, NULL, worker, (void *) (long long) 0);
    Pthread_create(&p2, NULL, worker, (void *) (long long) 1);
    Pthread_join(p1, NULL);
    Pthread_join(p2, NULL);
    return 0;
}
```

- The difference is that a global mutex is initialized, which ensures that only one thread at a time can lock m1 and m2. This means that the deadlock problem is solved because the threads are now synchronized. Each thread first locks the global mutex. While one thread holds this lock, the other one must wait. That means that only one thread at a time can execute the code where m1 and m2 are locked. Helgrind still reports an error because the lock order is violated and it does not know that the global mutex is used to synchronize the threads.

- Example Workflow: if arg == 0, thread 1 locks g, then m1, then m2, then releases them in the same order. If arg == 1, thread 2 locks g, then m2, then m1. This way, the threads are synchronized and the deadlock problem is solved.

------

##### 6. Let’s next look at main-signal.c. This code uses a variable (done) to signal that the child is done and that the parent can now continue. Why is this code inefficient? (what does the parent end up spending its time doing, particularly if the child thread takes a long time to complete?)

```c
#include <stdio.h>
#include "common_threads.h"

int done = 0;

void* worker(void* arg) {
    printf("this should print first\n");
    done = 1;
    return NULL;
}

int main(int argc, char *argv[]) {
    pthread_t p;
    Pthread_create(&p, NULL, worker, NULL);
    while (done == 0)
	;
    printf("this should print last\n");
    return 0;
}
```

- The parent thread is busy waiting for the child thread to finish. This is inefficient because the parent thread is not doing anything useful while waiting for the child thread to finish. The while loop in the main function is an example of a spinlock. The parent thread is spinning and checking the done variable in every iteration. This is inefficient because the parent thread is wasting CPU cycles. In this case, the parent thread is checking if done is 0 in every iteration.

------

##### 7. Now run helgrind on this program. What does it report? Is the code correct?

- Helgrind reports 24 errors in the code. This is because the done variable is not protected by a mutex. The child thread writes to the done variable and the parent thread reads from it. This is a race condition because the parent thread can read the done variable before the child thread writes to it. This can cause the parent thread to print "this should print last" before the child thread prints "this should print first".

------

##### 8. Now look at a slightly modified version of the code, which is found in `main-signal-cv.c`. This version uses a condition variable to do the signaling (and associated lock). Why is this code preferred to the previous version? Is it correctness, or performance, or both?

```c
#include <stdio.h>
#include "common_threads.h"

typedef struct __synchronizer_t {
    pthread_mutex_t lock;
    pthread_cond_t cond;
    int done;
} synchronizer_t;

synchronizer_t s;

void signal_init(synchronizer_t *s) {
    Pthread_mutex_init(&s->lock, NULL);
    Pthread_cond_init(&s->cond, NULL);
    s->done = 0;
}

void signal_done(synchronizer_t *s) {
    Pthread_mutex_lock(&s->lock);
    s->done = 1;
    Pthread_cond_signal(&s->cond);
    Pthread_mutex_unlock(&s->lock);
}

void signal_wait(synchronizer_t *s) {
    Pthread_mutex_lock(&s->lock);
    while (s->done == 0)
	Pthread_cond_wait(&s->cond, &s->lock);
    Pthread_mutex_unlock(&s->lock);
}

void* worker(void* arg) {
    printf("this should print first\n");
    signal_done(&s);
    return NULL;
}

int main(int argc, char *argv[]) {
    pthread_t p;
    signal_init(&s);
    Pthread_create(&p, NULL, worker, NULL);
    signal_wait(&s);
    printf("this should print last\n");

    return 0;
}
```

- This code is more efficient because the parent thread is not busy waiting for the child thread to finish. The parent thread waits for the child thread to finish by using a condition variable, the condition being the done variable set to 1 (then the parent thread can continue). This way, the parent thread is not wasting CPU cycles by spinning in a while loop. The parent thread is put to sleep by the condition variable and is woken up by the child thread when it is done.

- If we go step by step: The synchronizer is initalized, which includes a mutex and a condition variable. The child thread prints "this should print first" and then signals that it is done. The parent thread waits for the child thread to finish by calling signal_wait. The parent thread is put to sleep by the condition variable and is woken up by the child thread when it is done. Then the parent thread prints "this should print last".

------

##### 9. Once again run helgrind on main-signal-cv. Does it report any errors?

- Helgrind does not report any errors in the code. This is because the done variable is protected by a mutex and a condition variable. The child thread signals that it is done by calling signal_done. The parent thread waits for the child thread to finish by calling signal_wait. This way, the parent thread is not busy waiting for the child thread to finish.
