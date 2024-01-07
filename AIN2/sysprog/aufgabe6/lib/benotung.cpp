#include "benotung.h"
#include <stdexcept>

const benotung benotung::beste = benotung(10);
const benotung benotung::schlechteste = benotung(50);

// TODO: öffentlichen expliziten Konstruktor benotung(int)
benotung::benotung(int n)    : note(n) {

// TODO: Werfen Sie bei unzulässiger Note eine Ausnahme vom Typ std::invalid_argument
    if (note != 10 && note != 13 && note != 17 && note != 20 && note != 23 &&
        note != 27 && note != 30 && note != 33 && note != 37 && note != 40 &&
        note != 50) {
        throw std::invalid_argument("unzulaessige Note " + std::to_string(note));
    }
}

int benotung::int_value() const {
    return note;
}

// TODO: ...die true liefert, wenn der Wert der gekapselten Note kleiner oder gleich 40 ist
bool benotung::ist_bestanden() const {
    return note <= 40;
}

// TODO: ...die true liefert, wenn beide Objekte die gleiche Noten kapseln
bool operator==(const benotung &a, const benotung &b) {
    return a.int_value() == b.int_value();
}
