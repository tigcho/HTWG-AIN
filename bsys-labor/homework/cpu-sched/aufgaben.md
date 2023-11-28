# Antworten

## Aufgabe 1
- SJF: 
    - Job 0: Response 0.00 / Turnaround: 200.00
    - Job 1: Response 200.00 / Turnaround: 400.00
    - Job 2: Response 400.00 / Turnaround: 600.00

- FIFO:
    - Job 0: Response 0.00 / Turnaround: 200.00
    - Job 1: Response 200.00 / Turnaround: 400.00
    - Job 2: Response 400.00 / Turnaround: 600.00

## Aufgabe 2
- SJF: => kein Unterschied zu FIFO, weil sortierte Joblänge
    - Job 0: Response 0.00 / Turnaround: 100.00
    - Job 1: Response 100.00 / Turnaround: 300.00
    - Job 2: Response 300.00 / Turnaround: 600.00

- FIFO: 
    - Job 0: Response 0.00 / Turnaround: 100.00
    - Job 1: Response 100.00 / Turnaround: 300.00
    - Job 2: Response 300.00 / Turnaround: 600.00

## Aufgabe 3
- 200,200,200:
    - Job 0: Response 0.00 / Turnaround: 598.00
    - Job 1: Response 1.00 / Turnaround: 599.00
    - Job 2: Response 2.00 / Turnaround: 600.00

- 100,200,300:
    - Job 0: Response 0.00 / Turnaround: 298.00
    - Job 1: Response 1.00 / Turnaround: 499.00
    - Job 2: Response 2.00 / Turnaround: 600.00

## Aufgabe 4
Wenn alle Jobs dieselbe Länge haben oder wenn die Jobs bei SJF in einer Ordnung vom kürzesten zum längsten aufgerufen werden.

## Aufgabe 5
Wenn alle Jobs dieselbe Länge haben und die quantum length auch diese Länge hat. Dann kann ein Job innerhalb eines Schrittes in RR am Stück erledigt werden.

## Aufgabe 6
0.00 -> 0.50 -> 1.33 -> 2.50 -> 4.00 -> 5.83 -> 8.00 -> 10.50 -> 13.33 -> 16.50

## Aufgabe 7
Die response time steigt linear, wenn die quantum length auch linear steigt, denn so dauert es länger, bis ein Job anfangen darf.
n: Anzahl der Jobs; q: quantum length
(n-1)*q/n