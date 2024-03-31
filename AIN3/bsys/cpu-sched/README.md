# Questions

1. Compute response time and turnaround time when running three jobs of length
   20 with the SJF and FIFO schedulers.

- Both schedulers have the same results because the jobs have the same length. The
  only difference is that SJF will sort the jobs by length before running them. 

Output (same for SJF):
```sh
❯ ./scheduler.py -p FIFO -l 200,200,200 -c
ARG policy FIFO
ARG jlist 200,200,200

Here is the job list, with the run time of each job: 
  Job 0 ( length = 200.0 )
  Job 1 ( length = 200.0 )
  Job 2 ( length = 200.0 )


** Solutions **

Execution trace:
  [ time   0 ] Run job 0 for 200.00 secs ( DONE at 200.00 )
  [ time 200 ] Run job 1 for 200.00 secs ( DONE at 400.00 )
  [ time 400 ] Run job 2 for 200.00 secs ( DONE at 600.00 )

Final statistics:
  Job   0 -- Response: 0.00  Turnaround 200.00  Wait 0.00
  Job   1 -- Response: 200.00  Turnaround 400.00  Wait 200.00
  Job   2 -- Response: 400.00  Turnaround 600.00  Wait 400.00

  Average -- Response: 200.00  Turnaround 400.00  Wait 200.00
  ```
--------------------------

2. Now do the same but with jobs of different lengths: 100, 200, and 300.

- The SJF scheduler will sort the jobs by length before running them, but the
  results will be the same as FIFO because the jobs are already sorted by length.

Output (FIFO):
```sh
Execution trace:
  [ time   0 ] Run job 0 for 100.00 secs ( DONE at 100.00 )
  [ time 100 ] Run job 1 for 200.00 secs ( DONE at 300.00 )
  [ time 300 ] Run job 2 for 300.00 secs ( DONE at 600.00 )

Final statistics:
  Job   0 -- Response: 0.00  Turnaround 100.00  Wait 0.00
  Job   1 -- Response: 100.00  Turnaround 300.00  Wait 100.00
  Job   2 -- Response: 300.00  Turnaround 600.00  Wait 300.00

  Average -- Response: 133.33  Turnaround 333.33  Wait 133.33
```

Output (SJF):
```sh
Execution trace:
  [ time   0 ] Run job 0 for 100.00 secs ( DONE at 100.00 )
  [ time 100 ] Run job 1 for 200.00 secs ( DONE at 300.00 )
  [ time 300 ] Run job 2 for 300.00 secs ( DONE at 600.00 )

Final statistics:
  Job   0 -- Response: 0.00  Turnaround 100.00  Wait 0.00
  Job   1 -- Response: 100.00  Turnaround 300.00  Wait 100.00
  Job   2 -- Response: 300.00  Turnaround 600.00  Wait 300.00

  Average -- Response: 133.33  Turnaround 333.33  Wait 133.33
```
--------------------------

3. Now do the same but also with the RR scheduler and a time-slice of 1.

- The RR scheduler will run each job for 1 second and then move to the next job
  until all jobs are done. 

Output (RR):
```sh
❯ ./scheduler.py -p RR -l 200,200,200 -c -q 1
Final statistics:
  Job   0 -- Response: 0.00  Turnaround 598.00  Wait 398.00
  Job   1 -- Response: 1.00  Turnaround 599.00  Wait 399.00
  Job   2 -- Response: 2.00  Turnaround 600.00  Wait 400.00

  Average -- Response: 1.00  Turnaround 599.00  Wait 399.00

❯ ./scheduler.py -p RR -l 100,200,300 -c -q 1
Final statistics:
  Job   0 -- Response: 0.00  Turnaround 298.00  Wait 198.00
  Job   1 -- Response: 1.00  Turnaround 499.00  Wait 299.00
  Job   2 -- Response: 2.00  Turnaround 600.00  Wait 300.00

  Average -- Response: 1.00  Turnaround 465.67  Wait 265.67
```
--------------------------

4. For what types of workloads does SJF deliver the same turnaround
   times as FIFO?

- SJF will deliver the same turnaround times as FIFO when the jobs are already
  sorted by length or have the same length.
--------------------------

5. For what types of workloads and quantum lengths does SJF deliver
   the same response times as RR?

- SJF will deliver the same response times as RR when the jobs are sorted by length/
  have the same length and the quantum length is 1.
--------------------------

6. What happens to response time with SJF as job lengths increase?
   Can you use the simulator to demonstrate the trend?

- The response time will increase as the job lengths increase because the jobs will
  take longer to run.

Output:
```sh
❯ ./scheduler.py -p SJF -l 1,2,3
Average -- Response: 1.33

❯ ./scheduler.py -p SJF -l 2,4,6 
Average -- Response: 2.67

❯ ./scheduler.py -p SJF -l 4,8,12
Average -- Response: 5.33
```
--------------------------

7. What happens to response time with RR as quantum lengths 
   crease? Can you write an equation that gives the worst-case 
   response time, given N jobs?

- The response time will increase as the quantum lengths increase because the jobs
  are allowed to run longer.














