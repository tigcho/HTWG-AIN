# Antworten

## Aufgabe 1

```
valgrind --tool=helgrind ./main-race
==10854== Helgrind, a thread error detector
==10854== Copyright (C) 2007-2017, and GNU GPL'd, by OpenWorks LLP et al.
==10854== Using Valgrind-3.19.0 and LibVEX; rerun with -h for copyright info
==10854== Command: ./main-race
==10854== 
==10854== ---Thread-Announcement------------------------------------------
==10854== 
==10854== Thread #1 is the program's root thread
==10854== 
==10854== ---Thread-Announcement------------------------------------------
==10854== 
==10854== Thread #2 was created
==10854==    at 0x4988513: clone (clone.S:76)
==10854==    by 0x4989410: __clone_internal (clone-internal.c:83)
==10854==    by 0x4903A1F: create_thread (pthread_create.c:295)
==10854==    by 0x4904546: pthread_create@@GLIBC_2.34 (pthread_create.c:828)
==10854==    by 0x48503C4: pthread_create_WRK (hg_intercepts.c:445)
==10854==    by 0x48518E5: pthread_create@* (hg_intercepts.c:478)
==10854==    by 0x40118D: main (main-race.c:14)
==10854== 
==10854== ----------------------------------------------------------------
==10854== 
==10854== Possible data race during read of size 4 at 0x404038 by thread #1
==10854== Locks held: none
==10854==    at 0x4011AB: main (main-race.c:15)                                     # Fehler in Zeile 15
==10854== 
==10854== This conflicts with a previous write of size 4 by thread #2
==10854== Locks held: none
==10854==    at 0x401157: worker (main-race.c:8)                                    # Fehler in Zeile 8
==10854==    by 0x48505BE: mythread_wrapper (hg_intercepts.c:406)
==10854==    by 0x4903DEC: start_thread (pthread_create.c:442)
==10854==    by 0x4988523: clone (clone.S:100)
==10854==  Address 0x404038 is 0 bytes inside data symbol "balance"
==10854== 
==10854== ----------------------------------------------------------------
==10854== 
==10854== Possible data race during write of size 4 at 0x404038 by thread #1
==10854== Locks held: none
==10854==    at 0x4011B4: main (main-race.c:15)                                     # Fehler in Zeile 15
==10854== 
==10854== This conflicts with a previous write of size 4 by thread #2
==10854== Locks held: none
==10854==    at 0x401157: worker (main-race.c:8)                                    # Fehler in Zeile 8
==10854==    by 0x48505BE: mythread_wrapper (hg_intercepts.c:406)
==10854==    by 0x4903DEC: start_thread (pthread_create.c:442)
==10854==    by 0x4988523: clone (clone.S:100)
==10854==  Address 0x404038 is 0 bytes inside data symbol "balance"
==10854== 
==10854== 
==10854== Use --history-level=approx or =none to gain increased speed, at
==10854== the cost of reduced accuracy of conflicting-access information
==10854== For lists of detected and suppressed errors, rerun with: -s
==10854== ERROR SUMMARY: 2 errors from 2 contexts (suppressed: 0 from 0)            # Zusammenfassung
```

Es werden mögliche race conditions angezeigt, sowie welche Threads angelegt werden und weitere passende Informationen wie z. B. Locks.

## Aufgabe 2

Wenn die beiden Zeilen entfernt werden, bei denen balance hochgezählt wird, verschwinden logischerweise auch die Fehlermeldungen.

Wenn nur eine Zeile mit einem Lock geschützt wird, gibt es immernoch Fehler, weil durch den anderen ungeschützten Zugriff immer noch race conditions auftreten können.

