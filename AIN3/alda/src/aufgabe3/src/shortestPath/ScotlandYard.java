package aufgabe3.src.shortestPath;

import aufgabe2.graph.*;

import java.io.FileNotFoundException;

import aufgabe3.src.sim.SYSimulation;

import java.awt.Color;
import java.io.IOException;

import java.io.File;
import java.util.*;


/**
 * Kürzeste Wege im Scotland-Yard Spielplan mit A* und Dijkstra.
 *
 * @author Oliver Bittel
 * @since 26.09.2022
 */
public class ScotlandYard {

	static int[][] blocked = {{89, 128}, {105, 108}, {88, 117}, {86, 116}, {104, 116}};

	/**
	 * Fabrikmethode zur Erzeugung eines gerichteten Graphens für den Scotland-Yard-Spielplan.
	 * <p>
	 * Liest die Verbindungsdaten von der Datei ScotlandYard_Kanten.txt.
	 * Für die Verbindungen werden folgende Gewichte angenommen:
	 * U-Bahn = 5, Taxi = 2 und Bus = 3.
	 * Falls Knotenverbindungen unterschiedliche Beförderungsmittel gestatten,
	 * wird das billigste Beförderungsmittel gewählt.
	 * Bei einer Vebindung von u nach v wird in den gerichteten Graph sowohl
	 * eine Kante von u nach v als auch von v nach u eingetragen.
	 *
	 * @return Gerichteter und Gewichteter Graph für Scotland-Yard.
	 * @throws FileNotFoundException
	 */
	public static DirectedGraph<Integer> getGraph() throws FileNotFoundException {

		DirectedGraph<Integer> sy_graph = new AdjacencyListDirectedGraph<>();
		Scanner in = new Scanner(new File("src/aufgabe3/data/ScotlandYard_Kanten.txt"));

		while (in.hasNextLine()) {
			String[] line = in.nextLine().split("\\s");
			if (line.length != 3) {
				break;
			}
			int v = Integer.parseInt(line[0]);
			int w = Integer.parseInt(line[1]);
			double weight = switch (line[2]) {
				case "UBahn" -> 5;
				case "Bus" -> 3;
				case "Taxi" -> 2;
				default -> 0;
			};

			// für Teilaufgabe 4, östliche Themse-Brücken blockieren
//            if (Arrays.stream(blocked).anyMatch(x -> x[0] == v && x[1] == w || x[0] == w && x[1] == v)) {
//                weight = 100;
//            } else
			if (sy_graph.containsEdge(v, w)) {
				weight = Math.min(sy_graph.getWeight(v, w), weight);
			}
			sy_graph.addEdge(v, w, weight);
			sy_graph.addEdge(w, v, weight);
		}

		// Test, ob alle Kanten eingelesen wurden:
		System.out.println("Number of Vertices:       " + sy_graph.getNumberOfVertexes());    // 199
		System.out.println("Number of directed Edges: " + sy_graph.getNumberOfEdges());        // 862
		double wSum = 0.0;
		for (Integer v : sy_graph.getVertexSet())
			for (Integer w : sy_graph.getSuccessorVertexSet(v))
				wSum += sy_graph.getWeight(v, w);
		System.out.println("Sum of all Weights:       " + wSum);    // 1972.0

		return sy_graph;
	}


	/**
	 * Fabrikmethode zur Erzeugung einer Heuristik für die Schätzung
	 * der Distanz zweier Knoten im Scotland-Yard-Spielplan.
	 * Die Heuristik wird für A* benötigt.
	 * <p>
	 * Liest die (x,y)-Koordinaten (Pixelkoordinaten) aller Knoten von der Datei
	 * ScotlandYard_Knoten.txt in eine Map ein.
	 * Die zurückgelieferte Heuristik-Funktion estimatedCost
	 * berechnet einen skalierten Euklidischen Abstand.
	 *
	 * @return Heuristik für Scotland-Yard.
	 * @throws FileNotFoundException
	 */
	public static Heuristic<Integer> getHeuristic() throws FileNotFoundException {
		return new ScotlandYardHeuristic();
	}

	/**
	 * Scotland-Yard Anwendung.
	 *
	 * @param args wird nicht verewendet.
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {

		System.out.println(System.getProperty("user.dir"));

		DirectedGraph<Integer> syGraph = getGraph();

		//Heuristic<Integer> syHeuristic = null; // Dijkstra
		Heuristic<Integer> syHeuristic = getHeuristic(); // A*

		ShortestPath<Integer> sySp = new ShortestPath<Integer>(syGraph, syHeuristic);

		sySp.searchShortestPath(65, 157);
		System.out.println("Distance = " + sySp.getDistance()); // 9.0

		sySp.searchShortestPath(1, 175);
		System.out.println("Distance = " + sySp.getDistance()); // 25.0

		sySp.searchShortestPath(1, 173);
		System.out.println("Distance = " + sySp.getDistance()); // 22.0


		SYSimulation sim;
		try {
			sim = new SYSimulation();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		sySp.setSimulator(sim);

//        sim.startSequence("Shortest path from 65 to 157");
//        sySp.searchShortestPath(65,157); // 9.0

		// sim.startSequence("Shortest path from 1 to 175");
		// sySp.searchShortestPath(1,175); //25.0

		sim.startSequence("Shortest path from 1 to 173");
		sySp.searchShortestPath(1, 173); //22.0

		// bei Heuristik-Faktor von 1/10 wird nicht der optimale Pfad produziert.
		// bei 1/30 funktioniert es.

		System.out.println("Distance = " + sySp.getDistance());
		List<Integer> sp = sySp.getShortestPath();

		int a = -1;
		for (int b : sp) {
			if (a != -1)
				sim.drive(a, b, Color.RED.darker());
			sim.visitStation(b);
			a = b;
		}

		sim.stopSequence();


	}

}

class ScotlandYardHeuristic implements Heuristic<Integer> {
	private Map<Integer, Point> coord; // Ordnet jedem Knoten seine Koordinaten zu

	private static class Point {
		int x;
		int y;

		Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	public ScotlandYardHeuristic() throws FileNotFoundException {
		Scanner in = new Scanner(new File("src/aufgabe3/data/ScotlandYard_Knoten.txt"));
		coord = new HashMap<>();
		while (in.hasNext()) {
			int nr = in.nextInt();
			int x = in.nextInt();
			int y = in.nextInt();
			coord.put(nr, new Point(x, y));
		}
	}

	public double estimatedCost(Integer u, Integer v) {
		Point a = coord.get(u);
		Point b = coord.get(v);
		if (a != null && b != null) {
			return Math.sqrt((a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y)) / 50;
		}
		return 0.0;
	}
}