# Making it make sense :3

### What is vmstat and what does it tell me?
`virtual memory statistics` is a command line utility that reports information about many things.

#### Procs
- r: The number of processes running or waiting to run.
- b: The number of processes waiting for I/O to complete.

In my output the "r" value ranges from 0 to 2 and the "b" is constantly 0.

#### Memory
- swpd: The amount of virtual memory used.
- free: The amount of free physical memory.
- buff: The amount of memory used for buffering data.
- cache: The amount of memory used for caching data.

In my outut the "swpd" value is constantly 0, the "free" value ranges between very high numbers, the "buff" is stable at about 300 KB (consistent use for buffering) and the "cache"value is around 5,700,000 KB (consistent use for caching).

#### Swap
- si: The rate of swapping data from disk to memory.
- so: The rate of swapping data from memory to disk.

In my output, both are consistently 0, which aligns with the "swpd" value being 0.

#### IO
- bi: Blocks received from a block device (blocks/s).
- bo: Blocks sent to a block device (blocks/s).

In my output, both are generally 0, with small spikes indicating occasional disk activity.

#### System
- in: The number of interrupts per second, including the clock.
- cs: The number of context switches per second.

In my output, the "in" values vary with an average of around 7000, suggesting normal system activity. The "cs" values vary with an average of around 10000, indicating a moderate number of context switches.

#### CPU
- us: The percentage of CPU time spent on user mode.
- sy: The percentage of CPU time spent on kernel mode.
- id: The percentage of CPU time spent idle.
- wa: The percentage of CPU time spent waiting for I/O.
- st: The percentage of CPU time stolen from a virtual machine.

In my output, the "us" values are generally low, around 1-2%, indicating low CPU usage. The "sy" values are also low, indicating low user and kernel activity on the CPU. The "id" values are high, around 97-98%, suggesting low utilization. The "wa" value is 0, indicating no I/O wait time. The "st" values are consistently 0, indicating no CPU time stolen from a virtual machine.

# Questions

##### 1. Now, we will run the program mem.c but with very little memory usage. This can be accomplished by typing `./mem 1` (which uses only 1 MB of memory). How do the CPU usage statistics change when running mem? Do the numbers in the user time column make sense? How does this change when running more than one instance of mem at once?

- The value of "us" and "sy" are higher when running mem.c with very little memory usage. This is because the CPU is spending more time on user mode and kernel mode to manage the memory allocation and deallocation. The "us" value is higher than the "sy" value, indicating that the CPU is spending more time on user mode tasks (memory requests) than kernel mode tasks (memory management).
- When I run more than one instance of mem.c at once, the CPU usage statistics increase accordingly. The "us" and "sy" values increase with each additional instance, reflecting the additional load on the CPU to manage multiple memory allocations and deallocations.

----------------------

##### 2. Let’s now start looking at some of the memory statistics while running mem. We’ll focus on two columns: swpd (the amount of virtual memory used) and free (the amount of idle memory). Run `./mem 1024` (which allocates 1024 MB) and watch how these values change. Then kill the running program (by typing control-c) and watch again how the values change. What do you notice about the values? In particular, how does the free column change when the program exits? Does the amount of free memory increase by the expected amount when mem exits?

- When running `./mem 1024`, my "swpd" value remains 0, while the "free" value decreases as memory is allocated. When the program is killed, the "free" value increases back to its original value, indicating that the memory has been deallocated.

----------------------

##### 3. We’ll next look at the swap columns (si and so), which indicate how much swapping is taking place to and from the disk. Of course, to activate these, you’ll need to run mem with large amounts of memory. First, examine how much free memory is on your Linux system (for example, by typing cat /proc/meminfo; type man proc for details on the /proc file system and the types of information you can find there). One of the first entries in /proc/meminfo is the total amount of memory in your system. Let’s assume it’s something like 8 GB of memory; if so, start by running mem 4000 (about 4 GB) and watching the swap in/out columns. Do they ever give non-zero values? Then, try with 5000, 6000, etc. What happens to these values as the program enters the second loop (and beyond), as compared to the first loop? How much data (total) are swapped in and out during the second, third, and subsequent loops? (do the numbers make sense?)

- When running `./mem 8000`, the "si" and "so" values remain 0, indicating that no swapping is taking place. This is because the system has enough physical memory to accommodate the memory allocation.
- The job terminates with a signal by SIGKILL when I run it with `1300`, although I have about 16 GB in total, which is sent by the kernel when the system is running out of memory. This is because the system has a limited amount of physical memory and swap space, and the memory allocation exceeds the available resources.
- The value of "so" remains 0 after the first few loops where it reaches some sort of spike.
- The numbers seem rather random to me.

----------------------

##### 4. Do the same experiments as above, but now watch the other statistics (such a CPU utilization, and block I/O statistics). How do they change when mem is running?

- When running `./mem 8000`, the CPU utilization increases slightly, with the "us" and "sy" values increasing as the CPU spends more time on user mode and kernel mode tasks. The "bi" and "bo" values also increase slightly, indicating more block I/O activity as the memory allocation and deallocation take place.