```
valgrind --tool=helgrind ./main-race
==12109== Helgrind, a thread error detector
==12109== Copyright (C) 2007-2017, and GNU GPL'd, by OpenWorks LLP et al.
==12109== Using Valgrind-3.19.0 and LibVEX; rerun with -h for copyright info
==12109== Command: ./main-race
==12109== 
==12109== ---Thread-Announcement------------------------------------------
==12109== 
==12109== Thread #1 is the program's root thread
==12109== 
==12109== ---Thread-Announcement------------------------------------------
==12109== 
==12109== Thread #2 was created
==12109==    at 0x4988513: clone (clone.S:76)
==12109==    by 0x4989410: __clone_internal (clone-internal.c:83)
==12109==    by 0x4903A1F: create_thread (pthread_create.c:295)
==12109==    by 0x4904546: pthread_create@@GLIBC_2.34 (pthread_create.c:828)
==12109==    by 0x48503C4: pthread_create_WRK (hg_intercepts.c:445)
==12109==    by 0x48518E5: pthread_create@* (hg_intercepts.c:478)
==12109==    by 0x4011FF: main (main-race.c:18)
==12109== 
==12109== ----------------------------------------------------------------
==12109== 
==12109==  Lock at 0x4040A0 was first observed
==12109==    at 0x484D5A8: mutex_lock_WRK (hg_intercepts.c:942)
==12109==    by 0x4851CF2: pthread_mutex_lock (hg_intercepts.c:958)
==12109==    by 0x40117B: worker (main-race.c:10)
==12109==    by 0x48505BE: mythread_wrapper (hg_intercepts.c:406)
==12109==    by 0x4903DEC: start_thread (pthread_create.c:442)
==12109==    by 0x4988523: clone (clone.S:100)
==12109==  Address 0x4040a0 is 0 bytes inside data symbol "lock"
==12109== 
==12109== Possible data race during read of size 4 at 0x404080 by thread #1
==12109== Locks held: none
==12109==    at 0x40121D: main (main-race.c:20)
==12109== 
==12109== This conflicts with a previous write of size 4 by thread #2
==12109== Locks held: 1, at address 0x4040A0
==12109==    at 0x4011A2: worker (main-race.c:11)
==12109==    by 0x48505BE: mythread_wrapper (hg_intercepts.c:406)
==12109==    by 0x4903DEC: start_thread (pthread_create.c:442)
==12109==    by 0x4988523: clone (clone.S:100)
==12109==  Address 0x404080 is 0 bytes inside data symbol "balance"
==12109== 
==12109== ----------------------------------------------------------------
==12109== 
==12109==  Lock at 0x4040A0 was first observed
==12109==    at 0x484D5A8: mutex_lock_WRK (hg_intercepts.c:942)
==12109==    by 0x4851CF2: pthread_mutex_lock (hg_intercepts.c:958)
==12109==    by 0x40117B: worker (main-race.c:10)
==12109==    by 0x48505BE: mythread_wrapper (hg_intercepts.c:406)
==12109==    by 0x4903DEC: start_thread (pthread_create.c:442)
==12109==    by 0x4988523: clone (clone.S:100)
==12109==  Address 0x4040a0 is 0 bytes inside data symbol "lock"
==12109== 
==12109== Possible data race during write of size 4 at 0x404080 by thread #1
==12109== Locks held: none
==12109==    at 0x401226: main (main-race.c:20)
==12109== 
==12109== This conflicts with a previous write of size 4 by thread #2
==12109== Locks held: 1, at address 0x4040A0
==12109==    at 0x4011A2: worker (main-race.c:11)
==12109==    by 0x48505BE: mythread_wrapper (hg_intercepts.c:406)
==12109==    by 0x4903DEC: start_thread (pthread_create.c:442)
==12109==    by 0x4988523: clone (clone.S:100)
==12109==  Address 0x404080 is 0 bytes inside data symbol "balance"
==12109== 
==12109== 
==12109== Use --history-level=approx or =none to gain increased speed, at
==12109== the cost of reduced accuracy of conflicting-access information
==12109== For lists of detected and suppressed errors, rerun with: -s
==12109== ERROR SUMMARY: 2 errors from 2 contexts (suppressed: 0 from 0)
```

