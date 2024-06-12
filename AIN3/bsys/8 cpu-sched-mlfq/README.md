# Questions

1. Run a few randomly-generated problems with just two jobs and two queues; 
   compute the MLFQ execution trace for each. Make your life easier by 
   limiting the length of each job and turning off I/Os.

Output:
```sh
❯ python3 mlfq.py -M 0 -m 20 -n 2 -j 2 -s 5 -c 
Here is the list of inputs:
OPTIONS jobs 2
OPTIONS queues 2
OPTIONS allotments for queue  1 is   1
OPTIONS quantum length for queue  1 is  10
OPTIONS allotments for queue  0 is   1
OPTIONS quantum length for queue  0 is  10
OPTIONS boost 0
OPTIONS ioTime 5
OPTIONS stayAfterIO False
OPTIONS iobump False

Job List:
  Job  0: startTime   0 - runTime  12 - ioFreq   0
  Job  1: startTime   0 - runTime  16 - ioFreq   0


Execution Trace:

[ time 0 ] JOB BEGINS by JOB 0
[ time 0 ] JOB BEGINS by JOB 1
[ time 0 ] Run JOB 0 at PRIORITY 1 [ TICKS 9 ALLOT 1 TIME 11 (of 12) ]
[ time 1 ] Run JOB 0 at PRIORITY 1 [ TICKS 8 ALLOT 1 TIME 10 (of 12) ]
[ time 2 ] Run JOB 0 at PRIORITY 1 [ TICKS 7 ALLOT 1 TIME 9 (of 12) ]
[ time 3 ] Run JOB 0 at PRIORITY 1 [ TICKS 6 ALLOT 1 TIME 8 (of 12) ]
[ time 4 ] Run JOB 0 at PRIORITY 1 [ TICKS 5 ALLOT 1 TIME 7 (of 12) ]
[ time 5 ] Run JOB 0 at PRIORITY 1 [ TICKS 4 ALLOT 1 TIME 6 (of 12) ]
[ time 6 ] Run JOB 0 at PRIORITY 1 [ TICKS 3 ALLOT 1 TIME 5 (of 12) ]
[ time 7 ] Run JOB 0 at PRIORITY 1 [ TICKS 2 ALLOT 1 TIME 4 (of 12) ]
[ time 8 ] Run JOB 0 at PRIORITY 1 [ TICKS 1 ALLOT 1 TIME 3 (of 12) ]
[ time 9 ] Run JOB 0 at PRIORITY 1 [ TICKS 0 ALLOT 1 TIME 2 (of 12) ]
[ time 10 ] Run JOB 1 at PRIORITY 1 [ TICKS 9 ALLOT 1 TIME 15 (of 16) ]
[ time 11 ] Run JOB 1 at PRIORITY 1 [ TICKS 8 ALLOT 1 TIME 14 (of 16) ]
[ time 12 ] Run JOB 1 at PRIORITY 1 [ TICKS 7 ALLOT 1 TIME 13 (of 16) ]
[ time 13 ] Run JOB 1 at PRIORITY 1 [ TICKS 6 ALLOT 1 TIME 12 (of 16) ]
[ time 14 ] Run JOB 1 at PRIORITY 1 [ TICKS 5 ALLOT 1 TIME 11 (of 16) ]
[ time 15 ] Run JOB 1 at PRIORITY 1 [ TICKS 4 ALLOT 1 TIME 10 (of 16) ]
[ time 16 ] Run JOB 1 at PRIORITY 1 [ TICKS 3 ALLOT 1 TIME 9 (of 16) ]
[ time 17 ] Run JOB 1 at PRIORITY 1 [ TICKS 2 ALLOT 1 TIME 8 (of 16) ]
[ time 18 ] Run JOB 1 at PRIORITY 1 [ TICKS 1 ALLOT 1 TIME 7 (of 16) ]
[ time 19 ] Run JOB 1 at PRIORITY 1 [ TICKS 0 ALLOT 1 TIME 6 (of 16) ]
[ time 20 ] Run JOB 0 at PRIORITY 0 [ TICKS 9 ALLOT 1 TIME 1 (of 12) ]
[ time 21 ] Run JOB 0 at PRIORITY 0 [ TICKS 8 ALLOT 1 TIME 0 (of 12) ]
[ time 22 ] FINISHED JOB 0
[ time 22 ] Run JOB 1 at PRIORITY 0 [ TICKS 9 ALLOT 1 TIME 5 (of 16) ]
[ time 23 ] Run JOB 1 at PRIORITY 0 [ TICKS 8 ALLOT 1 TIME 4 (of 16) ]
[ time 24 ] Run JOB 1 at PRIORITY 0 [ TICKS 7 ALLOT 1 TIME 3 (of 16) ]
[ time 25 ] Run JOB 1 at PRIORITY 0 [ TICKS 6 ALLOT 1 TIME 2 (of 16) ]
[ time 26 ] Run JOB 1 at PRIORITY 0 [ TICKS 5 ALLOT 1 TIME 1 (of 16) ]
[ time 27 ] Run JOB 1 at PRIORITY 0 [ TICKS 4 ALLOT 1 TIME 0 (of 16) ]
[ time 28 ] FINISHED JOB 1

Final statistics:
  Job  0: startTime   0 - response   0 - turnaround  22
  Job  1: startTime   0 - response  10 - turnaround  28

  Avg  1: startTime n/a - response 5.00 - turnaround 25.00
```
-------------------------------

2. How would you run the scheduler to reproduce each of the examples in the chapter?

- Example 1: A Single Long-Running Job 
```sh
❯ python3 mlfq.py -n 3 -q 10 -l 0,200,0 -c
```

- Example 2: Along Came A Short Job
```sh
❯ python3 mlfq.py -n 3 -q 10 -l 0,180,0:100,20,0  -c
```

- Example 3: What About I/O? (This is probably wrong, no idea)
```sh
❯ python3 mlfq.py -n 3 -q 10 -l 0,175,0:50,25,1  -c
```
-------------------------------

3. How would you configure the scheduler parameters to behave just
   like a round-robin scheduler?

?
-------------------------------

4. Craft a workload with two jobs and scheduler parameters so that
   one job takes advantage of the older Rules 4a and 4b (turned on
   with the -S flag) to game the scheduler and obtain 99% of the CPU
   over a particular time interval.

```sh
❯ python3 mlfq.py -n 3 -q 10 -l [???] -S -c
```
-------------------------------

5. Given a system with a quantum length of 10 ms in its highest queue,
   how often would you have to boost jobs back to the highest priority
   level (with the -B flag) in order to guarantee that a single long-
   running (and potentially-starving) job gets at least 5% of the CPU?

?
-------------------------------

6. One question that arises in scheduling is which end of a queue to
   add a job that just finished I/O; the -I flag changes this behavior
   for this scheduling simulator. Play around with some workloads
   and see if you can see the effect of this flag.

?

