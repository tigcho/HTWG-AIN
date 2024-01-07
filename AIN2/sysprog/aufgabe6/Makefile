SUBDIRS = lib bin

.PHONY: all clean $(SUBDIRS)

all clean:
	for p in $(SUBDIRS); do \
  # p ist keine Variable des Makefiles sondern eine Variable
  # des Shell-Skripts. $p würde make durch einen leeren String
  # ersetzen, weil p im Makefile undefiniert ist. Aus $$ macht
  # make ein einfaches $, sodass die ausführende Shell die
  # richtige Syntax $p sieht.
		(cd $$p && $(MAKE) $@); \
	done