#----------------------------------- Kommando-Variablen
CXX = g++
CPPFLAGS = -I.
CXXFLAGS = -g -Wall -Wextra -Werror -std=c++11 -fPIC -pedantic
RM = rm -f
STATIC = ar rvs
DYNAMIC = -shared

#--------------------------------------- Hilfsvariablen
LIBNAME = aufgabe6
LIBTYPE = a
TARGET = lib$(LIBNAME)
OBJECTS = \
    benotung.o \
    fachnote.o \
    fachnoten_liste.o
SOURCES = $(OBJECTS:.o=.cpp)
HEADERS = $(OBJECTS:.o=.h)

LIBS = \
	$(TARGET).a \
	$(TARGET).so

#----------------------------------------- Musterregeln
%.o: %.cpp
	$(CXX) $(CPPFLAGS) $(CXXFLAGS) -c $< -o $@

#---------------------------------------- Standardziele
.PHONY: all clean

all: $(TARGET).$(LIBTYPE)

clean:
	$(RM) $(OBJECTS) $(TARGET).a $(TARGET).so depend
depend: $(SOURCES) $(HEADERS)
	$(CXX) -c $(CPPFLAGS) -MM $(SOURCES) > $@

#--------------------------------------- Ziele zur Programmerstellung
$(TARGET).a: $(OBJECTS)
	$(STATIC) $@ $^
$(TARGET).so: $(OBJECTS)
	$(CXX) $(DYNAMIC) $^ -o $@

#-------------------------------------- Abhaengigkeiten
include depend
