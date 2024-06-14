// O. Bittel;
// 22.02.2017

package aufgabe2.graph;

import java.util.*;

/**
 * Klasse zur Erstellung einer topologischen Sortierung.
 * @author Oliver Bittel
 * @since 22.02.2017
 * @param <V> Knotentyp.
 */
public class TopologicalSort<V> {
	private List<V> ts = new LinkedList<>(); // topologisch sortierte Folge
	// ...

	/**
	 * Führt eine topologische Sortierung für g durch.
	 * @param g gerichteter Graph.
	 */
	public TopologicalSort(DirectedGraph<V> g) {
		List<V> l = new LinkedList<>();
		Queue<V> q = new LinkedList<>();
		Map<V, Integer> inDegree = new HashMap<>();
		for (V v : g.getVertexSet()) {
			inDegree.put(v, g.getInDegree(v));
			if (g.getInDegree(v) == 0) {
				q.add(v);
			}
		}
		while (!q.isEmpty()) {
			V v = q.poll();
			l.add(v);
			for (V w : g.getSuccessorVertexSet(v)) {
				inDegree.put(w, inDegree.get(w) - 1);
				if (inDegree.get(w) == 0) {
					q.add(w);
				}
			}
		}
		if (l.size() == g.getNumberOfVertexes()) {
			ts = l;
		}
	}

	/**
	 * Liefert eine nicht modifizierbare Liste (unmodifiable view) zurück,
	 * die topologisch sortiert ist.
	 * @return topologisch sortierte Liste
	 */
	public List<V> topologicalSortedList() {
		return Collections.unmodifiableList(ts);
	}


	public static void main(String[] args) {
		DirectedGraph<Integer> g = new AdjacencyListDirectedGraph<>();
		g.addEdge(1, 2);
		g.addEdge(2, 3);
		g.addEdge(3, 4);
		g.addEdge(3, 5);
		g.addEdge(4, 6);
		g.addEdge(5, 6);
		g.addEdge(6, 7);
		System.out.println(g);

		TopologicalSort<Integer> ts = new TopologicalSort<>(g);

		if (ts.topologicalSortedList() != null) {
			System.out.println(ts.topologicalSortedList()); // [1, 2, 3, 4, 5, 6, 7]
		}
	}
}