# Antworten

## Aufgabe 2
50 MB:
    before: used = 3344; free = 1940; available = 10446
    while: used = 3348; free = 1946; available = 10452
    after: used = 3344; free = 1955; available = 10460

1000 MB:
    before: used = 3341; free = 1969; available = 10474
    while: used = 3390; free = 1910; available = 10416
    after: used = 3295; free = 2015; available = 10520

100000 MB:
    before: used = 3302; free = 1999; available = 10504
    while: used = 3314; free = 1982; available = 10488
    after: used = 3298; free = 2004; available = 10510

Man sieht, dass bei den ersten beiden Versuchen nicht alle MBs tatsächlich gebraucht werden. Beim dritten Versuch ist das Verhältnis noch stärker, hier lässt sich kaum ein unterschied zwischen laufen und nicht laufen ausmachen.

## Aufgabe 7
Neben dem Stack sieht man außerdem noch viele anon Mappings und viele Andere mit verschiedenen Namen wie z. B. libcairo.so.2.11706.0 oder ein paar Fonts.

## Aufgabe 8
Bei den ersten beiden ist der gesamte Speicherbereich ein paar Kilobytes größer als der Bereich für das Array, wie es auch gedacht ist.
Bei 100.000 MB wird allerdings nicht mehr wesentlich mehr Speicher verbraucht, als bei den 1000 MB.
