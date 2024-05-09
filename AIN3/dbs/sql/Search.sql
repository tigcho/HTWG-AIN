/** Alle Anfragen sollen unter dem Kundenkonto „DBSYS49“ ausgeführt werden. */

/** a) Wie viele Ferienwohnungen wurden noch nie gebucht? */
SELECT COUNT(*) 
FROM DBSYS49.ferienwohnung f
WHERE wohnungsname NOT IN (SELECT wohnungsname FROM DBSYS49.buchung);


/** b) Welche Kunden haben EINE Ferienwohnung bereits mehrmals gebucht? */
SELECT b.mail, f.wohnungsname, COUNT(*)
FROM DBSYS49.buchung b
JOIN DBSYS49.ferienwohnung f ON b.wohnungs_id = f.wohnungs_id
GROUP BY b.mail, f.wohnungsname
HAVING COUNT(*) > 1;


/** c) Welche Ferienwohnungen in Spanien haben durchschnittlich mehr als 4 Sterne erhalten? */
SELECT f.wohnungsname
FROM DBSYS49.ferienwohnung f
JOIN DBSYS49.adresse a ON f.adress_id = a.adress_id
JOIN DBSYS49.buchung b ON f.wohnungs_id = b.wohnungs_id
WHERE a.laendername = 'Spanien'
GROUP BY f.wohnungsname
HAVING AVG(b.bewertungssterne) > 4;


/** d) Welche Ferienwohnung hat die meisten Ausstattungen? Falls mehrere Ferienwohnungen das
Maximum an Ausstattungen erreichen, sollen diese alle ausgegeben werden. */
SELECT f.wohnungsname, COUNT(ba.ausstattungsname) 
FROM DBSYS49.ferienwohnung f
JOIN DBSYS49.beinhaltete_ausstattung ba ON f.wohnungs_id = ba.wohnungs_id
GROUP BY f.wohnungsname
HAVING COUNT(ba.ausstattungsname) = (
    SELECT MAX(anzahl_ausstattungen)
    FROM (
        SELECT COUNT(ba.ausstattungsname)
        FROM DBSYS49.ferienwohnung f
        JOIN DBSYS49.beinhaltete_ausstattung ba ON f.wohnungs_id = ba.wohnungs_id
        GROUP BY f.wohnungsname
    )
);


/** e) Wie viele Reservierungen gibt es für die einzelnen Länder? Länder, in denen keine Reservierungen
existieren, sollen mit der Anzahl 0 ebenfalls aufgeführt werden. Das Ergebnis soll nach der Anzahl
Reservierungen absteigend sortiert werden. */
SELECT NVL(a.laendername, 'Keine Reservierungen') AS laendername, COUNT(b.buchungsnummer)
FROM DBSYS49.adresse a
LEFT JOIN DBSYS49.ferienwohnung f ON a.adress_id = f.adress_id
LEFT JOIN DBSYS49.buchung b ON f.wohnungs_id = b.wohnungs_id
GROUP BY a.laendername
ORDER BY NVL(COUNT(b.buchungsnummer), 0) DESC, a.laendername ASC;


/** f) Wie viele Ferienwohnungen sind pro Stadtnamen gespeichert? */
SELECT a.stadt, COUNT(f.wohnungs_id) 
FROM DBSYS49.adresse a
JOIN DBSYS49.ferienwohnung f ON a.adress_id = f.adress_id
GROUP BY a.stadt;


/** g) Welche Ferienwohnungen haben bisher nur Bewertungen mit einem Stern erhalten? */
SELECT f.wohnungsname
FROM DBSYS49.ferienwohnung f
JOIN DBSYS49.buchung b ON f.wohnungs_id = b.wohnungs_id
GROUP BY f.wohnungsname
HAVING MIN(b.bewertungssterne) = 1 AND MAX(b.bewertungssterne) = 1;


/** h) Welche Ferienwohnungen mit Sauna sind in Spanien in der Zeit vom 01.05.2024 – 21.05.2024 noch
frei? Geben Sie den Ferienwohnungs-Namen und deren durchschnittliche Bewertung an.
Ferienwohnungen mit guten Bewertungen sollen zuerst angezeigt werden. Ferienwohnungen ohne
Bewertungen sollen am Ende ausgegeben werden. */
SELECT f.wohnungsname, AVG(b.bewertungssterne)
FROM DBSYS49.ferienwohnung f
JOIN DBSYS49.beinhaltete_ausstattung ba ON f.wohnungs_id = ba.wohnungs_id
JOIN DBSYS49.ausstattung a ON ba.ausstattungsname = a.ausstattungsname
JOIN DBSYS49.adresse ad ON f.adress_id = ad.adress_id
LEFT JOIN DBSYS49.buchung b ON f.wohnungs_id = b.wohnungs_id
WHERE a.ausstattungsname = 'Sauna' AND ad.laendername = 'Spanien' 
AND NOT EXISTS (
    SELECT 1 FROM DBSYS49.buchung b2 
    WHERE b2.wohnungs_id = f.wohnungs_id 
    AND b2.startdatum <= TO_DATE('2024/05/21', 'yyyy/mm/dd') 
    AND b2.enddatum >= TO_DATE('2024/05/01', 'yyyy/mm/dd')
)
GROUP BY f.wohnungsname
ORDER BY AVG(b.bewertungssterne) DESC NULLS LAST;