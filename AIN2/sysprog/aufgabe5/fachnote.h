#ifndef FACHNOTE_H
#define FACHNOTE_H

#include "benotung.h"
#include <string>

class fachnote
{
public:

//TODO: zwei öffentliche konstante Membervariablen fach vom Typ std::string und note vom Typ benotung
const std::string fach;
const benotung note;

// TODO: öffentlichen Konstruktor fachnote(const std::string&, const benotung&)
fachnote(const std::string&, const benotung&);

/* überlegen Sie, welche impliziten Memberfunktionen Sie mit = delete unterdrücken müssen
 *
 *
 * */
fachnote(const fachnote&) = delete;
fachnote& operator=(const fachnote&) = delete;
};

#endif