Erst wenn beide Zugriffe abgesichert werden, liefert helgrind keine Fehler mehr.

```
valgrind --tool=helgrind ./main-race
==12239== Helgrind, a thread error detector
==12239== Copyright (C) 2007-2017, and GNU GPL'd, by OpenWorks LLP et al.
==12239== Using Valgrind-3.19.0 and LibVEX; rerun with -h for copyright info
==12239== Command: ./main-race
==12239== 
==12239== 
==12239== Use --history-level=approx or =none to gain increased speed, at
==12239== the cost of reduced accuracy of conflicting-access information
==12239== For lists of detected and suppressed errors, rerun with: -s
==12239== ERROR SUMMARY: 0 errors from 0 contexts (suppressed: 7 from 7)
```

## Aufgabe 3

 Beide Threads können einen Lock benutzen und sich so gegenseitig blockieren.
 Z. B. ruft der erste Thread zuerst Lock 1 und dann Lock 2 auf. Währenddessen ruft Thread 2 erst Lock 2 und dann Lock 1 auf. Die jeweils zweiten Lockaufrufe können aber schon von dem jeweils anderen Thread ausgelöst worden sein, weshalb bei beiden die Ausführung des Codes stecken bleibt.

 ## Aufgabe 4

 ```
 valgrind --tool=helgrind ./main-deadlock
==12607== Helgrind, a thread error detector
==12607== Copyright (C) 2007-2017, and GNU GPL'd, by OpenWorks LLP et al.
==12607== Using Valgrind-3.19.0 and LibVEX; rerun with -h for copyright info
==12607== Command: ./main-deadlock
==12607== 
==12607== ---Thread-Announcement------------------------------------------
==12607== 
==12607== Thread #3 was created
==12607==    at 0x4988513: clone (clone.S:76)
==12607==    by 0x4989410: __clone_internal (clone-internal.c:83)
==12607==    by 0x4903A1F: create_thread (pthread_create.c:295)
==12607==    by 0x4904546: pthread_create@@GLIBC_2.34 (pthread_create.c:828)
==12607==    by 0x48503C4: pthread_create_WRK (hg_intercepts.c:445)
==12607==    by 0x48518E5: pthread_create@* (hg_intercepts.c:478)
==12607==    by 0x4012CB: main (main-deadlock.c:24)
==12607== 
==12607== ----------------------------------------------------------------
==12607== 
==12607== Thread #3: lock order "0x404080 before 0x4040C0" violated
==12607== 
==12607== Observed (incorrect) order is: acquisition of lock at 0x4040C0
==12607==    at 0x484D5A8: mutex_lock_WRK (hg_intercepts.c:942)
==12607==    by 0x4851CF2: pthread_mutex_lock (hg_intercepts.c:958)
==12607==    by 0x4011D0: worker (main-deadlock.c:13)
==12607==    by 0x48505BE: mythread_wrapper (hg_intercepts.c:406)
==12607==    by 0x4903DEC: start_thread (pthread_create.c:442)
==12607==    by 0x4988523: clone (clone.S:100)
==12607== 
==12607==  followed by a later acquisition of lock at 0x404080
==12607==    at 0x484D5A8: mutex_lock_WRK (hg_intercepts.c:942)
==12607==    by 0x4851CF2: pthread_mutex_lock (hg_intercepts.c:958)
==12607==    by 0x4011F7: worker (main-deadlock.c:14)
==12607==    by 0x48505BE: mythread_wrapper (hg_intercepts.c:406)
==12607==    by 0x4903DEC: start_thread (pthread_create.c:442)
==12607==    by 0x4988523: clone (clone.S:100)
==12607== 
==12607== Required order was established by acquisition of lock at 0x404080
==12607==    at 0x484D5A8: mutex_lock_WRK (hg_intercepts.c:942)
==12607==    by 0x4851CF2: pthread_mutex_lock (hg_intercepts.c:958)
==12607==    by 0x401182: worker (main-deadlock.c:10)
==12607==    by 0x48505BE: mythread_wrapper (hg_intercepts.c:406)
==12607==    by 0x4903DEC: start_thread (pthread_create.c:442)
==12607==    by 0x4988523: clone (clone.S:100)
==12607== 
==12607==  followed by a later acquisition of lock at 0x4040C0
==12607==    at 0x484D5A8: mutex_lock_WRK (hg_intercepts.c:942)
==12607==    by 0x4851CF2: pthread_mutex_lock (hg_intercepts.c:958)
==12607==    by 0x4011A9: worker (main-deadlock.c:11)
==12607==    by 0x48505BE: mythread_wrapper (hg_intercepts.c:406)
==12607==    by 0x4903DEC: start_thread (pthread_create.c:442)
==12607==    by 0x4988523: clone (clone.S:100)
==12607== 
==12607==  Lock at 0x404080 was first observed
==12607==    at 0x484D5A8: mutex_lock_WRK (hg_intercepts.c:942)
==12607==    by 0x4851CF2: pthread_mutex_lock (hg_intercepts.c:958)
==12607==    by 0x401182: worker (main-deadlock.c:10)
==12607==    by 0x48505BE: mythread_wrapper (hg_intercepts.c:406)
==12607==    by 0x4903DEC: start_thread (pthread_create.c:442)
==12607==    by 0x4988523: clone (clone.S:100)
==12607==  Address 0x404080 is 0 bytes inside data symbol "m1"
==12607== 
==12607==  Lock at 0x4040C0 was first observed
==12607==    at 0x484D5A8: mutex_lock_WRK (hg_intercepts.c:942)
==12607==    by 0x4851CF2: pthread_mutex_lock (hg_intercepts.c:958)
==12607==    by 0x4011A9: worker (main-deadlock.c:11)
==12607==    by 0x48505BE: mythread_wrapper (hg_intercepts.c:406)
==12607==    by 0x4903DEC: start_thread (pthread_create.c:442)
==12607==    by 0x4988523: clone (clone.S:100)
==12607==  Address 0x4040c0 is 0 bytes inside data symbol "m2"
==12607== 
==12607== 
==12607== 
==12607== Use --history-level=approx or =none to gain increased speed, at
==12607== the cost of reduced accuracy of conflicting-access information
==12607== For lists of detected and suppressed errors, rerun with: -s
==12607== ERROR SUMMARY: 1 errors from 1 contexts (suppressed: 7 from 7)
``` 

