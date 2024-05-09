package aufgabe2.graph;

import java.util.*;

public class TopologicalSort<V> {
	private final List<V> ts = new LinkedList<>();

	public TopologicalSort(DirectedGraph<V> graph) {
		List<V> result = new LinkedList<>();
		Map<V, Integer> inDegree = new TreeMap<>();
		Queue<V> q = new ArrayDeque<>();

		for (V v : graph.getVertexSet()) {
			inDegree.put(v, graph.getInDegree(v));
			if (inDegree.get(v) == 0) {
				q.add(v);
			}
		}

		while (!q.isEmpty()) {
			V v = q.remove();
			result.add(v);
			for (V w : graph.getSuccessorVertexSet(v)) {
				inDegree.put(w, inDegree.get(w) - 1);
				if (inDegree.get(w) == 0) {
					q.add(w);
				}
			}
		}

		if (result.size() == graph.getNumberOfVertexes()) {
			ts.addAll(result);
		}
	}

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
