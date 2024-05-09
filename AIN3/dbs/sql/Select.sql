SELECT * FROM dbsys34.adresse WHERE ADRESS_ID = 1;
SELECT * FROM dbsys34.adresse WHERE ADRESS_ID = 2;
SELECT * FROM dbsys34.ferienwohnung w, dbsys34.beinhaltete_ausstattung baus, dbsys34.ausstattung aus
    WHERE w.WOHNUNGS_ID = 1 AND baus.WOHNUNGS_ID = w.WOHNUNGS_ID AND aus.AUSSTATTUNGSNAME = baus.AUSSTATTUNGSNAME;
SELECT * FROM dbsys34.bild b, dbsys34.ferienwohnung f WHERE f.WOHNUNGS_ID = 1 AND f.WOHNUNGS_ID = b.wohnungs_id;
SELECT attr.* FROM dbsys34.ferienwohnung f, dbsys34.attraktion attr, dbsys34.attraktions_entfernung ae
    WHERE f.WOHNUNGS_ID = 1 AND f.WOHNUNGS_ID = ae.WOHNUNGS_ID AND attr.ATTRAKTIONSNAME = ae.ATTRAKTIONSNAME;
SELECT * FROM dbsys34.buchung WHERE buchungsnummer = 1;
SELECT * FROM dbsys34.kunde WHERE mail = 'mustermann@muster.com';
