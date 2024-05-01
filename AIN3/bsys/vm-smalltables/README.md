# Making it make sense :3

1. The virtual address space for the process is 1024 pages, and each page has the size of 32 bytes. To address 1024 pages, we need 10 bits because 2^10 = 1024. To address a single page, we need 5 bits because 2^5 = 32. Therefore, the virtual address is 15 bits long (10 bits for the Virtual Page Numer + 5 Bits for the offset).

2. The physical memory has 128 frames, and each frame has the size of 32 bytes. To address 128 frames, we need 7 bits because 2^7 = 128. To address a single frame, we need 5 bits because 2^5 = 32. Therefore, the physical address is 12 bits long (7 bits for the Frame Number + 5 Bits for the offset).

3. The system uses a multi-level page table to map the virtual address to the physical address. The first five bits of the virtual address are used to index the page directory. If the Page Directory Entry (PDE) is valid, it points to a page of the page table. The next five bits are used to index the page table. Each page table holds 32 page table entries (PTEs). If the PTE is valid it points to the physical frame number (PFN) of the virtual page.

4. The size of the PTE and PDE are 8 bits (1 Byte) each which allows for 256 entries in the page table and page directory because 2^8 = 256. The valid bit is 1 bit, and if it is set, it means that the entry is in use. The PFN/PDE is 7 bits long and locate where the page is located in physical memory/which page should be used for the next level of lookup. The Page Directory Base Register (PDBR) points to the starting page in the page directory in physical memory.

-------------------

# Questions

##### 1. With a linear page table, you need a single register to locate the page table, assuming that hardware does the lookup upon a TLB miss. How many registers do you need to locate a two-level page table? A three-level table?

- For a linear page table, we only need one register to locate the page table because the page table is stored in contiguous memory. Given a virtual address, the hardware extracts the VPN and uses it to index into the page table to find the corresponding PFN. Since the page table is a simple array, you only need a single base register to locate the beginning. If there is a TLB miss, the hardware will use the base register to find the page table and perform the lookup. At a TLB hit, the hardware will use the TLB to find the PFN directly, without needing the base register.

- For each level more I would have assumed we need one register more to index between them, but I am not sure about this. Maybe having one register in general is enough because the PTBR holds the base address of a page directory, and if there are more levels then that value might just be different for each level, without needing a separate register for each level.

from: GeeksForGeeks[![GeeksForGeeks](https://media.geeksforgeeks.org/wp-content/uploads/20190608174704/multilevel.png)](https://www.geeksforgeeks.org/multilevel-paging-in-operating-system/)

-------------------

##### 2. Use the simulator to perform translations given random seeds 0, 1, and 2, and check your answers using the -c flag. How many memory references are needed to perform each lookup?

`❯ python3 paging-multilevel-translate.py -s 0`
For Virtual Address `0x611c`:
- 0x611c in binary is 11000 01000 11100
- First 5 bits are 11000 = 24 (0x18, PDE index)
- Next 5 bits are 01000 = 8 (0x8, PTE index)
- Last 5 bits are 11100 = 28 (0x1c, offset)
- Now look at byte 24 (index) in page 108 
- The content is 83 fe e0 da 7f d4 7f eb be 9e d5 ad e4 ac 90 d6 92 d8 c1 f8 9f e1 ed e9 a1 e8 c7 c2 a9 d1 db ff
- The PDE content is 0xa1 (in binary 10100001, so it is a valid entry)
- The rest of the address 0100001 (33) is the PFN, now we look at byte 8 (index) in page 33
- The content is 7f 7f 7f 7f 7f 7f 7f 7f b5 7f 9d 7f 7f 7f 7f 7f 7f 7f 7f 7f 7f 7f 7f 7f 7f 7f f6 b1 7f 7f 7f 7f
- The PTE content is 0xb5 (in binary 10110101, so it is a valid entry)
- The rest of the address is 0110101 (53) is the PFN
- The physical address is constructed from the PFN and the offset: 53 * 32 + 28 = 1724 (0x6bc)
- The content of the physical address is 8 because offset 28 at page 53 gives me 0x08

- You need 3 memory references to perform the lookup: 1 for the PDE, 1 for the PTE, and 1 for the data.




