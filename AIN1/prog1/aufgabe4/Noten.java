package aufgabe4;

/**
 * Utility-Klasse im Paket aufgabe4 nur Klassenmethoden/-Variablen.
 */
public final class Noten {
    private Noten() { } // Konstruktor ohne Rückgabetyp

    /**
     * zwei öffentliche (public) konstanten Klassenvariablen (static).
     */
    public static final double BESTE = 1.0;
    /**
     * definiert die schlechtere konstante Klassenvariable.
     */
    public static final double SCHLECHTESTE = 5.0;

    /**
     * öffentliche (public) Klassenmethoden (static).
     * (String note) ist Parameterliste
     * @param note wird hier überprüft
     * @return boolean ist Rückgabetyp
     */
    public static boolean istZulaessig(String note) { // Kopf
        /*
          ueberprüft, ob das erste Zeichen des Strings eine Ziffer ist
          ob das zweite Zeichen ein Punkt oder Komma ist
          ob das dritte Zeichen wieder eine Ziffer ist
          falls nicht, dann Rueckgabewert 'false'
         */
        if (note.length() != 3
            || !Character.isDigit(note.charAt(0))          // Rumpf
            || (note.charAt(1) != '.' && note.charAt(1) != ',')
            || !Character.isDigit(note.charAt(2))) {
            return false;
        }
        /*
          überprüft, ob das erste Zeichen eine 1, 2 oder 3 ist. Falls ja,
          wird überprüft, ob das dritte Zeichen eine 0, 3 oder 7 ist.
          Falls nicht, ist die Note ungültig. (siehe javadoc files)
          Für die Ziffern 4 und 5 an erster Stelle ist nur die Nachkomma
          -stelle 0 gueltig. Ansonsten 'false'
         */
        switch (note.charAt(0)) {
        case '1', '2', '3':
            if (note.charAt(2) == '0'
                || note.charAt(2) == '3'
                || note.charAt(2) == '7') {
                return true;
            }
            break;
        case '4', '5':
            if (note.charAt(2) == '0') {
                return true;
            }
            break;
        default:
            return false;
        }
        return false;
    }

    /**
     * oeffentliche Klassenmethode toDouble.
     * konvertiere String in Gleitkommazahl und ersetze Komma mit Punkt
     * @param note wird überprüft
     * @return double ist Rückgabetyp von konvertiertem String
     */
    public static double toDouble(String note) {
        if (!istZulaessig(note)) {
            throw new IllegalArgumentException("Ungültige Note: " + note);
        }
        return Double.parseDouble(note.replace(',', '.'));
    }

    /**
     * öffentliche Klassenmethode, Parametertyp double statt String.
     * gibt String-Darstellung der Note zurück mit String.format
     * @param note wird überprüft
     * @return String ist Rückgabetyp
     */
    public static String toString(double note) {
        if (note < BESTE || note > SCHLECHTESTE) {
            throw new IllegalArgumentException("Ungültige Note: " + note);
        }
        return String.format("%.1f", note);
    }

    /**
     * öffentliche Klassenmethode istBestanden, Note kleiner gleich 4.
     * @param note wird überprüft
     * @return bestandene Note kleiner gleich 4
     */
    public static boolean istBestanden(double note) {
        return note <= 4.0; //wird automatisch als true oder false interpretiert
    }

    /**
     * bessere und schlechtere Noten werden hier definiert. Zwei Noten werden.
     * miteinander verglichen, in either case wird auf jeden Fall die
     * kleinere Note returned. Analog zu schlechtere Note.
     * @param note1 wird zuerst überprüft
     * @param note2 wird danach überprüft
     * @return Wert der besseren Note
     */
    public static double bessere(double note1, double note2) {
        if (note1 < note2) {
            return note1;
        } else {
            return note2;
        }
    }

    /**
     * funktioniert wie oben.
     * @param note1 wird zuerst überprüft
     * @param note2 wird danach überprüft
     * @return Wert der schlechteren Note
     */
    public static double schlechtere(double note1, double note2) {
        if (note1 > note2) {
            return note1;
        } else {
            return note2;
        }
    }
}