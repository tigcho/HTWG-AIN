package aufgabe1;

import aufgabe1.AbstractFrequencyTable;
import aufgabe1.Word;
import java.util.Arrays;

/**
 *
 * @author oliverbittel
 */
public class ArrayFrequencyTable extends AbstractFrequencyTable {

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
        // neues fqTable obj mit 0 Elementen
        size = 0;
        fqTable = new Word[DEFAULT_SIZE];
    }

    @Override
    public void add(String w, int f) {
        if (w == null || w.isEmpty()) { // umgeht NullException, ungueltige Daten ignoriert
            return;
        }

        int wordIdx = findIdx(w); // speichert Ergebnis der Methode
        if (wordIdx == -1) {
            // Das Wort ist nicht vorhanden
            if (size >= fqTable.length) { // Table vergroessern, wenn zu klein
                fqTable = Arrays.copyOf(fqTable, 2 * size);
            }
            fqTable[size] = new Word(w, f); // neues Wort wird angehängt
            size++;
            moveLeft(size - 1); // size-1 ist das vorletze Wort, weil das neue nicht vorhanden war
        } else {
            // Das Wort ist vorhanden
            fqTable[wordIdx].addFrequency(f);
            moveLeft(wordIdx); // wordIdx enthaelt das gesuchte Wort und wird verschoben
        }
    }

    @Override
    public Word get(int pos) {
        if (pos < 0 || pos >= size) { // prueft gueltigen Bereich, Index beginnt bei 0
            throw new IndexOutOfBoundsException();
        }
        return fqTable[pos];
    }

    @Override
    public int get(String w) {
        for (int i = 0; i < size; i++) {
            Word wd = get(i);
            if (wd.getWord().equals(w)) { // sucht das gesuchte Wort in dem Array
                return wd.getFrequency();
            }
        }
        return 0; // Wort gibt es nicht
    }

    private int findIdx (String w) {
        for (int i = 0; i < size; i++) {
            if (get(i).getWord().equals(w)) {
                return i; // gibt Index des gefundenen Wortes
            }
        }
        return -1; // Wort nicht gefunden
    }

    private void moveLeft(int pos) {
        Word x = fqTable[pos];
        int i = pos - 1; // Zeiger ein Schritt vor pos

        while (i >= 0 && fqTable[i].getFrequency() < x.getFrequency()) { // y muss < x sein, dann vertauschen
            fqTable[i + 1] = fqTable[i];
            i--; // Zeiger ueberprueft ein Element weniger
        }
        fqTable[i + 1] = x; // Wort wird an der neuen Stelle platziert
    }
}
