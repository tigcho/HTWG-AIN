package aufgabe5;

/**
 * instanziierbare Klasse Fachnote.
 */
public final class Fachnote {

    /**
     * öffentliche konstante Instanzvariable vom Typ String.
     */
    public final String fach;

    /**
     * öffentliche konstante Instanzvariable vom Typ Note.
     */
    public final Note note;

    /**
     * öffentlicher Konstruktor zum Initialisieren der beiden Instanzvariablen.
     * ist öffentlich, damit andere Klassen diese Instanzvariablen haben können
     * @param fach die zur Fachnote gehört
     * @param note vom Fach
     */
    public Fachnote(String fach, Note note) {
        if (fach == null || fach.length() == 0) {
            throw new IllegalArgumentException("Fachname darf nicht 0 sein "
                                               + "und Länge 0 haben.");
        }
        if (note == null) {
            throw new IllegalArgumentException("Note darf nicht 0 sein.");
        }
    /*
     * Parameter fach und note werden Instanzvariablen this zugewiesen.
     * this ist aktuelles Objekt und nicht lokale Variable fach
     * Werte werden überwiesen, damit sie in andere Methoden
     */
        this.fach = fach;
        this.note = note;
    }
}