#include "benotung.h"
#include <stdexcept>

benotung benotung::beste = benotung(10);
benotung benotung::schlechteste = benotung(50);

benotung::benotung(int n)    : note(n) {
    if (note != 10 && note != 13 && note != 17 && note != 20 && note != 23 &&
        note != 27 && note != 30 && note != 33 && note != 37 && note != 40 &&
        note != 50) {
        throw std::invalid_argument("unzulaessige Note " + std::to_string(note));
    }
}

int benotung::int_value() {
    return note;
}

bool benotung::ist_bestanden() {
    return note <= 40;
}

bool operator==(benotung &a, benotung &b) {
    return a.note == b.note;
}
