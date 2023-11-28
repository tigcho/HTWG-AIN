# Antworten

## Aufgabe 1
```
./x86.py -p loop.s -t 1 -i 100 -R dx -c 

   dx          Thread 0         
    0   
   -1   1000 sub  $1,%dx
   -1   1001 test $0,%dx
   -1   1002 jgte .top
   -1   1003 halt
```

## Aufgabe 2

```
./x86.py -p loop.s -t 2 -i 100 -a dx=3,dx=3 -R dx -c

   dx          Thread 0                Thread 1         
    3   
    2   1000 sub  $1,%dx
    2   1001 test $0,%dx
    2   1002 jgte .top
    1   1000 sub  $1,%dx
    1   1001 test $0,%dx
    1   1002 jgte .top
    0   1000 sub  $1,%dx
    0   1001 test $0,%dx
    0   1002 jgte .top
   -1   1000 sub  $1,%dx
   -1   1001 test $0,%dx
   -1   1002 jgte .top
   -1   1003 halt
    3   ----- Halt;Switch -----  ----- Halt;Switch -----  
    2                            1000 sub  $1,%dx
    2                            1001 test $0,%dx
    2                            1002 jgte .top
    1                            1000 sub  $1,%dx
    1                            1001 test $0,%dx
    1                            1002 jgte .top
    0                            1000 sub  $1,%dx
    0                            1001 test $0,%dx
    0                            1002 jgte .top
   -1                            1000 sub  $1,%dx
   -1                            1001 test $0,%dx
   -1                            1002 jgte .top
   -1                            1003 halt

```

Es gibt in diesem Code keine Race-Condition, weil nur die Werte in Registern verwendet werden und jeder Thread einen eigenen Satz Register zur Verfügung hat.

## Aufgabe 3
```
./x86.py -p loop.s -s 0 -t 2 -i 3 -r -a dx=3,dx=3 -R dx -c

   dx          Thread 0                Thread 1         
    3   
    2   1000 sub  $1,%dx
    2   1001 test $0,%dx
    2   1002 jgte .top
    3   ------ Interrupt ------  ------ Interrupt ------  
    2                            1000 sub  $1,%dx
    2                            1001 test $0,%dx
    2                            1002 jgte .top
    2   ------ Interrupt ------  ------ Interrupt ------  
    1   1000 sub  $1,%dx
    1   1001 test $0,%dx
    2   ------ Interrupt ------  ------ Interrupt ------  
    1                            1000 sub  $1,%dx
    1   ------ Interrupt ------  ------ Interrupt ------  
    1   1002 jgte .top
    0   1000 sub  $1,%dx
    1   ------ Interrupt ------  ------ Interrupt ------  
    1                            1001 test $0,%dx
    1                            1002 jgte .top
    0   ------ Interrupt ------  ------ Interrupt ------  
    0   1001 test $0,%dx
    0   1002 jgte .top
   -1   1000 sub  $1,%dx
    1   ------ Interrupt ------  ------ Interrupt ------  
    0                            1000 sub  $1,%dx
   -1   ------ Interrupt ------  ------ Interrupt ------  
   -1   1001 test $0,%dx
   -1   1002 jgte .top
    0   ------ Interrupt ------  ------ Interrupt ------  
    0                            1001 test $0,%dx
    0                            1002 jgte .top
   -1   ------ Interrupt ------  ------ Interrupt ------  
   -1   1003 halt
    0   ----- Halt;Switch -----  ----- Halt;Switch -----  
   -1                            1000 sub  $1,%dx
   -1                            1001 test $0,%dx
   -1   ------ Interrupt ------  ------ Interrupt ------  
   -1                            1002 jgte .top
   -1                            1003 halt
```

Die Anzahl der Interrupts beeinflusst die Reihenfolge der Instruktionen auf der CPU und die Zeit, die das Programm braucht, jedoch nicht die Ergebnisse in den Registern, denn diese werden nicht zwischen Threads geteilt.

## Aufgabe 4

```
./x86.py -p looping-race-nolock.s -t 1 -M 2000 -c

 2000          Thread 0         
    0   
    0   1000 mov 2000, %ax
    0   1001 add $1, %ax
    1   1002 mov %ax, 2000
    1   1003 sub  $1, %bx
    1   1004 test $0, %bx
    1   1005 jgt .top
    1   1006 halt

```

## Aufgabe 5

