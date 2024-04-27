# Questions

1. First run with the flags `-n 10 -H 0 -p BEST -s 0` to generate a 
few random allocations and frees. Can you predict what alloc()/free() 
will return? Can you guess the state of the free list after each 
request? What do you notice about the free list over time?

- The free list becomes more fragmented over time. The free list is 
  initially empty, but as allocations and deallocations are made, 
  the free list becomes more fragmented. This is because the 
  allocator does not coalesce adjacent free blocks.

```sh
ptr[0] = Alloc(3) returned 1000 (searched 1 elements)
Free List [ Size 1 ]: [ addr:1003 sz:97 ]
# Allocates 3 bytes at address 1000, leaving 97 bytes free at address 1003


Free(ptr[0])
returned 0
Free List [ Size 2 ]: [ addr:1000 sz:3 ][ addr:1003 sz:97 ]
# Frees 3 bytes at address 1000, leaving 97 bytes free at address 1003


ptr[1] = Alloc(5) returned 1003 (searched 2 elements)
Free List [ Size 2 ]: [ addr:1000 sz:3 ][ addr:1008 sz:92 ]
# Allocates 5 bytes at address 1003, leaving 92 bytes free at address 1008


Free(ptr[1])
returned 0
Free List [ Size 3 ]: [ addr:1000 sz:3 ][ addr:1003 sz:5 ][ addr:1008 sz:92 ]
# Frees 5 bytes at address 1003, leaving 92 bytes free at address 1008


ptr[2] = Alloc(8) returned 1008 (searched 3 elements)
Free List [ Size 3 ]: [ addr:1000 sz:3 ][ addr:1003 sz:5 ][ addr:1016 sz:84 ]
# Allocates 8 bytes at address 1008, leaving 84 bytes free at address 1016


Free(ptr[2])
returned 0
Free List [ Size 4 ]: [ addr:1000 sz:3 ][ addr:1003 sz:5 ][ addr:1008 sz:8 ][ addr:1016 sz:84 ]
# Frees 8 bytes at address 1008, leaving 84 bytes free at address 1016


ptr[3] = Alloc(8) returned 1008 (searched 4 elements)
Free List [ Size 3 ]: [ addr:1000 sz:3 ][ addr:1003 sz:5 ][ addr:1016 sz:84 ]
# Allocates 8 bytes at address 1008, leaving 84 bytes free at address 1016


Free(ptr[3])
returned 0
Free List [ Size 4 ]: [ addr:1000 sz:3 ][ addr:1003 sz:5 ][ addr:1008 sz:8 ][ addr:1016 sz:84 ]
# Frees 8 bytes at address 1008, leaving 84 bytes free at address 1016


ptr[4] = Alloc(2) returned 1000 (searched 4 elements)
Free List [ Size 4 ]: [ addr:1002 sz:1 ][ addr:1003 sz:5 ][ addr:1008 sz:8 ][ addr:1016 sz:84 ]
# Allocates 2 bytes at address 1000, leaving 1 byte free at address 1002


ptr[5] = Alloc(7) returned 1008 (searched 4 elements)
Free List [ Size 4 ]: [ addr:1002 sz:1 ][ addr:1003 sz:5 ][ addr:1015 sz:1 ][ addr:1016 sz:84 ]
# Allocates 7 bytes at address 1008, leaving 1 byte free at address 1015
```

----------------------------------------

2. How are the results different when using a WORST fit policy to
search the free list (-p WORST)? What changes?

- The free list becomes more fragmented than the BEST fit policy. 
  The WORST fit policy selects the largest free block that can 
  accommodate the requested size. This results in smaller free 
  blocks being left behind, which can lead to fragmentation even faster.

