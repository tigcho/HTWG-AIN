#----------------------------------- Kommando-Variablen
CXX = g++
CPPFLAGS = -I.
CXXFLAGS = -g -Wall -Wextra -Werror -std=c++11 -pedantic
RM = rm -f
LDFLAGS = -L../lib -laufgabe6

#--------------------------------------- Hilfsvariablen
LIBNAME = aufgabe6
LIB = ../lib

TARGET = notenspiegel
OBJECTS = $(TARGET).o
SOURCES = $(OBJECTS:.o=.cpp)
HEADERS = $(LIB)/$(OBJECTS:.o=.h)

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
$(TARGET): $(TARGET).o $(OBJECTS)
	$(CXX) $(CPPFLAGS) -o $@ $^ $(LDFLAGS)

#-------------------------------------- Abhaengigkeiten
include depend