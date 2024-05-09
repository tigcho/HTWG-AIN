package aufgabe2.graph;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.*;

public class AnalyzeWebSite {
    public static void main(String[] args) throws IOException {
        // Graph aus Website erstellen und ausgeben:
        DirectedGraph<String> webSiteGraph = buildGraphFromWebSite("/home/selin/HTWG-AIN/AIN3/alda/src/aufgabe2/data/WebSiteKlein");
        //DirectedGraph<String> webSiteGraph = buildGraphFromWebSite("/home/selin/HTWG-AIN/AIN3/alda/src/aufgabe2/data/WebSiteGross");
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

    private static <V> void pageRank(DirectedGraph<V> g) {
        int nI = 10;
        double alpha = 0.5;

        // Definiere und initialisiere rankTable:
        Map<V, Double> rankTable = new HashMap<>();
        for (V v : g.getVertexSet()) {
            rankTable.put(v, 1.0 / g.getNumberOfVertexes());
        }

        // Iteration:
        for (int i = 0; i < nI; i++) {
            Map<V, Double> newRankTable = new HashMap<>();
            for (V v : g.getVertexSet()) {
                double rank = 0;
                for (V w : g.getSuccessorVertexSet(v)) {
                    rank += rankTable.get(w) / g.getPredecessorVertexSet(w).size();
                }
                newRankTable.put(v, alpha * rank + (1 - alpha) / g.getNumberOfVertexes());
            }
            rankTable = newRankTable;
        }

        // Rank Table ausgeben (nur für data/WebSiteKlein):
        if (g.getNumberOfVertexes() <= 100) {
            for (V v : g.getVertexSet()) {
                System.out.println(v + ": " + rankTable.get(v));
            }
        }

        // Nach Ranks sortieren Top 100 ausgeben (nur für data/WebSiteGross):
        if (g.getNumberOfVertexes() > 100) {
            List<Map.Entry<V, Double>> list = new ArrayList<>(rankTable.entrySet());
            list.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));
            for (int i = 0; i < 100; i++) {
                System.out.println(list.get(i).getKey() + ": " + list.get(i).getValue());
            }
        }
        
        // Top-Seite mit ihren Vorgängern und Ranks ausgeben (nur für data/WebSiteGross):
        if (g.getNumberOfVertexes() > 100) {
            V top = Collections.max(rankTable.entrySet(), Map.Entry.comparingByValue()).getKey();
            System.out.println("Top-Seite: " + top);
            System.out.println("Vorgänger: " + g.getPredecessorVertexSet(top));
            System.out.println("Rank: " + rankTable.get(top));
        }
    }
}
