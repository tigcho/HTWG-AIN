SUBDIRS = lib bin

.PHONY: all clean $(SUBDIRS)

all clean:
	for p in $(SUBDIRS); do \
		(cd $$p && $(MAKE) $@); \
	done