Es wird beschrieben, wo das Programm die so genannte Lock order verletzt.

## Aufgabe 5

```
valgrind --tool=helgrind ./main-deadlock-global 
==12762== Helgrind, a thread error detector
==12762== Copyright (C) 2007-2017, and GNU GPL'd, by OpenWorks LLP et al.
==12762== Using Valgrind-3.19.0 and LibVEX; rerun with -h for copyright info
==12762== Command: ./main-deadlock-global
==12762== 
==12762== ---Thread-Announcement------------------------------------------
==12762== 
==12762== Thread #3 was created
==12762==    at 0x4988513: clone (clone.S:76)
==12762==    by 0x4989410: __clone_internal (clone-internal.c:83)
==12762==    by 0x4903A1F: create_thread (pthread_create.c:295)
==12762==    by 0x4904546: pthread_create@@GLIBC_2.34 (pthread_create.c:828)
==12762==    by 0x48503C4: pthread_create_WRK (hg_intercepts.c:445)
==12762==    by 0x48518E5: pthread_create@* (hg_intercepts.c:478)
==12762==    by 0x401319: main (main-deadlock-global.c:27)
==12762== 
==12762== ----------------------------------------------------------------
==12762== 
==12762== Thread #3: lock order "0x4040C0 before 0x404100" violated
==12762== 
==12762== Observed (incorrect) order is: acquisition of lock at 0x404100
==12762==    at 0x484D5A8: mutex_lock_WRK (hg_intercepts.c:942)
==12762==    by 0x4851CF2: pthread_mutex_lock (hg_intercepts.c:958)
==12762==    by 0x4011F7: worker (main-deadlock-global.c:15)
==12762==    by 0x48505BE: mythread_wrapper (hg_intercepts.c:406)
==12762==    by 0x4903DEC: start_thread (pthread_create.c:442)
==12762==    by 0x4988523: clone (clone.S:100)
==12762== 
==12762==  followed by a later acquisition of lock at 0x4040C0
==12762==    at 0x484D5A8: mutex_lock_WRK (hg_intercepts.c:942)
==12762==    by 0x4851CF2: pthread_mutex_lock (hg_intercepts.c:958)
==12762==    by 0x40121E: worker (main-deadlock-global.c:16)
==12762==    by 0x48505BE: mythread_wrapper (hg_intercepts.c:406)
==12762==    by 0x4903DEC: start_thread (pthread_create.c:442)
==12762==    by 0x4988523: clone (clone.S:100)
==12762== 
==12762== Required order was established by acquisition of lock at 0x4040C0
==12762==    at 0x484D5A8: mutex_lock_WRK (hg_intercepts.c:942)
==12762==    by 0x4851CF2: pthread_mutex_lock (hg_intercepts.c:958)
==12762==    by 0x4011A9: worker (main-deadlock-global.c:12)
==12762==    by 0x48505BE: mythread_wrapper (hg_intercepts.c:406)
==12762==    by 0x4903DEC: start_thread (pthread_create.c:442)
==12762==    by 0x4988523: clone (clone.S:100)
==12762== 
==12762==  followed by a later acquisition of lock at 0x404100
==12762==    at 0x484D5A8: mutex_lock_WRK (hg_intercepts.c:942)
==12762==    by 0x4851CF2: pthread_mutex_lock (hg_intercepts.c:958)
==12762==    by 0x4011D0: worker (main-deadlock-global.c:13)
==12762==    by 0x48505BE: mythread_wrapper (hg_intercepts.c:406)
==12762==    by 0x4903DEC: start_thread (pthread_create.c:442)
==12762==    by 0x4988523: clone (clone.S:100)
==12762== 
==12762==  Lock at 0x4040C0 was first observed
==12762==    at 0x484D5A8: mutex_lock_WRK (hg_intercepts.c:942)
==12762==    by 0x4851CF2: pthread_mutex_lock (hg_intercepts.c:958)
==12762==    by 0x4011A9: worker (main-deadlock-global.c:12)
==12762==    by 0x48505BE: mythread_wrapper (hg_intercepts.c:406)
==12762==    by 0x4903DEC: start_thread (pthread_create.c:442)
==12762==    by 0x4988523: clone (clone.S:100)
==12762==  Address 0x4040c0 is 0 bytes inside data symbol "m1"
==12762== 
==12762==  Lock at 0x404100 was first observed
==12762==    at 0x484D5A8: mutex_lock_WRK (hg_intercepts.c:942)
==12762==    by 0x4851CF2: pthread_mutex_lock (hg_intercepts.c:958)
==12762==    by 0x4011D0: worker (main-deadlock-global.c:13)
==12762==    by 0x48505BE: mythread_wrapper (hg_intercepts.c:406)
==12762==    by 0x4903DEC: start_thread (pthread_create.c:442)
==12762==    by 0x4988523: clone (clone.S:100)
==12762==  Address 0x404100 is 0 bytes inside data symbol "m2"
==12762== 
==12762== 
==12762== 
==12762== Use --history-level=approx or =none to gain increased speed, at
==12762== the cost of reduced accuracy of conflicting-access information
==12762== For lists of detected and suppressed errors, rerun with: -s
==12762== ERROR SUMMARY: 1 errors from 1 contexts (suppressed: 7 from 7)
```

