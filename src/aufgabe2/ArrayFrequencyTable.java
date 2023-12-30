package aufgabe2;

import aufgabe2.AbstractFrequencyTable;
import aufgabe2.Word;
import java.util.Arrays;

/**
 *
 * @author oliverbittel
 */
public class ArrayFrequencyTable extends AbstractFrequencyTable {
    // TODO: Instanzvariablen festlegen
    private int size = 0;
    private Word fqTable[];
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
        // TODO: Ziel ist fqTable mit 0 Elementen
        size = 0;
        fqTable = new Word[DEFAULT_SIZE];
    }

    @Override
    public void add(String w, int f) {
        // TODO: NullException Behandlung, ungueltige Daten ignorieren
        if (w == null || w.isEmpty()) {
            return;
        }

        // TODO: Position des Wortes speichern, die findIdx zurueckgibt
        int wordIdx = findIdx(w);
        // TODO: Wort ist nicht vorhanden
        if (wordIdx == -1) {
            // TODO: Table vergroessern, wenn zu klein
            if (size >= fqTable.length) {
                fqTable = Arrays.copyOf(fqTable, 2 * size);
            }
            // TODO: neues Wort wird anhaengen
            fqTable[size] = new Word(w, f);
            size++;
            moveLeft(size - 1);
        } else {
            // TODO: Wort ist vorhanden
            fqTable[wordIdx].addFrequency(f);
            moveLeft(wordIdx);
        }
    }

    @Override
    public Word get(int pos) {
        // TODO: IndexOutOfBounds-Behandlung, return Positon des Wortes
        if (pos < 0 || pos >= size) {
            throw new IndexOutOfBoundsException();
        }
        return fqTable[pos];
    }

    @Override
    public int get(String w) {
        // TODO: Alle Positionen durchgehen, Woerter und Haeufigkeit herholen
        for (int i = 0; i < size; i++) {
            if (get(i).getWord().equals(w)) {
                return get(i).getFrequency();
            }
        }
        return 0;
    }

    // TODO: Position eines Wortes suchen und zurueckgeben
    private int findIdx (String w) {
        for (int i = 0; i < size; i++) {
            if (get(i).getWord().equals(w)) {
                return i;
            }
        }
        return -1;
    }

    // TODO: Zeiger setzen, Woerter nach Haeufigkeit vergleichen und vertauschen
    private void moveLeft(int pos) {
        Word x = fqTable[pos];
        int i = pos - 1;

        while (i >= 0 && fqTable[i].getFrequency() < x.getFrequency()) {
            fqTable[i + 1] = fqTable[i];
            i--;
        }
        fqTable[i + 1] = x;
    }
}
