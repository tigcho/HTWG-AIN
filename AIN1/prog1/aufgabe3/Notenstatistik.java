// Notenstatistik.java
        package aufgabe3;

import java.util.Locale;
import java.util.Scanner;

/**
 * erstellt eine Notenstatistik. * <p>
 * Das Programm erwartet Prüfungsnoten im Format
 * <code>Ganze,Zehntel</code> oder <code>Ganze.Zehntel</code>,
 * wobei <code>Ganze</code> und <code>Zehntel</code> nur aus
 * je einer Dezimalziffer bestehen dürfen. * Andere Eingaben werden wegen Formatfehler ignoriert.
 * </p>
 * <p>
 * Das Programm gibt die folgende Statistik aus:
 * </p>
 * <ul>
 * <li>die Anzahl der berücksichtigten Noten</li>
 * <li>die Anzahl der Bestandenen</li>
 * <li>die best Note</li>
 * <li>die worst Note</li>
 * <li>den Durchschnitt der Bestandenen</li>
 * <li>die Durchfallquote in Prozent</li>
 * </ul>
 * <p>
 * Es werden in der Statistik nur die nach HTWG-Prüfungsordnung
 * zulässigen Noten (1,0 1,3 1,7 2,0 2,3 2,7 3,0 3,3 3,7 4,0 5,0) * berücksichtigt.
 * Andere Eingaben werden wegen falscher Vorkommastelle oder * falscher Nachkommastelle ignoriert.
 * Alle Noten bis 4,0 gelten als passed, nur die 5,0 als durchgefallen.
 * </p>
 * @author Selin K
 * @version 01.05.23
 */
public final class Notenstatistik {
    private Notenstatistik() { }

    private static final Scanner EINGABE = new Scanner(System.in);

    /**
     * main ist der Startpunkt des Programms.
     * * @param args wird nicht verwendet.
     */
    public static void main(String[] args) {
        Locale.setDefault(Locale.GERMANY);

        final int maxChars = 3;
        int passed = 0;
        int failed = 0;
        double average = 0.0;
        double noteSum = 0;
        double best = 5.0;
        double worst = 1.0;

        //--------------------------------------------------- Noten einlesen
        System.out.println("Noten im Format Ganze,Zehntel "
                + "oder Ganze.Zehntel eingeben (Ende mit Strg-D):");

        while (EINGABE.hasNext()) {
            String note = EINGABE.next();

            //---------------------------------------------- Eingabe pruefen
            if (note.length() != maxChars) {
                System.out.println("Note " + note + " wird wegen "
                        + "Formatfehler ignoriert!");
                continue;            }

            if (!Character.isDigit(note.charAt(0))
                    || (note.charAt(1) != '.' && note.charAt(1) != ',')
                    || !Character.isDigit(note.charAt(2))) {
                System.out.println("Note " + note
                        + " wird wegen Formatfehler ignoriert!");
                continue;            }

            switch (note.charAt(0)) {
                case '1', '2', '3':
                    if (note.charAt(2) != '0' && note.charAt(2) != '3'
                            && note.charAt(2) != '7') {
                        System.out.println("Note "
                                + note + " wird wegen " + "Nachkommastelle "
                                + note.charAt(2) + " ignoriert!");
                        continue;                }
                    break;
                case '4', '5':
                    if (note.charAt(2) != '0') {
                        System.out.println("Note " + note + " wird wegen "
                                + "Nachkommastelle "
                                + note.charAt(2) + " ignoriert!");
                        continue;                }
                    break;
                default:
                    System.out.println("Note " + note + " wird wegen "
                            + "Vorkommastelle "
                            + note.charAt(0) + " ignoriert!");
                    continue;            }

            //------------------------------------------------ Note erfassen


            double noteValue = Double.parseDouble(note.replace(',', '.'));

            if (noteValue != 0.0 && noteValue < 5.0) {
                passed++;
                noteSum += noteValue;

                // Aktualisiere average

                average += noteValue;

            } else if (noteValue != 0.0) {
                failed++;
            }

            // Aktualisiere best und worst Note
            if (noteValue != 0.0 && noteValue < best) {
                best = noteValue;
            }
            if (noteValue != 0.0 && noteValue > worst) {
                worst = noteValue;
            }
        }

        final int checked = passed + failed;
        final double failureRate = ((double) failed / (double) checked) * 100.0;

        average =
                average / (double) passed;

        // while

        //------------------------------------------ Notenstatistik ausgeben
        System.out.println("Anzahl Bestandene: " + passed);

        if (passed > 0) {
            System.out.println("Beste Note: "
                    + String.format("%.1f", best));
            System.out.println("Schlechteste Note: "
                    + String.format("%.1f", worst));
            System.out.println("Durchschnitt Bestandene: "
                    + String.format("%.1f", average));
            System.out.println("Durchfallquote: "
                    + String.format("%.1f%%", failureRate));
        }
    }
} // main