```sh
❯ python3 malloc.py -n 10 -H 0 -p WORST -s 0 -c
seed 0
size 100
baseAddr 1000
headerSize 0
alignment -1
policy WORST
listOrder ADDRSORT
coalesce False
numOps 10
range 10
percentAlloc 50
allocList 
compute True

ptr[0] = Alloc(3) returned 1000 (searched 1 elements)
Free List [ Size 1 ]: [ addr:1003 sz:97 ]
# Allocates 3 bytes at address 1000, leaving 97 bytes free at address 1003


Free(ptr[0])
returned 0
Free List [ Size 2 ]: [ addr:1000 sz:3 ][ addr:1003 sz:97 ]
# Frees 3 bytes at address 1000, leaving 97 bytes free at address 1003


ptr[1] = Alloc(5) returned 1003 (searched 2 elements)
Free List [ Size 2 ]: [ addr:1000 sz:3 ][ addr:1008 sz:92 ]
# Allocates 5 bytes at address 1003, leaving 92 bytes free at address 1008


Free(ptr[1])
returned 0
Free List [ Size 3 ]: [ addr:1000 sz:3 ][ addr:1003 sz:5 ][ addr:1008 sz:92 ]
# Frees 5 bytes at address 1003, leaving 92 bytes free at address 1008


ptr[2] = Alloc(8) returned 1008 (searched 3 elements)
Free List [ Size 3 ]: [ addr:1000 sz:3 ][ addr:1003 sz:5 ][ addr:1016 sz:84 ]
# Allocates 8 bytes at address 1008, leaving 84 bytes free at address 1016


Free(ptr[2])
returned 0
Free List [ Size 4 ]: [ addr:1000 sz:3 ][ addr:1003 sz:5 ][ addr:1008 sz:8 ][ addr:1016 sz:84 ]
# Frees 8 bytes at address 1008, leaving 84 bytes free at address 1016


ptr[3] = Alloc(8) returned 1016 (searched 4 elements)
Free List [ Size 4 ]: [ addr:1000 sz:3 ][ addr:1003 sz:5 ][ addr:1008 sz:8 ][ addr:1024 sz:76 ]
# Allocates 8 bytes at address 1016, leaving 76 bytes free at address 1024


Free(ptr[3])
returned 0
Free List [ Size 5 ]: [ addr:1000 sz:3 ][ addr:1003 sz:5 ][ addr:1008 sz:8 ][ addr:1016 sz:8 ][ addr:1024 sz:76 ]
# Frees 8 bytes at address 1016, leaving 76 bytes free at address 1024


ptr[4] = Alloc(2) returned 1024 (searched 5 elements)
Free List [ Size 5 ]: [ addr:1000 sz:3 ][ addr:1003 sz:5 ][ addr:1008 sz:8 ][ addr:1016 sz:8 ][ addr:1026 sz:74 ]
# Allocates 2 bytes at address 1024, leaving 74 bytes free at address 1026


ptr[5] = Alloc(7) returned 1026 (searched 5 elements)
Free List [ Size 5 ]: [ addr:1000 sz:3 ][ addr:1003 sz:5 ][ addr:1008 sz:8 ][ addr:1016 sz:8 ][ addr:1033 sz:67 ]
# Allocates 7 bytes at address 1026, leaving 67 bytes free at address 1033
```

----------------------------------------

3. What about when using FIRST fit (-p FIRST)? What speeds up when you use first fit?

- The FIRST fit policy selects the first free block that can accommodate the requested size. This speeds up the allocation process because the allocator does not need to search the entire free list to find a free block that can accommodate the requested size.

