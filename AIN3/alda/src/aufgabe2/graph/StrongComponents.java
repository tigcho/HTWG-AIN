// O. Bittel;
// 22.02.2017

package aufgabe2.graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class StrongComponents<V> {
	private final Map<Integer,Set<V>> comp = new TreeMap<>();
	private int count;

	public StrongComponents(DirectedGraph<V> g) {
		DepthFirstOrder<V> p = new DepthFirstOrder<>(g);
		List<V> pi = new LinkedList<>(p.postOrder());
		Collections.reverse(pi);

		DirectedGraph<V> gi = g.invert();

		Set<V> besucht = new HashSet<>();
		for (var v : pi) {
			if (!besucht.contains(v)) {
				comp.put(count, new TreeSet<>());
				comp.get(count).add(v);
				visitDF(v, gi, besucht);
				count++;
			}
		}
	}

	private void visitDF(V v, DirectedGraph<V> g, Set<V> besucht) {
		besucht.add(v);
		for(var w : g.getSuccessorVertexSet(v)) {
			if (!besucht.contains(w)) {
				comp.get(count).add(w);
				visitDF(w, g, besucht);
			}
		}
	}

	public int numberOfComp() {
		return comp.size();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (var key : comp.entrySet()) {
			sb.append("Component " + key.getKey() + ": ");
			for (var val : key.getValue()) {
				sb.append(val + ", ");
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