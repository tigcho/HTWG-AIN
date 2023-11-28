#!/bin/bash
POLICIES=("FIFO" "LRU" "OPT" "UNOPT" "RAND" "CLOCK")

#clear contents of hitrates.txt
> hitrates.txt

for policy in "${POLICIES[@]}"
do
    for i in 1 2 3 4
    do
        ./../../../ostep-homework/vm-beyondphys-policy/paging-policy.py -c -f ./vpn.txt -p "$policy" -C "$i" | grep "hitrate" | grep -o "[0-9][0-9]\.[0-9][0-9]">> hitrates.txt
        echo "Done with policy $policy with cache size $i"
    done
done