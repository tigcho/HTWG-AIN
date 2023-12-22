#ifndef BENOTUNG_H
#define BENOTUNG_H

class benotung {

// TODO: private Membervariable note vom Typ int
private:
    int note;

public:

// TODO: expliziten Konstruktor benotung(int)
    explicit benotung(int n);

// TODO: zwei öffentliche konstante statische Membervariablen
    static const benotung beste;
    static const benotung schlechteste;

// TODO: öffentliche Memberfunktion int int_value()
    int int_value() const;

// TODO: öffentliche Memberfunktion bool ist_bestanden()...
    bool ist_bestanden() const;

// TODO: befreundete Funktion bool operator==(benotung, benotung)...
    friend bool operator==(const benotung&, const benotung&);
};

#endif