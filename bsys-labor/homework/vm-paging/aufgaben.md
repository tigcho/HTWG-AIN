# Antworten

## Aufgabe 1

```
./paging-linear-translate.py -P 1k -a 1m -p 512m -v -n 0 -c
./paging-linear-translate.py -P 1k -a 2m -p 512m -v -n 0 -c
./paging-linear-translate.py -P 1k -a 4m -p 512m -v -n 0 -c

./paging-linear-translate.py -P 1k -a 1m -p 512m -v -n 0 -c
./paging-linear-translate.py -P 2k -a 1m -p 512m -v -n 0 -c
./paging-linear-translate.py -P 4k -a 1m -p 512m -v -n 0 -c
```

Die Page Table wächst, wenn der Adressspace wächst.
Die Page Table schrumpft, wenn die Pagegröße steigt.
Bei großen Pages bleiben öfter Teile der Page leer und werden nicht verwendet => interne Fragmentierung?

## Aufgabe 2

./paging-linear-translate.py -P 1k -a 16k -p 32k -v -u 0
=> Alle Adressen sind invalid, da keine Page gefüllt ist.

./paging-linear-translate.py -P 1k -a 16k -p 32k -v -u 25
VA 0x00003986 (decimal:    14726) --> invalid
VA 0x00002bc6 (decimal:    11206) --> 20422 [VPN:1100, PFN:10011]
VA 0x00001e37 (decimal:     7735) --> invalid
VA 0x00000671 (decimal:     1649) --> invalid
VA 0x00001bc9 (decimal:     7113) --> invalid

./paging-linear-translate.py -P 1k -a 16k -p 32k -v -u 50
VA 0x00003385 (decimal:    13189) --> 16261 [VPN:1100, PFN:1111]
VA 0x0000231d (decimal:     8989) --> invalid [VPN:1000]
VA 0x000000e6 (decimal:      230) --> 24806 [VPN:0000, PFN:11000]
VA 0x00002e0f (decimal:    11791) --> invalid [VPN:1011]
VA 0x00001986 (decimal:     6534) --> 30086 [VPN:0110, PFN:11101]

./paging-linear-translate.py -P 1k -a 16k -p 32k -v -u 75
VA 0x00002e0f (decimal:    11791) --> 19983 [VPN:1011,PFN:10011]
VA 0x00001986 (decimal:     6534) --> 32134 [VPN:0110,PFN:11111]
VA 0x000034ca (decimal:    13514) --> 27850 [VPN:1101,PFN:11011]
VA 0x00002ac3 (decimal:    10947) --> 3779 [VPN:1010,PFN:0011]
VA 0x00000012 (decimal:       18) --> 24594 [VPN:0000,PFN:11000]

=> Je höher der Anteil der allokierten Pages ist, desto höher der Anteil der gültigen Adressen.

## Aufgabe 3

```
./paging-linear-translate.py -P 8 -a 32 -p 1024 -v -s 1 -c
```

Die Virtual Adress Größe ist viel zu klein, wie auch die anderen Werte.

```
./paging-linear-translate.py -P 8k -a 32k -p 1m -v -s 2 -c
```

Ebenfalls kleine Größen.

```
./paging-linear-translate.py -P 1m -a 256m -p 512m -v -s 3 -c
```

Der Virtual Adress Space nimmt die Hälfte des Physikalischen Speichers ein.

## Aufgabe 4

```
./paging-linear-translate.py -P 2k -a 1k -c
```

Adress Space muss größer sein als Page size (und ein Vielfaches davon), ansonsten macht paging keinen Sinn

```
./paging-linear-translate.py -a 1m -p 1m -c
```

Der Physikalische Speicher muss größer sein als der virtuelle Speicher, da ansonsten manche Teile nie in den Arbeitsspeicher geladen werden können und somit nie ausgeführt bzw. genutzt werden können