package aufgabe5;

/**
 * instanziierbare Klasse für Wertobjekte.
 * instanziierbare Klassen legen mit Instanzvariablen den Aufbau
 * von Objekten fest, mit Konstruktoren deren Initialisierung und mit
 * Instanzmethoden deren Verhalten
 */
public final class Note {
    private int note;

    /**
     * privater Konstruktor zum Initialisieren der Instanzvariable.
     * Konstruktor hat keinen Rückgabewert, erstellt Objekt in der Klasse
     * Instanzvariable speichert den Wert eines Objektes, hier note
     * sie sind auch für Objekte individuell erstellt.
     * @param note wird initialisiert
     */
    private Note(int note) {
        this.note = note;
    }

    /**
     * beste mögliche Note definieren.
     * Klassenvarialen werden mit allen Objekten innerhalb einer Klasse geteilt
     */
    public static final Note BESTE = new Note(1);

    /**
     * schlechteste mögliche Note definieren.
     */
    public static final Note SCHLECHTESTE = new Note(5);

    /**
     * öffentliche Fabrikmethode die ein Objekt einer Klasse zurückgibt.
     * sind Klassenmethoden, die eine Instanz ihrer Klasse liefern
     * IllegalArgumentException, wenn ungültiger Wert übergeben wird.
     * @param note ist vom Typ int
     * @return Wert ist ein Note-Objekt
     */
    public static Note valueOf(int note) {
        switch (note) {
        case 10, 13, 17, 20, 23, 27, 30, 33, 37, 40, 50:
            return new Note(note);
        default:
            throw new IllegalArgumentException("unzulaessige Note " + note);
        }
    }

    /**
     * weitere öffentliche Fabrikmethode mit Fehlertext.
     * Fabrikmethode = erzeugt Objekte
     * @param note vom Typ String
     * @return im Format toString
     */
    public static Note valueOf(String note) {
        switch (note) {
        case "1,0", "1,3", "1,7", "2,0", "2,3", "2,7", "3,0", "3,3", "3,7",
                "4,0", "5,0":
            return new Note(Integer.parseInt(note.replace(",",
                    "")));
        default:
            throw new IllegalArgumentException("unzulaessige Note " + note);
        }
    }

    /**
     * öffentliche Instanzmethode, die im Objekt gespeicherte Note liefert.
     * Instanzmethode = Ausführung Objekte
     * @return note als int
     */
    public int intValue() {
        return this.note;
    }

    /**
     * öffentliche Instanzmethode, true wenn Wert größer als 40.
     * @return true oder false
     */
    public boolean istBestanden() {
        return this.note <= 40;
    }

    /**
     * öffentliche Instanzmethode toString.
     * Override überschreibt Instanzmethode und formatiert sie.
     * Wert der Instanzvariable erst durch 10 teilen mit einer NK-Stelle
     * @return String Format der Note
     */
    @Override
    public String toString() {
        return String.format("%.1f", note / 10.0).replace('.', ',');
    }

    /**
     * öffentliche Instanzmethode prüft, ob zwei Objekte die gleiche.
     * Instanzvariablen haben
     * Override ist nötig, damit der Compiler überprüft, ob tatsächlich
     * eine Methode einer Oberklasse überschrieben wird
     * @param o das zu vergleichende Objekt
     * @return true wenn sie gleich sind
     */
    @Override
    public boolean equals(Object o) {
        // ist Parameter o Instanz der Klasse? ja?
        if (o instanceof Note) {
            // dann Casting in Variable that vom Typ Note
            Note that = (Note) o;
            // aktuelles Objekt wird mit übergebenem Objekt verglichen
            return this.note == that.note;
        }
        return false;
    }

    /**
     * Erstelle einen hashcode mit dem Wert der Note.
     * @return die Note
     */
    @Override
    public int hashCode() {
        // verwendet Wert der Instanzvariable
        return this.note;
    }
}