package aufgabe6;
import aufgabe5.Note;

/**
 * Instanziierbare Klasse fuer Entitaeten als Unterklasse von .Leistung.
 */
public class BenoteteLeistung extends Leistung {

    // konstante private Instanzvariable vom Typ aufgabe5.Note
    private final Note note;

    /**
     * Konstruktor Benotete Leistung.
     * @param fach das Fach
     * @param note die Note
     */
    public BenoteteLeistung(String fach, String note) {
        super(fach);
        this.note = Note.valueOf(note);
    }

    /**
     * ob das Fach bestanden ist.
     * @return ob das Fach bestanden ist.
     */
    @Override
    public boolean istBestanden() {
        return note.istBestanden();
    }

    /**
     * note ueberschreiben.
     * @return die note.
     */
    public String getNote() {
        if (istBenotet()) {
            return this.note.toString();
        }
        return "";
    }

    /**
     * ob das Fach benotet ist.
     * @return ob das Fach benotet ist.
     */
    public boolean istBenotet() {
        return true;
    }

    /**
     * liefert die Note des Fachs in Worten.
     * @return bestanden oder nicht bestanden.
     */
    @Override
    public String getNoteInWorten() {
        if (istBestanden()) {
            if (note.intValue() >= 10 && note.intValue() <= 15) {
                return "sehr gut";
            } else if (note.intValue() >= 16 && note.intValue() <= 25) {
                return "gut";
            } else if (note.intValue() >= 26 && note.intValue() <= 35) {
                return "befriedigend";
            } else if (note.intValue() >= 36 && note.intValue() <= 40) {
                return "ausreichend";
            }
        } else {
            return "nicht ausreichend";
        }
        return "";
    }
}