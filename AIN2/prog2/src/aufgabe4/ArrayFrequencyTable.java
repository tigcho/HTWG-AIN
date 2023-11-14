package aufgabe4;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayFrequencyTable<T> extends AbstractFrequencyTable<T> {
    private int size = 0;
    private Element<T>[] fqTable;
    private final int DEFAULT_SIZE = 100;

    public ArrayFrequencyTable() {
        clear();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public final void clear() {
        size = 0;
        fqTable = new Element[DEFAULT_SIZE];
    }

    @Override
    public void add(T s, int f) {
        if (s == null || f < 1) {
            return;
        }

        int wordIdx = findIdx(s);
        if (wordIdx == -1) {
            if (size >= fqTable.length) {
                fqTable = Arrays.copyOf(fqTable, 2 * size);
            }
            fqTable[size] = new Element<>(s, f);
            size++;
            moveLeft(size - 1);
        } else {
            fqTable[wordIdx].addFrequency(f);
            moveLeft(wordIdx);
        }
    }

    @Override
    public Element<T> get(int pos) {
        if (pos < 0 || pos >= size) {
            throw new IndexOutOfBoundsException();
        }
        return fqTable[pos];
    }

    @Override
    public int get(T s) {
        for (int i = 0; i < size; i++) {
            if (get(i).getElement().equals(s)) {
                return get(i).getFrequency();
            }
        }
        return 0;
    }

    private int findIdx(T w) {
        for (int i = 0; i < size; i++) {
            if (get(i).getElement().equals(w)) {
                return i;
            }
        }
        return -1;
    }

    private void moveLeft(int pos) {
        Element<T> x = fqTable[pos];
        int i = pos - 1;

        while (i >= 0 && fqTable[i].getFrequency() < x.getFrequency()) {
            fqTable[i + 1] = fqTable[i];
            i--;
        }
        fqTable[i + 1] = x;
    }

    @Override
    public Iterator<Element<T>> iterator() {
        return new Iterator<>() {
            private int currentPos = 0;

            @Override
            public boolean hasNext() {
                return currentPos < size;
            }

            @Override
            public Element<T> next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return fqTable[currentPos++];
            }
        };
    }
}
