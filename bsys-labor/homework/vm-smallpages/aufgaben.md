# Antworten

## Aufgabe 1

Da das Page Directory ein Baum ist, genügt es im besten Fall, die Wurzel, also PDTR, in einem Register zu Speichern. Dies gilt für two-level und three-level page tables, hier kann es aber auch passieren, dass die page tables nicht alle in das Page Directory passen. In diesem Fall müssen dann weitere Page Directories angelegt werden. Diese werden aber wieder in einem Page Directory gespeichert, weshalb ein Register immer noch ausreicht.

## Aufgabe 2

```sh
./paging-multilevel-translate.py -s 0 -n 1 -c

Virtual Address 611c:
  --> pde index:0x18 [decimal 24] pde contents:0xa1 (valid 1, pfn 0x21 [decimal 33])
    --> pte index:0x8 [decimal 8] pte contents:0xb5 (valid 1, pfn 0x35 [decimal 53])
      --> Translates to Physical Address 0x6bc --> Value: 08
```

## Aufgabe 3

Zugriff auf top-level page directory => Geschwindigkeitsvorteil durch häufige Zugriffe zeitlich hintereinander
Zugriff auf page table entries => Geschwindigkeitsvorteil durch Ausnutzung der räumlichen Nähe