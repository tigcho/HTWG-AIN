#include "fachnote.h"
#include <stdexcept>

fachnote::fachnote(const std::string &f, const benotung &b)
        : fach(f), note(b)
{
// TODO: Werfen Sie eine Ausnahme vom Typ std::invalid_argument, wenn 0
    if (f.empty())
    {
        throw std::invalid_argument("unzulaessiges Fach");
    }
}