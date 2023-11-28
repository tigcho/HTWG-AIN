# Antworten

## Aufgabe 1
-s 1:
- VA0: Segmentation Violation
- VA1: 0x00003741; dezimal: 14145
- VA2: Segmentation Violation
- VA3: Segmentation Violation
- VA4: Segmentation Violation

-s 2:
- VA0: 0x00003ce2; dezimal: 15586
- VA1: 0x00003cff; dezimal: 15615
- VA2: Segmentation Violation
- VA3: Segmentation Violation
- VA4: Segmentation Violation

-s 3:
- VA0: Segmentation Violation
- VA1: Segmentation Violation
- VA2: Segmentation Violation
- VA3: 0x00002317; dezimal: 8929
- VA4: 0x000022e1; dezimal: 8929

=> überall, wo das Limit größer als die Virtuelle Adresse ist

## Aufgabe 2
Bei -s 1 -n 10 muss man ein Limit bei mindestens 868 setzten, damit die größte Virtuelle Adresse noch reinpasst.
Allgemein muss das Limit so groß wie der Address Space sein.

## Aufgabe 3
-s 1 -n 10 -l 100 -b 16284 -c
die maximale Base ist Physische Speicher Größe - Limit: 16 * 1024 − 100 = 16284

## Aufgabe 4
./relocation.py -s 1 -n 10 -l 4096 -b 16284 -p 32768 -a 4096 -c

## Aufgabe 5
f(x) = Limit/Virtual-Address-Space-Size
der Graph ist eine Gerade durch den Ursprung mit einer Steigung von 1/Virtual-Address-Space-Size