```
./x86.py -p looping-race-nolock.s -t 2 -a bx=3 -M 2000 -c

 2000          Thread 0                Thread 1         
    0   
    0   1000 mov 2000, %ax
    0   1001 add $1, %ax
    1   1002 mov %ax, 2000
    1   1003 sub  $1, %bx
    1   1004 test $0, %bx
    1   1005 jgt .top
    1   1000 mov 2000, %ax
    1   1001 add $1, %ax
    2   1002 mov %ax, 2000
    2   1003 sub  $1, %bx
    2   1004 test $0, %bx
    2   1005 jgt .top
    2   1000 mov 2000, %ax
    2   1001 add $1, %ax
    3   1002 mov %ax, 2000
    3   1003 sub  $1, %bx
    3   1004 test $0, %bx
    3   1005 jgt .top
    3   1006 halt
    3   ----- Halt;Switch -----  ----- Halt;Switch -----  
    3                            1000 mov 2000, %ax
    3                            1001 add $1, %ax
    4                            1002 mov %ax, 2000
    4                            1003 sub  $1, %bx
    4                            1004 test $0, %bx
    4                            1005 jgt .top
    4                            1000 mov 2000, %ax
    4                            1001 add $1, %ax
    5                            1002 mov %ax, 2000
    5                            1003 sub  $1, %bx
    5                            1004 test $0, %bx
    5                            1005 jgt .top
    5                            1000 mov 2000, %ax
    5                            1001 add $1, %ax
    6                            1002 mov %ax, 2000
    6                            1003 sub  $1, %bx
    6                            1004 test $0, %bx
    6                            1005 jgt .top
    6                            1006 halt
```

In jedem Thread wird die Schleife dreimal durchlaufen, weil bx bei jedem Durchlaufen runtergezählt wird und dann gesprungen wird, wenn bx kleiner als 0 ist. bx wird am Anfang auf 3 gesetzt.
Der Wert an der Stelle 2000 ist am ende 6, weil zuerst der Thread 0 die Shared Variable dreimal hochzählt und dann der Thread 1.

## Aufgabe 6

```
./x86.py -p looping-race-nolock.s -t 2 -M 2000 -i 4 -r -s 0 -c

 2000          Thread 0                Thread 1         
    0   
    0   1000 mov 2000, %ax
    0   1001 add $1, %ax
    1   1002 mov %ax, 2000
    1   1003 sub  $1, %bx
    1   ------ Interrupt ------  ------ Interrupt ------  
    1                            1000 mov 2000, %ax
    1                            1001 add $1, %ax
    2                            1002 mov %ax, 2000
    2                            1003 sub  $1, %bx
    2   ------ Interrupt ------  ------ Interrupt ------  
    2   1004 test $0, %bx
    2   1005 jgt .top
    2   ------ Interrupt ------  ------ Interrupt ------  
    2                            1004 test $0, %bx
    2                            1005 jgt .top
    2   ------ Interrupt ------  ------ Interrupt ------  
    2   1006 halt
    2   ----- Halt;Switch -----  ----- Halt;Switch -----  
    2                            1006 halt

./x86.py -p looping-race-nolock.s -t 2 -M 2000 -i 4 -r -s 1 -c

 2000          Thread 0                Thread 1         
    0   
    0   1000 mov 2000, %ax
    0   ------ Interrupt ------  ------ Interrupt ------  
    0                            1000 mov 2000, %ax
    0                            1001 add $1, %ax
    1                            1002 mov %ax, 2000
    1                            1003 sub  $1, %bx
    1   ------ Interrupt ------  ------ Interrupt ------  
    1   1001 add $1, %ax
    1   1002 mov %ax, 2000
    1   1003 sub  $1, %bx
    1   1004 test $0, %bx
    1   ------ Interrupt ------  ------ Interrupt ------  
    1                            1004 test $0, %bx
    1                            1005 jgt .top
    1   ------ Interrupt ------  ------ Interrupt ------  
    1   1005 jgt .top
    1   1006 halt
    1   ----- Halt;Switch -----  ----- Halt;Switch -----  
    1   ------ Interrupt ------  ------ Interrupt ------  
    1                            1006 halt

```

Ohne Interrupts würde am Ende 2 herauskommen. Mit -s 1 wird allerdings der erste Interrupt in Thread 0 bei einer kritischen Stelle gemacht, was bewirkt, das der Wert aus 2000 in ax für Thread 0 gespeichert wird. Dann wird aber in Thread 1 einmal der Startwert von 2000 hochgezählt und wieder abgespeichert. Danach ist der Thread 0 wieder an der Reihe und zählt nur den Wert in ax hoch, ohne zu wissen, dass der Wert in 2000 bereits hochgezählt wurde. Wenn jetzt Thread 0 seinen errechneten Wert in 2000 abspeichern will, wird der errechnete Wert von Thread 1 überschrieben.

Die kritische Sektion sind also die mov-add-mov-Befehle.

## Aufgabe 7

