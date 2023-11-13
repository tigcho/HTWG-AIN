// Notenspiegel.java
package aufgabe5;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Notenspiegel liest die Namen von F&auml;chern mit den zugeh&ouml;rigen Noten
 * in eine verkettete Liste ein und gibt dann einen Notenspiegel aus.
 * @author Tigcho
 * @version 24.05.23
 */
public final class Notenspiegel {
    private Notenspiegel() {
    }

    private static final Scanner EINGABE = new Scanner(System.in);

    /**
     * main ist der Startpunkt des Programms.
     *
     * @param args wird nicht verwendet.
     */
    public static void main(String[] args) {
        FachnotenListe liste = new FachnotenListe(); // leere Liste

        //--------------------------------------------- Notenspiegel einlesen

        System.err.printf(
                "Faecher mit Noten zwischen %d und %d eingeben "
                + "(Ende mit Strg-D):%n",
                Note.BESTE.intValue(), Note.SCHLECHTESTE.intValue());

        while (EINGABE.hasNext()) {
            try {

                //------------------------------------ Fach und Note einlesen

                /*
                Wert für fas Fach wird in notenWert gespeichert und eingelesen
                 */
                String notenWert = EINGABE.next();

                /*
                hat die Eingabe ein Integer/Ganzzahl? Dann wird er eingelesen
                 und in der Variable note vom Typ Note gespeichert. valueOf
                 nimmt einen Parameter, der den Wert der Note repräsentiert,
                 und erstellt daraus eine neue Instanz in der Klasse.
                 */
                if (EINGABE.hasNextInt()) {
                    Note note = Note.valueOf(EINGABE.nextInt());
                    // Fachnote wird FachnotenListe eingefügt
                    liste.insert(new Fachnote(notenWert, note));
                } else {
                    // das gleiche wie oben, nur als String
                    Note note = Note.valueOf(EINGABE.next());
                    liste.insert(new Fachnote(notenWert, note));
                }


                //--------------------- neue Fachnote in Notenliste eintragen

            } catch (IllegalArgumentException x) {
                System.err.printf("Eingabefehler: %s%n", x.getMessage());
            } catch (NoSuchElementException x) {
                System.err.println("Fach ohne Note ignoriert!");
                break;
            }
        }

        //--------------------------------------------- Notenspiegel ausgeben

        System.out.println("NOTENSPIEGEL");
        final int ten = 10;

        // erstellt Liste und wird variable Iterator zugewiesen
        FachnotenListe.Iterator iterator = liste.new Iterator();

        // solange es Elemente in der Liste gibt, wird die while-Schleife laufen
        while (iterator.hasNext()) {

            // jedes naechste Element in der Liste wird next zugewiesen
            Fachnote next = iterator.next();

            // Variable speichert den Output als String
            String noteOutput;

            // aktuelles Fach = note zehn??
            if (next.note.intValue() == ten) {
                noteOutput = "mit Bestnote bestanden";

                // ansonsten wird der Wert auf bestanden gesetzt
            } else if (next.note.istBestanden()) {
                noteOutput = "bestanden";

                // weder zehn, noch bestanden
            } else {
                noteOutput = "nicht bestanden";
            }

            System.out.printf("%-25s%-5s%s%n", next.fach, next.note,
                    noteOutput);
        }
    }
} // main
