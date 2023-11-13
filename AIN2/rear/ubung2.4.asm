a)
Das Register $ra ist ein Register für die Rücksprungadresse, um 
zum Ausgangspunkt zurückzukehren. Die Rückkehradresse ist die 
Adresse des nächsten Befehls, der nach dem Funktionsaufruf 
ausgeführt werden soll.

    jal rekmul: Hier wird die rekmul Funktion aufgerufen. Der Befehl jal (Jump and Link) 
    speichert die Rückkehradresse (Adresse des Befehls nach jal rekmul) im $ra-Register, 
    bevor er zur Funktion springt.

    jal rekmul: Innerhalb der rekmul-Funktion der Befehl selbst rekursiv auf. Bei jedem 
    rekursiven Aufruf wird die Rückkehradresse auf den Stapel gelegt.

    jr $ra: (Jump and Return) springt zur im $ra-Register gespeicherten Adresse zurück, 
    wenn eine Funktion beendet ist, damit an der richtigen Stelle weitergemacht wird.
    
b)
Nach der Ausführung von Zeile 58 (jr $ra) läuft das Programm an der Adresse weiter, die
im $ra-Register gespeichert wurde, d.h. die Adresse des nächsten Befehls nach
dem jal-Aufruf. Hier die Adresse, die in Zeile 50 während des rekursiven Aufrufs von 
jal rekmul im $ra-Register gespeichert wurde. D.h., dass das Programm nach der rekursiven 
rekmul-Funktion an der Adresse in Zeile 50 fortgesetzt wird.