package aufgabe6;

/**
 * Utility-Klassse um Faecher festzulegen aus AIN1.
 */
public final class Faecher {
    private Faecher() { }

    // private konstante Klassenvariable FAECHER vom Typ Array von Strings
    private static final String[] FAECHER = {
        "Digitaltechnik",
        "Mathematik 1",
        "Softwaremodellierung",
        "Programmiertechnik 1"};

    /**
     * oeffentliche Klassenmethode, die was prueft.
     * For-Schleife geht jedes Element fach im Array FAECHER durch und
     * ueberprueft, ob f mit einem Fach im Array übereinstimmt.
     * @param f gegebenes Fach
     * @return true wenn es im Stundenplan ist
     */

    public static boolean istZulaessig(String f) {
        // Für jedes Element vom Typ String mit dem Namen fach in FAECHER...
        for (String fach : FAECHER) {
            if (fach.equals(f)) { // inhaltlicher Vergleich
                return true; // ist im Stundenplan
            }
        }
        return false; // ist nicht im Stundenplan
    }
}