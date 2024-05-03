# Making it make sense :3

1. Page Replacement Policies
- FIFO (First In First Out): The oldest page is replaced first.
- LRU (Least Recently Used): The least recently used page is replaced first.
- MRU (Most Recently Used): The most recently used page is replaced first.
- OPT (Optimal): The page that will not be used for the longest period of time is replaced first.
- UNOPT (Unoptimal): The page that will be used for the longest period of time is replaced first.
- CLOCK: does the clock algorithm and takes another argument which states how many bits should be kept per page. The more clock bits there are, the better the algorithm should be at determining which pages to keep in memory.

2. Analyzing the output
When running the simulator with the following command:
`./paging-policy.py --addresses=0,1,2,0,1,3,0,3,1,2,1 --policy=LRU --cachesize=3 -c`

We get the following output:
```sh
ARG addresses 0,1,2,0,1,3,0,3,1,2,1
ARG numaddrs 10
ARG policy LRU
ARG cachesize 3
ARG maxpage 10
ARG seed 0

Solving...

Access: 0 MISS LRU->      [br 0]<-MRU Replace:- [br Hits:0 Misses:1]
Access: 1 MISS LRU->   [br 0, 1]<-MRU Replace:- [br Hits:0 Misses:2]
Access: 2 MISS LRU->[br 0, 1, 2]<-MRU Replace:- [br Hits:0 Misses:3]
Access: 0 HIT  LRU->[br 1, 2, 0]<-MRU Replace:- [br Hits:1 Misses:3]
Access: 1 HIT  LRU->[br 2, 0, 1]<-MRU Replace:- [br Hits:2 Misses:3]
Access: 3 MISS LRU->[br 0, 1, 3]<-MRU Replace:2 [br Hits:2 Misses:4]
Access: 0 HIT  LRU->[br 1, 3, 0]<-MRU Replace:2 [br Hits:3 Misses:4]
Access: 3 HIT  LRU->[br 1, 0, 3]<-MRU Replace:2 [br Hits:4 Misses:4]
Access: 1 HIT  LRU->[br 0, 3, 1]<-MRU Replace:2 [br Hits:5 Misses:4]
Access: 2 MISS LRU->[br 3, 1, 2]<-MRU Replace:0 [br Hits:5 Misses:5]
Access: 1 HIT  LRU->[br 3, 2, 1]<-MRU Replace:0 [br Hits:6 Misses:5]
```

It indicates the result of each page access, whether it was a hit or a miss, the current state of the cache, and which page was replaced. The notation `[br ...]` shows the cache content from the LRU to MRU. The `Replace` field shows which page was replaced.

3. Question about different cache sizes
```sh
❯ python3 paging-policy.py -C 3 -a 1,2,3,4,1,2,5,1,2,3,4,5 -c
Access: 1  MISS FirstIn ->          [1] <- Lastin  Replaced:- [Hits:0 Misses:1]
Access: 2  MISS FirstIn ->       [1, 2] <- Lastin  Replaced:- [Hits:0 Misses:2]
Access: 3  MISS FirstIn ->    [1, 2, 3] <- Lastin  Replaced:- [Hits:0 Misses:3]
Access: 4  MISS FirstIn ->    [2, 3, 4] <- Lastin  Replaced:1 [Hits:0 Misses:4]
Access: 1  MISS FirstIn ->    [3, 4, 1] <- Lastin  Replaced:2 [Hits:0 Misses:5]
Access: 2  MISS FirstIn ->    [4, 1, 2] <- Lastin  Replaced:3 [Hits:0 Misses:6]
Access: 5  MISS FirstIn ->    [1, 2, 5] <- Lastin  Replaced:4 [Hits:0 Misses:7]
Access: 1  HIT  FirstIn ->    [1, 2, 5] <- Lastin  Replaced:- [Hits:1 Misses:7]
Access: 2  HIT  FirstIn ->    [1, 2, 5] <- Lastin  Replaced:- [Hits:2 Misses:7]
Access: 3  MISS FirstIn ->    [2, 5, 3] <- Lastin  Replaced:1 [Hits:2 Misses:8]
Access: 4  MISS FirstIn ->    [5, 3, 4] <- Lastin  Replaced:2 [Hits:2 Misses:9]
Access: 5  HIT  FirstIn ->    [5, 3, 4] <- Lastin  Replaced:- [Hits:3 Misses:9]

FINALSTATS hits 3   misses 9   hitrate 25.00
```
- In this example we have a cache size of 3, which means that the cache can hold up to 3 pages at a time. The cache is initially empty, so the first 3 pages are added to the cache without any replacements. With a FIFO policy, the oldest page is replaced when a new page is accessed. The first four accesses are misses since the cache starts empty. After four accesses, the cache is full, and the first page is replaced. The fifth access is therefore a miss, because the first page is no longer in the cache. Same with the 6th and 7th access. The 8th, 9th and 10th are hits because the cache holds the pages 1, 2 and 5 at that point.

```sh
❯ python3 paging-policy.py -C 4 -a 1,2,3,4,1,2,5,1,2,3,4,5 -c
Access: 1  MISS FirstIn ->          [1] <- Lastin  Replaced:- [Hits:0 Misses:1]
Access: 2  MISS FirstIn ->       [1, 2] <- Lastin  Replaced:- [Hits:0 Misses:2]
Access: 3  MISS FirstIn ->    [1, 2, 3] <- Lastin  Replaced:- [Hits:0 Misses:3]
Access: 4  MISS FirstIn -> [1, 2, 3, 4] <- Lastin  Replaced:- [Hits:0 Misses:4]
Access: 1  HIT  FirstIn -> [1, 2, 3, 4] <- Lastin  Replaced:- [Hits:1 Misses:4]
Access: 2  HIT  FirstIn -> [1, 2, 3, 4] <- Lastin  Replaced:- [Hits:2 Misses:4]
Access: 5  MISS FirstIn -> [2, 3, 4, 5] <- Lastin  Replaced:1 [Hits:2 Misses:5]
Access: 1  MISS FirstIn -> [3, 4, 5, 1] <- Lastin  Replaced:2 [Hits:2 Misses:6]
Access: 2  MISS FirstIn -> [4, 5, 1, 2] <- Lastin  Replaced:3 [Hits:2 Misses:7]
Access: 3  MISS FirstIn -> [5, 1, 2, 3] <- Lastin  Replaced:4 [Hits:2 Misses:8]
Access: 4  MISS FirstIn -> [1, 2, 3, 4] <- Lastin  Replaced:5 [Hits:2 Misses:9]
Access: 5  MISS FirstIn -> [2, 3, 4, 5] <- Lastin  Replaced:1 [Hits:2 Misses:10]

FINALSTATS hits 2   misses 10   hitrate 16.67
```
- In this example we have a cache size of 4, which means that the cache can hold up to 4 pages at a time. It works the same way as above.

**Question:** What is the difference between the two examples above? Why does the hit rate differ so much between the two examples?

- The difference between the two examples is the cache size. The first example has a cache size of 3, while the second example has a cache size of 4. The hit rate differs because the cache size affects how many pages can be stored in the cache at a time. A larger cache size doesn't necessarily mean a higher hit rate. 

------------------------

# Questions

##### 1. Generate random addresses with the following arguments: `-s 0 -n 10, -s 1 -n 10, and -s 2 -n 10`. Change the policy from FIFO, to LRU, to OPT. Compute whether each access in said address traces are hits or misses.





