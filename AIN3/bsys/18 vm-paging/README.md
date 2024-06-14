# Questions

##### 1. Before doing any translations, let’s use the simulator to study how linear page tables change size given different parameters. Compute the size of linear page tables as different parameters change. Some suggested inputs are below; by using the -v flag, you can see how many page-table entries are filled. First, to understand how linear page table size changes as the address space grows, run with these flags:

```sh
-P 1k -a 1m -p 512m -v -n 0
-P 1k -a 2m -p 512m -v -n 0
-P 1k -a 4m -p 512m -v -n 0
```

##### Then, to understand how linear page table size changes as page size grows:

```sh
-P 1k -a 1m -p 512m -v -n 0
-P 2k -a 1m -p 512m -v -n 0
-P 4k -a 1m -p 512m -v -n 0
```

- As the address space grows, the page table size grows linearly. This is because the page table needs to store the mapping of each page in the address space to a frame in memory. As the address space grows, the page table needs to store more mappings, which increases the size of the page table. 

- We don't want to use big page sizes because it can lead to internal fragmentation. If a program doesn't need much memory to begin with, then allocating a big page would be a waste. I assume the chance of page faults would also be higher than usual. Also, when a large page is swapped out, it consumes more disk space and it would take longer to load back into memory.

------------------------------------

##### 2. Now let’s do some translations. Start with some small examples, and change the number of pages that are allocated to the address space with the -u flag. For example:

```sh
-P 1k -a 16k -p 32k -v -u 0
-P 1k -a 16k -p 32k -v -u 25
-P 1k -a 16k -p 32k -v -u 50
-P 1k -a 16k -p 32k -v -u 75
-P 1k -a 16k -p 32k -v -u 100
```

##### What happens as you increase the percentage of pages that are allocated in each address space?

- As the percentage of pages that are allocated in each address space increases, the page table size also increases. This is because the page table needs to store the mapping of each page in the address space to a frame in memory. As more pages are allocated, the page table needs to store more mappings, which increases the size of the page table.

------------------------------------

##### 3. Now let’s try some different random seeds, and some different (and sometimes quite crazy) address-space parameters, for variety:

```sh
-P 8 -a 32 -p 1024 -v -s 1
-P 8k -a 32k -p 1m -v -s 2
-P 1m -a 256m -p 512m -v -s 3
```

##### Which of these parameter combinations are unrealistic? Why?

- The parameter combination `-P 8 -a 32 -p 1024 -v -s 1` is unrealistic because the page size is larger than the address space. This means that each page in the address space is larger than the entire address space itself, which is not possible.
