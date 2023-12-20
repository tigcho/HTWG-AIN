#ifndef BENOTUNG_H
#define BENOTUNG_H

class benotung {
private:
    int note;

public:
    static const benotung beste;
    static const benotung schlechteste;

    int int_value();
    bool ist_bestanden();

    friend bool operator==(benotung, benotung);
};

#endif