Helgrind gibt denselben Fehler aus, obwohl es das nicht sollte. Es zeigt, das auch helgrind nicht perfekt ist.

## Aufgabe 6

Dies ist nicht effizient, da so im "Main-Thread" CPU-Cyclen verschwendet werden mit der while-Schleife.

## Aufgabe 7

```
valgrind --tool=helgrind ./main-signal
==13171== Helgrind, a thread error detector
==13171== Copyright (C) 2007-2017, and GNU GPL'd, by OpenWorks LLP et al.
==13171== Using Valgrind-3.19.0 and LibVEX; rerun with -h for copyright info
==13171== Command: ./main-signal
==13171== 
this should print first
==13171== ---Thread-Announcement------------------------------------------
==13171== 
==13171== Thread #1 is the program's root thread
==13171== 
==13171== ---Thread-Announcement------------------------------------------
==13171== 
==13171== Thread #2 was created
==13171==    at 0x4988513: clone (clone.S:76)
==13171==    by 0x4989410: __clone_internal (clone-internal.c:83)
==13171==    by 0x4903A1F: create_thread (pthread_create.c:295)
==13171==    by 0x4904546: pthread_create@@GLIBC_2.34 (pthread_create.c:828)
==13171==    by 0x48503C4: pthread_create_WRK (hg_intercepts.c:445)
==13171==    by 0x48518E5: pthread_create@* (hg_intercepts.c:478)
==13171==    by 0x401196: main (main-signal.c:15)
==13171== 
==13171== ----------------------------------------------------------------
==13171== 
==13171== Possible data race during read of size 4 at 0x404038 by thread #1
==13171== Locks held: none
==13171==    at 0x4011B5: main (main-signal.c:16)
==13171== 
==13171== This conflicts with a previous write of size 4 by thread #2
==13171== Locks held: none
==13171==    at 0x40115C: worker (main-signal.c:9)
==13171==    by 0x48505BE: mythread_wrapper (hg_intercepts.c:406)
==13171==    by 0x4903DEC: start_thread (pthread_create.c:442)
==13171==    by 0x4988523: clone (clone.S:100)
==13171==  Address 0x404038 is 0 bytes inside data symbol "done"
==13171== 
this should print last
==13171== 
==13171== Use --history-level=approx or =none to gain increased speed, at
==13171== the cost of reduced accuracy of conflicting-access information
==13171== For lists of detected and suppressed errors, rerun with: -s
==13171== ERROR SUMMARY: 1 errors from 1 contexts (suppressed: 62 from 36)
```

Helgrind meldet eine race condition mit der variable done, weil hier nicht gewartet wird, bis der erzeugt thread fertig ist. Vor ineffizientem Code warnt helgrind aber nicht.

## Aufgabe 8

Der code ist in bisschen Korrekter, obwohl immer noch nicht auf den Thread mit join gewartet wird. Der Code ist aber deutlich effizienter mit dem Signal-Wait-Mechanismus.

## Aufgabe 9

Heldgrind liefert keine Fehler.

```
valgrind --tool=helgrind ./main-signal-cv 
==13442== Helgrind, a thread error detector
==13442== Copyright (C) 2007-2017, and GNU GPL'd, by OpenWorks LLP et al.
==13442== Using Valgrind-3.19.0 and LibVEX; rerun with -h for copyright info
==13442== Command: ./main-signal-cv
==13442== 
this should print first
this should print last
==13442== 
==13442== Use --history-level=approx or =none to gain increased speed, at
==13442== the cost of reduced accuracy of conflicting-access information
==13442== For lists of detected and suppressed errors, rerun with: -s
==13442== ERROR SUMMARY: 0 errors from 0 contexts (suppressed: 7 from 7)
```