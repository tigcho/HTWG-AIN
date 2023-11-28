# Antworten

## Aufgabe 1
Die CPU wird die ganze Zeit benutzt, weil bei beiden Prozessen 100 angegeben wird. Die Prozesse werden nacheinander abgearbeitet.

## Aufgabe 2
Der Prozess mit der CPU brauch für jede seiner Instruktionen einen Zeitschritt. Der I/O Prozess braucht 5 Zeitschritte für die Ausführung auf dem I/O Gerät (ist so vorgegeben) und nochmal jeweils einen Zeitschritt und zu initiieren und wieder abzuschließen. Diese beiden Befehle sind Instruktionen auf der CPU.

## Aufgabe 3
Es macht einen Unterschied, zu wechseln, jetzt dauern die beiden Prozesse nur noch 7 Schritte. Das liegt daran, dass gewechselt wird, wenn ein Prozess fertig ist oder wenn ein Prozess gerade mit der I/O beschäftigt ist. In diesem Fall werden also die 4 Instruktionen von dem CPU-Prozess zwischengeschoben.

## Aufgabe 4
Jetzt wird der CPU_Prozess nicht mehr zwischengeschoben, weil die Flag -S es verbietet. Also werden die beiden Prozesse hintereinander ausgeführt und brauchen wieder die maximale Anzahl von 11 Zeitschritten.

## Aufgabe 5
Jetzt passiert wieder dasselbe wie in Aufgabe 3

## Aufgabe 6
Die erste Instruktion aus dem I/O Prozess wird jetzt für 10 Instruktionen warten gelassen, weil alle drei CPU-Prozesse eingeschoben werden. Die Systemressourcen werden daher nicht sehr effizient benutzt, denn es gibt oft größere Lücken bei der Auslastung der CPU und der I/O Geräte.

## Aufgabe 7
Nun wird in jede Instruktion aus dem I/O Prozess ein CPU-Prozess eingeschoben, was eine sehr gute Auslastung der I/O Geräte und eine perfekte Auslastung der CPU erreicht. Die verbesserte Auslastung der I/O Geräte wird dadurch erreicht, dass man die anderen Prozesse kurz unterbricht, um die alte I/O Instruktion beenden zu lassen und eine neue zu starten. 

## Aufgabe 8
-s 1:
- IO_RUN_IMMEDIATE / IO_RUN_LATER: kein Unterschied, die Ressourcen werden effizient genutzt
- SWITCH_ON_IO / SWITCH_ON_END: SWITCH_ON_END ist nicht so effizient, weil die 3 CPU-intensiven Instruktionen erst am Ende ausgeführt werden, obwohl CPU davor sehr oft frei ist

-s 2:
- IO_RUN_IMMEDIATE / IO_RUN_LATER: kein Unterschied, auch hier werden die Ressourcen effizient genutzt
- SWITCH_ON_IO / SWITCH_ON_END: SWITCH_ON_END ist sehr uneffizient (da keine Parallelität), wohingegen SWITCH_ON_IO nicht besser performen könnte

-s 3:
- IO_RUN_IMMEDIATE / IO_RUN_LATER: IO_RUN_IMMEDIATE performt leicht besser (~53% vs. 50% CPU-Auslastung)
- SWITCH_ON_IO / SWITCH_ON_END: SWITCH_ON_END erzielt wiederholt eine schlechtere CPU-Auslastung (50% vs. 37,5%)