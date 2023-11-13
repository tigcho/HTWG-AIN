package aufgabe6;

/**
 * Instanziierbare Klasse fuer Entitaeten als Unterklassen von .Leistung.
 */
public class UnbenoteteLeistung extends Leistung {

    // konstante private Instanzvariable, die Leistung speichert.
    private final boolean bestanden;

    /**
     * oeffentlicher Konstruktor fuer Objekte vom Typ UnbenoteteLeistung.
     * @param fach das Fach
     * @param bestanden ob bestanden wurde oder nicht
     */
    public UnbenoteteLeistung(String fach, boolean bestanden) {
        super(fach);
        this.bestanden = bestanden;
    }

    /**
     * Ob es benotet ist.
     * @return Ob es benotet ist.
     */
    public boolean istBestanden() {
        return bestanden;
    }

    /**
     * ob es bestanden ist.
     * @return ob es bestanden ist.
     */
    public boolean istBenotet() {
        return false;
    }

    /**
     * Note des Fachs in Worten.
     * @return ob es bestanden ist oder nicht.
     */
    @Override
    public String getNoteInWorten() {
        if (istBestanden()) {
            return "bestanden";
        }
        return "nicht bestanden";
    }
}