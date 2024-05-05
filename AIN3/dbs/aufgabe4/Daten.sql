create table Ferienwohnung (
    WohnungsID char(10) primary key,
    Wohnungsname varchar2(50) not null,
    AnzahlZimmer int not null check (AnzahlZimmer > 0),
    Groesse float not null check (Groesse > 0),
    Preis float not null check (Preis > 0),
    AdressID char(10),
    constraint fk_Adresse foreign key (AdressID) references Adresse(AdressID)
);

create table BeinhalteteAusstattung (
    WohnungsID char(10),
    Ausstattungsname varchar2(50),
    primary key (WohnungsID, Ausstattungsname),
    constraint fk_Ferienwohnung foreign key (WohnungsID) references Ferienwohnung(WohnungsID),
    constraint fk_Ausstattung foreign key (Ausstattungsname) references Ausstattung(Ausstattungsname)
);

create table Ausstattung (
    Ausstattungsname varchar2(50) primary key
);

create table Bild (
    Link varchar2(200) primary key,
    WohnungsID char(10),
    constraint fk_Ferienwohnung foreign key (WohnungsID) references Ferienwohnung(WohnungsID)
);

create table Kunde (
    Mailadresse varchar2(35) primary key,
    Passwort varchar2(35) not null check (length(Passwort) >= 8),
    Name varchar2(50) not null,
    IBAN varchar2(22) not null,
    AdressID char(10),
    constraint fk_Adresse foreign key (AdressID) references Adresse(AdressID)
);

create table Buchung (
    Buchungsnummer int primary key,
    Startdatum date not null,
    Enddatum date not null check (Startdatum < Enddatum),
    Stornodatum date,
    Rechnungsnummer int,
    Rechnungsdatum int,
    Betrag float not null check (Betrag > 0),
    Bewertungsdatum date,
    Bewertungssterne int,
    WohnungsID char(10),
    Mailadresse varchar2(35),
    constraint fk_Ferienwohnung foreign key (WohnungsID) references Ferienwohnung(WohnungsID),
    constraint fk_Kunde foreign key (Mailadresse) references Kunde(Mailadresse),
    check (Bewertungssterne between 1 and 5)
);

create table Anzahlung (
    AnzahlungsID char(10) primary key,
    Geldbetrag float not null,
    Zahlungsdatum date not null,
    Buchungsnummer int,
    constraint fk_Buchung foreign key (Buchungsnummer) references Buchung(Buchungsnummer)
);

create table Adresse (
    AdressID char(10) primary key,
    Strasse varchar2(50) not null,
    Hausnummer int not null check (Hausnummer > 0),
    Postleitzahl varchar2(5) not null check (regexp_like(Postleitzahl, '^d{5}$')),
    Stadt varchar2(50) not null,
    Land varchar2(50) not null,
    constraint fk_Land foreign key (Land) references Land(Laendername)
);

create table Land (
    Laendername varchar2(50) primary key
);

create table AttraktionEntfernung (
    WohnungsID char(10),
    Attraktionsname varchar2(50),
    Kilometer float not null check (Kilometer > 0),
    primary key (WohnungsID, Attraktionsname),
    constraint fk_Ferienwohnung foreign key (WohnungsID) references Ferienwohnung(WohnungsID),
    constraint fk_Attraktion foreign key (Attraktionsname) references Attraktion(Attraktionsname)
);

create table Attraktion (
    Attraktionsname varchar2(50) primary key,
    Beschreibung varchar2(200) not null
);