```sh
❯ python3 malloc.py -n 10 -H 0 -p FIRST -s 0 -c
seed 0
size 100
baseAddr 1000
headerSize 0
alignment -1
policy FIRST
listOrder ADDRSORT
coalesce False
numOps 10
range 10
percentAlloc 50
allocList 
compute True

ptr[0] = Alloc(3) returned 1000 (searched 1 elements)
Free List [ Size 1 ]: [ addr:1003 sz:97 ]
# Allocates 3 bytes at address 1000, leaving 97 bytes free at address 1003


Free(ptr[0])
returned 0
Free List [ Size 2 ]: [ addr:1000 sz:3 ][ addr:1003 sz:97 ]
# Frees 3 bytes at address 1000, leaving 97 bytes free at address 1003


ptr[1] = Alloc(5) returned 1003 (searched 2 elements)
Free List [ Size 2 ]: [ addr:1000 sz:3 ][ addr:1008 sz:92 ]
# Allocates 5 bytes at address 1003, leaving 92 bytes free at address 1008


Free(ptr[1])
returned 0
Free List [ Size 3 ]: [ addr:1000 sz:3 ][ addr:1003 sz:5 ][ addr:1008 sz:92 ]
# Frees 5 bytes at address 1003, leaving 92 bytes free at address 1008


ptr[2] = Alloc(8) returned 1008 (searched 3 elements)
Free List [ Size 3 ]: [ addr:1000 sz:3 ][ addr:1003 sz:5 ][ addr:1016 sz:84 ]
# Allocates 8 bytes at address 1008, leaving 84 bytes free at address 1016


Free(ptr[2])
returned 0
Free List [ Size 4 ]: [ addr:1000 sz:3 ][ addr:1003 sz:5 ][ addr:1008 sz:8 ][ addr:1016 sz:84 ]
# Frees 8 bytes at address 1008, leaving 84 bytes free at address 1016


ptr[3] = Alloc(8) returned 1008 (searched 3 elements)
Free List [ Size 3 ]: [ addr:1000 sz:3 ][ addr:1003 sz:5 ][ addr:1016 sz:84 ]
# Allocates 8 bytes at address 1008, leaving 84 bytes free at address 1016


Free(ptr[3])
returned 0
Free List [ Size 4 ]: [ addr:1000 sz:3 ][ addr:1003 sz:5 ][ addr:1008 sz:8 ][ addr:1016 sz:84 ]
# Frees 8 bytes at address 1008, leaving 84 bytes free at address 1016


ptr[4] = Alloc(2) returned 1000 (searched 1 elements)
Free List [ Size 4 ]: [ addr:1002 sz:1 ][ addr:1003 sz:5 ][ addr:1008 sz:8 ][ addr:1016 sz:84 ]
# Allocates 2 bytes at address 1000, leaving 1 byte free at address 1002


ptr[5] = Alloc(7) returned 1008 (searched 3 elements)
Free List [ Size 4 ]: [ addr:1002 sz:1 ][ addr:1003 sz:5 ][ addr:1015 sz:1 ][ addr:1016 sz:84 ]
# Allocates 7 bytes at address 1008, leaving 1 byte free at address 1015
```

----------------------------------------

4. For the above questions, how the list is kept ordered can affect the
time it takes to find a free location for some of the policies. Use
the different free list orderings (`-l ADDRSORT, -l SIZESORT+,
-l SIZESORT-`) to see how the policies and the list orderings interact.

1.    BEST Fit Policy:
       -  ADDRSORT: BEST fit searches for the smallest block that meets the request. ADDRSORT is neutral since BEST fit may still require scanning most of the list to find the "best" fit.
       -  SIZESORT+: This is optimal for BEST fit, ensuring the list is already sorted by smallest size, minimizing search time.
       -  SIZESORT-: This might slow down BEST fit since it starts with the largest blocks, requiring more searches to find the smallest suitable block.

2.    WORST Fit Policy:
       -  ADDRSORT: WORST fit looks for the largest block to allocate from. ADDRSORT may increase search time as it lacks size order.
       -  SIZESORT+: WORST fit requires a longer search since the smallest blocks are at the start of the list. This leads to higher search costs.
       -  SIZESORT-: This is optimal for WORST fit, allowing for quick retrieval of the largest block.

3.    FIRST Fit Policy:
       -  ADDRSORT: FIRST fit scans the list until it finds a suitable block. ADDRSORT works well for this policy, but it might lead to fragmentation at the beginning of the list.
       -  SIZESORT+: This may require longer scans, as smaller blocks come first, potentially slowing down the allocation.
       -  SIZESORT-: FIRST fit may quickly find suitable blocks with larger blocks at the start, improving performance.

-    ADDRSORT: Works best with FIRST fit, as it quickly finds the first suitable block. With BEST fit, it requires more scanning to find the smallest block. WORST fit also has longer search times.
-    SIZESORT+: Ideal for BEST fit, as it minimizes search time for the smallest block. WORST fit and FIRST fit might require more scans.
-    SIZESORT-: Suitable for WORST fit and FIRST fit, as larger blocks are at the beginning. BEST fit requires more scanning to find smaller blocks.
