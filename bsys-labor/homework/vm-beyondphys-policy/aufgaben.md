# Antworten

## Aufgabe 1

```sh
./paging-policy.py -s <1|2|3> -n 10 -p FIFO -c
Access: 8  miss 8
Access: 7  miss 8 7
Access: 4  miss 8 7 4
Access: 2  miss 7 4 2
Access: 5  miss 4 2 5
Access: 4  hit 4 2 5
Access: 7  miss 2 5 7
Access: 3  miss 5 7 3
Access: 4  miss 7 3 4
Access: 5  miss 3 4 5
=> hit rate: 10%

Access: 1  miss 1
Access: 8  miss 1 8
Access: 7  miss 1 8 7
Access: 2  miss 8 7 2
Access: 4  miss 7 2 4
Access: 4  hit 7 2 4
Access: 6  miss 2 4 6
Access: 7  miss 4 6 7
Access: 0  miss 6 7 0
Access: 0  hit 6 7 0
=> hit rate: 20%

Access: 9  miss 9
Access: 9  hit 9
Access: 0  miss 9 0
Access: 0  hit 9 0
Access: 8  miss 9 0 8
Access: 7  miss 0 8 7
Access: 6  miss 8 7 6
Access: 3  miss 7 6 3
Access: 6  hit 7 6 3
Access: 6  hit 7 6 3
=> hit rate: 40%

./paging-policy.py -s <1|2|3> -n 10 -p LRU -c
Access: 8  miss 8
Access: 7  miss 8 7
Access: 4  miss 8 7 4
Access: 2  miss 7 4 2
Access: 5  miss 4 2 5
Access: 4  hit 2 5 4
Access: 7  miss 5 4 7
Access: 3  miss 4 7 3
Access: 4  hit 7 3 4
Access: 5  miss 3 4 5
=> hit rate: 20%

Access: 1  miss 1
Access: 8  miss 1 8
Access: 7  miss 1 8 7
Access: 2  miss 8 7 2
Access: 4  miss 7 2 4
Access: 4  hit 7 2 4
Access: 6  miss 2 4 6
Access: 7  miss 4 6 7
Access: 0  miss 6 7 0
Access: 0  hit 6 7 0
=> hit rate: 20%

Access: 9  miss 9
Access: 9  hit 9
Access: 0  miss 9 0
Access: 0  hit 9 0
Access: 8  miss 9 0 8
Access: 7  miss 0 8 7
Access: 6  miss 8 7 6
Access: 3  miss 7 6 3
Access: 6  hit 7 3 6
Access: 6  hit 7 3 6
=> hit rate: 40%

./paging-policy.py -s <1|2|3> -n 10 -p OPT -c
Access: 8  miss 8
Access: 7  miss 8 7
Access: 4  miss 8 7 4
Access: 2  miss 7 4 2
Access: 5  miss 7 4 5
Access: 4  hit 7 4 5
Access: 7  hit 7 4 5
Access: 3  miss 4 5 3
Access: 4  hit 4 5 3
Access: 5  hit 4 5 3
=> hit rate: 40%

Access: 1  miss 1
Access: 8  miss 1 8
Access: 7  miss 1 8 7
Access: 2  miss 1 7 2
Access: 4  miss 1 7 4
Access: 4  hit 1 7 4
Access: 6  miss 1 7 6
Access: 7  hit 1 7 6
Access: 0  miss 1 7 0
Access: 0  hit 1 7 0
=> hit rate: 30%

Access: 9  miss 9
Access: 9  hit 9
Access: 0  miss 9 0
Access: 0  hit 9 0
Access: 8  miss 9 0 8
Access: 7  miss 9 0 7
Access: 6  miss 9 0 6
Access: 3  miss 9 6 3
Access: 6  hit 9 6 3
Access: 6  hit 9 6 3
=> hit rate: 40%
```

## Aufgabe 2

```sh
./paging-policy.py -C 5 -p FIFO -a 1,2,3,4,5,6,1,2,3,4,5,6 -c
=> hit rate = 0%
=> Für eine cache size von 6 würde die hit rate 50% betragen.

./paging-policy.py -C 5 -p LRU -a 1,2,3,4,5,6,1,2,3,4,5,6 -c
=> hit rate = 0%
=> Für eine cache size von 6 würde die hit rate 50% betragen. LRU performt hierbei so schlecht, weil keine zeitliche Lokalität gegeben ist.

./paging-policy.py -C 5 -p MRU -a 1,2,3,4,5,6,5,6,5,6,5 -c
=> hit rate = 0%
=> Für eine cache size von 6 würde die hit rate 45,54% betragen. MRU erzielt hierbei keine hits, da der Cache mit den Adressen von 1 bis 5 gefüllt wird und dann abwechselnd 2 Adressen angefragt werden. Weil aber immer die aktuellste page ausgelagert wird, führt dies zu ständigen misses.
```

## Aufgabe 3

```sh
./paging-policy.py -s 0 -n 100000 -a -1 -p FIFO -c
=> Ich erwarte etwa 30% als hit rate, da vermutlich nur selten die zeitliche Lokalität
genutzt werden kann (eher geringe Wahrscheinlichkeit bei 10 verschiedenen Adressen).
Ergebnis: hit rate = 30.1%

./paging-policy.py -s 0 -n 100000 -a -1 -p LRU -c
=> ähnliche Performance wie die von FIFO erwarten (35%).
Ergebnis: hit rate = 30.0%

./paging-policy.py -s 0 -n 100000 -a -1 -p OPT -c
=> Als hit rate würde ich bei der optimal policy einen deutlich höheren Wert erwarten, nämlich etwa 60%.
Ergebnis: hit rate = 51.86%

./paging-policy.py -s 0 -n 100000 -a -1 -p RAND -c
=> Die random policy sollte ungefähr so gut wie FIFO bzw. LRU abschneiden, da Lokalität nicht genutzt wird.
Ergebnis: hit rate = 29.95%

./paging-policy.py -s 0 -n 100000 -a -1 -p CLOCK -c
=> Clock sollte etwas besser als FIFO bzw. RAND performen (Schätzung: 33%).
Ergebnis: hit rate = 29.82%
```

## Aufgabe 4

Man kann einen trace, der locality berücksichtigt, erstellen, indem man auf bestimmte Adressen in einer
bestimmten Zeit (bzw. in einem Teil vom trace) öfters zugreift.

./paging-policy.py -C 5 -p OPT -a 0,1,2,3,4,5,4,3,2,2,5,4,1,2,0 -c   
=> hit rate = 53.33%
./paging-policy.py -C 5 -p LRU -a 0,1,2,3,4,5,4,3,2,2,5,4,1,2,0 -c   
=> hit rate = 53.33%
./paging-policy.py -C 5 -p RAND -a 0,1,2,3,4,5,4,3,2,2,5,4,1,2,0 -c  
=> hit rate = 33.33%
./paging-policy.py -C 5 -b 2 -p CLOCK -a 0,1,2,3,4,5,4,3,2,2,5,4,1,2,0 -c 
=> hit rate = 46.67%

=> RAND performt angesichts der hit rate 20% schlechter als LRU. CLOCK kommt fast an LRU ran.
Die Anzahl der clockbits beeinflusst die hit rate bei diesem Beispiel nicht.