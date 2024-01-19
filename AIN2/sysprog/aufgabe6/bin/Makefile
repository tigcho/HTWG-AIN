#----------------------------------- Kommando-Variablen
CXX = g++
CPPFLAGS = -I$(LIB)
CXXFLAGS = -g -fPIC -Wall -Wextra -Werror -std=c++11 -pedantic
RM = rm -f
LDFLAGS = -L$(LIB)

#--------------------------------------- Hilfsvariablen
LIBNAME = aufgabe6
LIB = ../lib
LIBTYPE = a

TARGET = notenspiegel
OBJECTS = \
		$(TARGET).o
SOURCES = $(OBJECTS:.o=.cpp)
HEADERS = $(LIB)/fachnoten_liste.h \
		  $(LIB)/fachnote.h \
		  $(LIB)/benotung.h


#----------------------------------------- Musterregeln
%.o: %.cpp
	$(CXX) $(CPPFLAGS) $(CXXFLAGS) -c $< -o $@

#---------------------------------------- Standardziele
.PHONY: all clean

all: $(TARGET)
clean:
	$(RM) $(OBJECTS) $(TARGET) depend
depend: $(SOURCES) $(HEADERS)
	$(CXX) $(CPPFLAGS) -MM $(SOURCES) > $@

#--------------------------------------- Ziele zur Programmerstellung
$(TARGET): $(OBJECTS) $(LIB)/lib$(LIBNAME).$(LIBTYPE)
	$(CXX) -o $@ $< $(LDFLAGS) -l$(LIBNAME)

#-------------------------------------- Abhaengigkeiten
include depend