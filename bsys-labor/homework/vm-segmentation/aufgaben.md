# Antworten

## Aufgabe 1

./segmentation.py -a 128 -p 512 -b 0 -l 20 -B 512 -L 20 -s 0

- VA0: 0x0000006c (dezimal: 108) in Segment 1 => 0x000001ec (decimal:  492)
- VA1: 0x00000061 (dezimal: 97) => SEGFAULT
- VA1: 0x00000035 (dezimal: 53) => SEGFAULT
- VA1: 0x00000021 (dezimal: 33) => SEGFAULT
- VA1: 0x00000041 (dezimal: 65) => SEGFAULT

./segmentation.py -a 128 -p 512 -b 0 -l 20 -B 512 -L 20 -s 1

- VA0: 0x00000011 (dezimal: 17) => in Segment 0: 0x00000011 (dezimal: 17)
- VA1: 0x0000006c (dezimal: 108) => in Segment 1: 0x000001ec (dezimal: 492)
- VA2: 0x00000061 (dezimal: 97) => SEGFAULT
- VA3: 0x00000020 (dezimal: 32) => SEGFAULT
- VA4: 0x0000003f (dezimal: 63) => SEGFAULT

./segmentation.py -a 128 -p 512 -b 0 -l 20 -B 512 -L 20 -s 2

- VA0: 0x0000007a (dezimal: 122) => in Segment 1: 0x000001fa (dezimal:506)
- VA1: 0x00000079 (dezimal: 121) => in Segment 1: 0x000001f9 (dezimal:505)
- VA2: 0x00000007 (dezimal: 7) => in Segment 0: 0x00000007 (dezimal: 7)
- VA3: 0x0000000a (dezimal: 10) => in Segment 0: 0x0000000a (dezimal: 10)
- VA4: 0x0000006a (dezimal: 106) => SEGFAULT

## Aufgabe 2
Höchstmögliche VA in Segment 0: 19
Niedrigste VA in Segment 1: 108
Niedrigste illegale VA im Adressraum: 20
Höchste illegale VA im Adressraum: 107

./segmentation.py -a 128 -p 512 -b 0 -l 20 -B 512 -L 20 -s 2 -A 19,20,107,108 -c

## Aufgabe 3
./segmentation.py -a 16 -p 128 -A 0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15 --b0 0 --l0 2 --b1 130 --l1 2 -c

## Aufgabe 4
(256 * 0,9) / 2= 230.4 / 2 = 115.2

./segmentation.py -a 256 -p 1024 --b0 0 --l0 115 --b1 1024 --l1 115 -c -n 1000 | grep VIOLATION | wc -l

## Aufgabe 5
beide Limits auf 0 stellen
./segmentation.py -a 128 -p 512 -A 0,127 --l0 0 --l1 0 -c
