import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Aufgabe3_Textanalyse {
    public static void loesung() throws IOException {
        //
        // a) (5P)
        // Die Datei Kafka_Der_Prozess.txt soll eingelesen werden und verschiedene Auswertungen erfolgen.
        // Ergänzen Sie die Funktion einlesen so, dass die eingelesenen Wörter als Liste zurückgeliefert werden.
        System.out.println("\nAufgabe 3a (4P):");
        long start = System.nanoTime(); // aktuelle Zeit in nsec
        List<String> lst_Kafka = einlesen("data/Kafka_Der_Prozess.txt");
        System.out.println("Benötigte Zeit in msec: " + (double)(System.nanoTime()-start)/1.0e06);

        // Geben Sie die Anzahl der eingelesenen Wörter aus. Benutzen Sie dazu Ihre eingelesene Liste.
        // Ihr Code: ...
        int size = lst_Kafka.size();
        System.out.println(size);


        // Sortieren Sie die Liste und geben Sie die ersten 100 Wörter aus.
        start = System.nanoTime();
        // Ihr Code: ...
        System.out.println("Benötigte Zeit in msec: " + (double)(System.nanoTime()-start)/1.0e06);
        Collections.sort(lst_Kafka);
        for (int i = 0; i < 100; ++i) {
            System.out.print(lst_Kafka.get(i) + " ");
        }

        // Speichern Sie die Liste in eine TreeSet und geben Sie ersten die 100  Wörter aus.
        // Berücksichtigen Sie die Konstruktoren der Klasse TreeSet!
        // Ihr Code: ...
        Set<String> set_kafka = new TreeSet<>(lst_Kafka);
        int count = 100;
        for (String s : set_kafka){
            System.out.print(s + " ");
            count--;
            if (count <= 0) {
                break;
            }
        }

        //
        // b) (5P)
        // Verwenden Sie die bereits eingelesene Liste lst_Kafka und erstellen Sie eine Häufigkeitstabelle als SortedMap.
        // Wieviel unterschiedliche Wörter gibt es?
        // Geben Sie mit Hilfe von subMap alle Wörter (mit ihren Häufigkeiten) aus, die mit "Ver" beginnen.
        // Geben Sie die 20 häufigsten Wörter (mit ihren Häufigkeiten) aus.
        System.out.println("\nAufgabe 3b (4P):");
        start = System.nanoTime();
        SortedMap<String, Integer> fqTable_Kafka = ermittleHaeufigkeiten(lst_Kafka);
        System.out.println("Benötigte Zeit in msec: " + (double)(System.nanoTime()-start)/1.0e06);
        // Ihr Code: ...
        for (Map.Entry<String, Integer> eintrag
                : fqTable_Kafka.subMap("Ver", "Ves").entrySet()) {
            System.out.println(eintrag.getKey() + "," + eintrag.getValue());
        }
        TwentyMostFrequent(fqTable_Kafka);

        //
        // c) (5P)
        // Ein Wort gilt als falsch geschrieben, wenn es nicht in der Wörterbuchdatei word_list_german_spell_checked.txt vorkommt.
        // Das Einlesen der Datei word_list_german_spell_checked.txt dauert etwas Zeit, da sie mehr als 2 Millionen Einträge enthält.
        // Ermitteln Sie für den Kafka-Text eine Häufigkeitstabelle der falsch geschriebenen Wörter.
        // Wieviel falsch geschriebene Wörter gibt es?
        // Geben Sie die 20 häufigsten falsch geschriebenen Wörter (mit ihren Häufigkeiten) aus.
        System.out.println("\nAufgabe 3c (3P):");

        start = System.nanoTime();
        // word_list_german_spell_checked.txt einlesen:
        // Ihr Code: ..
        List<String> lst_Deutsch = einlesen("/home/fabian/htwg/Prog2/src/Workshop1_2023_Collections/data/word_list_german_spell_checked.txt");

        Set<String> ww = new TreeSet<>(lst_Deutsch);


        System.out.println("Benötigte Zeit in msec: " + (double)(System.nanoTime()-start)/1.0e06);
        // Ihr Code:
        // ...
        Map<String, Integer> fqTable_falsch = new HashMap<>();
        for (String s : lst_Kafka) {
            if (!ww.contains(s)){
                if (!fqTable_falsch.containsKey(s)){
                    fqTable_falsch.put(s, 1);
                } else {
                    fqTable_falsch.put(s, fqTable_falsch.get(s) + 1);
                }
            }
        }
        TwentyMostFrequent(fqTable_falsch);

        //
        // d) (5P)
        // Ermitteln Sie auch für Harry_Potter_und_der_Stein_der_Weisen.txt eine Häufigkeitstabelle.
        // Ermitteln Sie eine Häufigkeitstabelle der Wörter, die sowohl in Kafka_Der_Prozess.txt als auch
        // in Harry_Potter_und_der_Stein_der_Weisen.txt vorkommen, indem Sie die Häufigkeiten der gemeinsamen Wörter addieren.
        // Wieviel unterschiedliche Wörter gibt es in jedem Buch?
        // Wieviel unterschiedliche gemeinsame Wörter gibt es?
        // Geben Sie die 20 häufigsten gemeinsamen Wörter (mit ihren Häufigkeiten) aus.
        System.out.println("\nAufgabe 3d (3P):");
        // Ihr Code:
        List<String> lst_Harry = einlesen("data/Harry_Potter_und_der_Stein_der_Weisen.txt");
        SortedMap<String, Integer> fqTable_Harry = ermittleHaeufigkeiten(lst_Harry);
        Map<String, Integer> fqTable_Kafka_Harry = new HashMap<>(fqTable_Harry);
        for (Map.Entry<String, Integer> eintrag : fqTable_Kafka.entrySet()) {
            if (fqTable_Kafka_Harry.containsKey(eintrag.getKey())){
                fqTable_Kafka_Harry.put(eintrag.getKey(), eintrag.getValue() + fqTable_Kafka_Harry.get(eintrag.getKey()));
            } else {
                fqTable_Kafka_Harry.put(eintrag.getKey(), eintrag.getValue());
            }
        }
        TwentyMostFrequent(fqTable_Kafka_Harry);
    }

    private static void TwentyMostFrequent(Map<String, Integer> fqTable) {
        List<Map.Entry<String, Integer>> liste3 = new ArrayList<>(fqTable.entrySet());
        liste3.sort(Map.Entry.comparingByValue());
        liste3 = liste3.reversed();
        int count = 20;
        for (Map.Entry<String, Integer> eintrag : liste3) {
            System.out.println(eintrag.getKey() + ", " + eintrag.getValue());
            count--;
            if (count <= 0) {
                break;
            }
        }
    }

    private static List<String> einlesen(String fileName) throws IOException {
        LineNumberReader in = new LineNumberReader(new FileReader(fileName, StandardCharsets.UTF_8));
        List<String> list = new ArrayList<>();
        String line;

        while ((line = in.readLine()) != null) {
            String[] wf = line.split("[^a-z^A-Z^ß^ä^ö^ü^Ä^Ö^Ü]+");
            for (String w: wf) {
                if (w.isEmpty() || w.length() == 1)
                    continue;
                list.add(w);
            }
        }
        return list;
    }

    private static SortedMap<String, Integer> ermittleHaeufigkeiten(List<String> wListe)  {
        // Ihr Code:
        SortedMap<String, Integer> map = new TreeMap<>();

        for (String s : wListe){
            if (!map.containsKey(s)){
                map.put(s, 1);
            } else {
                map.put(s, map.get(s) + 1);
            }
        }
        return map;
    }
}

