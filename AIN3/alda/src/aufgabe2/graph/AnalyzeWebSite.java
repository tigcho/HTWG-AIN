// O. Bittel;
// 2.8.2023

package aufgabe2.graph;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.*;


/**
 * Klasse zur Analyse von Web-Sites.
 *
 * @author Oliver Bittel
 * @since 30.10.2023
 */
public class AnalyzeWebSite {
    public static void main(String[] args) throws IOException {
        // Graph aus Website erstellen und ausgeben:
//        DirectedGraph<String> webSiteGraph = buildGraphFromWebSite("/home/fabian/htwg/AlgoDat/Aufgaben/src/aufgabe2/data/WebSiteKlein");
        DirectedGraph<String> webSiteGraph = buildGraphFromWebSite("/home/fabian/htwg/AlgoDat/Aufgaben/src/aufgabe2/data/WebSiteGross");
        System.out.println("Anzahl Seiten: \t" + webSiteGraph.getNumberOfVertexes());
        System.out.println("Anzahl Links: \t" + webSiteGraph.getNumberOfEdges());
        //System.out.println(webSiteGraph);

        // Starke Zusammenhangskomponenten berechnen und ausgeben
        StrongComponents<String> sc = new StrongComponents<>(webSiteGraph);
        System.out.println(sc.numberOfComp());
        //System.out.println(sc);

        // Page Rank ermitteln und Top-100 ausgeben
        pageRank(webSiteGraph);
    }

    /**
     * Liest aus dem Verzeichnis dirName alle Web-Seiten und
     * baut aus den Links einen gerichteten Graphen.
     *
     * @param dirName Name eines Verzeichnises
     * @return gerichteter Graph mit Namen der Web-Seiten als Knoten und Links als gerichtete Kanten.
     */
    private static DirectedGraph buildGraphFromWebSite(String dirName) throws IOException {
        File webSite = new File(dirName);
        DirectedGraph<String> webSiteGraph = new AdjacencyListDirectedGraph();

        for (File f : webSite.listFiles()) {
            String from = f.getName();
            LineNumberReader in = new LineNumberReader(new FileReader(f));
            String line;
            while ((line = in.readLine()) != null) {
                if (line.contains("href")) {
                    String[] s_arr = line.split("\"");
                    String to = s_arr[1];
                    webSiteGraph.addEdge(from, to);
                }
            }
        }
        return webSiteGraph;
    }

    /**
     * pageRank ermittelt Gewichte (Ranks) von Web-Seiten
     * aufgrund ihrer Link-Struktur und gibt sie aus.
     *
     * @param g gerichteter Graph mit Web-Seiten als Knoten und Links als Kanten.
     */
    private static <V> void pageRank(DirectedGraph<V> g) {
        int nI = 10;
        double alpha = 0.5;

        // Definiere und initialisiere rankTable:
        Map<V, Double> rankTable = new HashMap<>();
        for (V v : g.getVertexSet()) {
            rankTable.put(v, 1.0);
        }

        // Iteration:
        for (int i = 0; i < nI; i++) {
            Map<V, Double> newRankTable = new HashMap<>();
            for (V v : g.getVertexSet()) {
                double sum = 0;
                for (V w : g.getPredecessorVertexSet(v)) {
                    sum += rankTable.get(w) / g.getSuccessorVertexSet(w).size();
                }
                double rank = (1 - alpha) + alpha * sum;
                newRankTable.put(v, rank);
            }
            rankTable = newRankTable;
        }

        // Rank Table ausgeben (nur für data/WebSiteKlein):
        if (g.getNumberOfVertexes() < 100) {
            System.out.println("\nRanktabelle:");
            for (V v : rankTable.keySet()) {
                System.out.println("Page: "+ v + ",\t Rank: " + rankTable.get(v));
            }
        }

        // Nach Ranks sortieren Top 100 ausgeben (nur für data/WebSiteGross):
        else {
            System.out.println("\nTop 100:");
            TreeMap<Double, V> sortedRankTable = new TreeMap<>();
            for (V v : rankTable.keySet()) {
                sortedRankTable.put(rankTable.get(v), v);
            }
            int count = 0;
            for (V v : sortedRankTable.descendingMap().values()) {
                if (count < 100) {
                    System.out.println("Page: " + v + ",\t Rank: " + rankTable.get(v));
                    count++;
                } else {
                    break;
                }
            }

            // und top seite ausgeben
            System.out.println("\nTop-Seite:");
            V v = sortedRankTable.descendingMap().values().iterator().next();
            System.out.println("Page: " + v + ",\t Rank: " + rankTable.get(v));
            System.out.println("\nVorgänger:");
            for (V w : g.getPredecessorVertexSet(v)) {
                System.out.println("Page: " + w + ",\t Rank: " + rankTable.get(w));
            }
        }

    }
}