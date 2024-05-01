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

from: [![GeeksForGeeks](https://media.geeksforgeeks.org/wp-content/uploads/20190608174704/multilevel.png)](https://www.geeksforgeeks.org/multilevel-paging-in-operating-system/)