```
./x86.py -p looping-race-nolock.s -a bx=1 -t 2 -M 2000 -i 3 -c

 2000          Thread 0                Thread 1         
    0   
    0   1000 mov 2000, %ax
    0   1001 add $1, %ax
    1   1002 mov %ax, 2000
    1   ------ Interrupt ------  ------ Interrupt ------  
    1                            1000 mov 2000, %ax
    1                            1001 add $1, %ax
    2                            1002 mov %ax, 2000
    2   ------ Interrupt ------  ------ Interrupt ------  
    2   1003 sub  $1, %bx
    2   1004 test $0, %bx
    2   1005 jgt .top
    2   ------ Interrupt ------  ------ Interrupt ------  
    2                            1003 sub  $1, %bx
    2                            1004 test $0, %bx
    2                            1005 jgt .top
    2   ------ Interrupt ------  ------ Interrupt ------  
    2   1006 halt
    2   ----- Halt;Switch -----  ----- Halt;Switch -----  
    2                            1006 halt
```

Der richtige Wert 2 kommt ab einer Intervallfrequenz von mindestens 3 heraus, weil die kritische Sektion die ersten 3 Befehle betrifft. Bei einer Intervallfrequenz von mindestens 3 werden in jedem Thread auf jeden Fall die ersten 3 Befehle hintereinander ausgeführt.

## Aufgabe 8

```
./x86.py -p looping-race-nolock.s -a bx=1 -t 2 -M 2000 -i 3 -a bx=100 -c
./x86.py -p looping-race-nolock.s -a bx=1 -t 2 -M 2000 -i 5 -a bx=100 -c
```

An dem Ergebnis ändert sich nichts, wenn die Intervallgröße durch 3 Teilbar ist, weil ein Schleifendurchlauf erst eine kritische Sektion von 3 Instruktionen hat und dann noch drei weitere. Wenn die Intervallgröße nicht durch 3 Teilbar ist, wird die kritische Sektion nach dem ersten Durchlauf durch einen Interrupt geteilt und es werden wieder falsche Ergebnisse berechnet.

## Aufgabe 9

```
./x86.py -p wait-for-me.s -a ax=1,ax=0 -R ax -M 2000 -c

 2000      ax          Thread 0                Thread 1         
    0       1   
    0       1   1000 test $1, %ax
    0       1   1001 je .signaller
    1       1   1006 mov  $1, 2000
    1       1   1007 halt
    1       0   ----- Halt;Switch -----  ----- Halt;Switch -----  
    1       0                            1000 test $1, %ax
    1       0                            1001 je .signaller
    1       0                            1002 mov  2000, %cx
    1       0                            1003 test $1, %cx
    1       0                            1004 jne .waiter
    1       0                            1005 halt
```

Ein Thread wird beendet, wenn ax auf 1 steht. Dann wird 2000 auf 1 gesetzt. Bei Thread 0 ist ax = 1, deswegen wird der Thread gleich beendet und Thread 1 testet einmal, ob Thread 0 fertig ist, indem der Wert bei 2000 geprüft wird. Da 2000 auf 1 gesetzt ist, wird auch Thread 2 beendet.

## Aufgabe 10

```
./x86.py -p wait-for-me.s -a ax=0,ax=1 -R ax -M 2000 -c

 2000      ax          Thread 0                Thread 1         
    0       0   
    0       0   1000 test $1, %ax
    0       0   1001 je .signaller
    0       0   1002 mov  2000, %cx
    0       0   1003 test $1, %cx
    0       0   1004 jne .waiter
    0       0   1002 mov  2000, %cx
    0       0   1003 test $1, %cx
    0       0   1004 jne .waiter
    0       0   1002 mov  2000, %cx
    0       0   1003 test $1, %cx
    0       0   1004 jne .waiter
...
    0       0   1002 mov  2000, %cx
    0       0   1003 test $1, %cx
    0       0   1004 jne .waiter
    0       0   1002 mov  2000, %cx
    0       0   1003 test $1, %cx
    0       0   1004 jne .waiter
    0       1   ------ Interrupt ------  ------ Interrupt ------  
    0       1                            1000 test $1, %ax
    0       1                            1001 je .signaller
    1       1                            1006 mov  $1, 2000
    1       1                            1007 halt
    1       0   ----- Halt;Switch -----  ----- Halt;Switch -----  
    1       0   1002 mov  2000, %cx
    1       0   1003 test $1, %cx
    1       0   1004 jne .waiter
    1       0   1005 halt
```

Da in Thread 0 ax auf 0 gesetzt ist, und 2000 von einem anderen Thread noch nicht auf 1 gesetzt werden konnte, bleibt Thread 0 solange in einer Schleife, bis ein Interrupt auftritt und der andere Thread mit ax = 1 den Wert bei 2000 erhöhen kann. Erst danach kann Thread 0 ebenfalls beenden.

Dieses Programm nutzt die CPU nicht sehr effizient, da die CPU nichts anderes ausführen kann, wenn ein Thread so lange im Wartezustand ist

Dieser Effekt wird noch verstärkt, wenn die Intervallgröße mit -i 1000 vergrößert wird, weil so Thread 0 noch länger im Wartezustand verweilt, ohne dass Thread 1 einschreiten kann.