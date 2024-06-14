// O. Bittel;
// 22.02.2017

package aufgabe2.graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Klasse für Bestimmung aller strengen Komponenten.
 * Kosaraju-Sharir Algorithmus.
 * @author Oliver Bittel
 * @since 22.02.2017
 * @param <V> Knotentyp.
 */
public class StrongComponents<V> {
	// comp speichert jede Komponente die zughörigen Knoten.
	private final Map<Integer,Set<V>> comp = new TreeMap<>();

	// Anzahl der Komponenten:
	private int numberOfComp = 0;

	/**
	 * Ermittelt alle strengen Komponenten mit
	 * dem Kosaraju-Sharir Algorithmus.
	 * @param g gerichteter Graph.
	 */
	public StrongComponents(DirectedGraph<V> g) {
		DepthFirstOrder<V> dfo = new DepthFirstOrder<>(g);
		List<V> p = dfo.postOrder();
		List<V> pi = p.reversed();
		DirectedGraph<V> gi = g.invert();
		Set<V> besucht = new HashSet<>();
		for (V v : pi) {
			if (!besucht.contains(v)) {
				Set<V> compSet = new TreeSet<>();
				dfs(gi, v, besucht, compSet);
				comp.put(numberOfComp, compSet);
				numberOfComp++;
			}
		}
	}

	private void dfs(DirectedGraph<V> g, V v, Set<V> besucht, Set<V> compSet) {
		besucht.add(v);
		compSet.add(v);
		for (V w : g.getSuccessorVertexSet(v)) {
			if (!besucht.contains(w)) {
				dfs(g, w, besucht, compSet);
			}
		}
	}

	/**
	 *
	 * @return Anzahl der strengen Komponeneten.
	 */
	public int numberOfComp() {
		return numberOfComp;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < numberOfComp; i++) {
			sb.append("Component ").append(i).append(": ");
			for (V v : comp.get(i)) {
				sb.append(v).append(", ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}


	public static void main(String[] args) {
		DirectedGraph<Integer> g = new AdjacencyListDirectedGraph<>();
		g.addEdge(1,2);
		g.addEdge(1,3);
		g.addEdge(2,1);
		g.addEdge(2,3);
		g.addEdge(3,1);

		g.addEdge(1,4);
		g.addEdge(5,4);

		g.addEdge(5,7);
		g.addEdge(6,5);
		g.addEdge(7,6);

		g.addEdge(7,8);
		g.addEdge(8,2);

		StrongComponents<Integer> sc = new StrongComponents<>(g);

		System.out.println(sc.numberOfComp());  // 4

		System.out.println(sc);
		// Component 0: 5, 6, 7,
		// Component 1: 8,
		// Component 2: 1, 2, 3,
		// Component 3: 4,
	}
}