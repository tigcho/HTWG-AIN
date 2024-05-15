package aufgabe2.graph;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.Set;

public class AdjacencyListDirectedGraph<V> implements DirectedGraph<V> {
    // doppelte Map für Nachfolgerknoten und Gewicht der Kanten:
    private final Map<V, Map<V, Double>> succ = new TreeMap<>(); 
    
    // doppelte Map für die Vorgängerknoten und Gewicht der Kanten:
    private final Map<V, Map<V, Double>> pred = new TreeMap<>(); 

    private int numberEdge = 0;

	@Override
	public boolean addVertex(V v) {
	    if (succ.containsKey(v)) {
	        return false;
	    }
        // v is a new vertex and has no succs and preds yet
        // so we add an empty map for the succs and preds of v
	    succ.put(v, new TreeMap<>());
	    pred.put(v, new TreeMap<>());
	    return true;
    }

    @Override
    public boolean addEdge(V v, V w, double weight) {
        addVertex(v);
        addVertex(w);
        succ.get(v).put(w, weight); // w is after v in succ
        pred.get(w).put(v, weight); // v is before w in pred
        numberEdge++;
        return true;
    }

    // overloaded version because weight can either be 1.0 or a given value
    @Override
    public boolean addEdge(V v, V w) {
        return addEdge(v, w, 1.0);
    }

    @Override
    public boolean containsVertex(V v) {
        return succ.containsKey(v);
    }

    @Override
    public boolean containsEdge(V v, V w) {
        return succ.containsKey(v) && succ.get(v).containsKey(w);
    }

    @Override
    public double getWeight(V v, V w) {
        return succ.get(v).get(w);
    }

	
    @Override
    public int getInDegree(V v) {
        return pred.get(v).size();
    }

    @Override
    public int getOutDegree(V v) {
        return succ.get(v).size();
    }
	
	@Override
    public Set<V> getVertexSet() {
        return Collections.unmodifiableSet(succ.keySet());
    }

    @Override
    public Set<V> getPredecessorVertexSet(V v) {
        return Collections.unmodifiableSet(pred.get(v).keySet());
    }

    @Override
    public Set<V> getSuccessorVertexSet(V v) {
        return Collections.unmodifiableSet(succ.get(v).keySet());
    }

    @Override
    public int getNumberOfVertexes() {
        return succ.size();
    }

    @Override
    public int getNumberOfEdges() {
        return numberEdge;
    }

    // invert the graph by creating a new one and adding the edges in reverse order
    /* before:
    * 1 --> 2 weight = 1.0
    * 2 --> 5 weight = 1.0
    * 2 --> 6 weight = 1.0
    * 3 --> 7 weight = 1.0
    * after:
    * 2 --> 1 weight = 1.0
    * 5 --> 2 weight = 1.0
    * 6 --> 2 weight = 1.0
    * 7 --> 3 weight = 1.0 */
    @Override
    public DirectedGraph<V> invert() {
        DirectedGraph<V> g = new AdjacencyListDirectedGraph<>();
        for (V v : getVertexSet()) {
	        for (V w : getSuccessorVertexSet(v)) {
	        g.addEdge(w, v, getWeight(v, w));
	        }
        }
        return g;
    }

	
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (V v : getVertexSet()) {
            for (V w : getSuccessorVertexSet(v)) {
	        sb.append(v).append(" --> ").append(w).append(" weight = ").append(getWeight(v, w)).append("\n");
            }
        }
        return sb.toString();
    }

	
    public static void main(String[] args) {
        DirectedGraph<Integer> g = new AdjacencyListDirectedGraph<>();
        g.addEdge(1,2);
        g.addEdge(2,5);
        g.addEdge(5,1);
        g.addEdge(2,6);
        g.addEdge(3,7);
        g.addEdge(4,3);
        g.addEdge(4,6);
        g.addEdge(7,4);
      
        System.out.println(g.getNumberOfVertexes());	// 7
        System.out.println(g.getNumberOfEdges());		// 8
        System.out.println(g.getVertexSet());	// 1, 2, ..., 7
        System.out.println(g);
	    // 1 --> 2 weight = 1.0 
	    // 2 --> 5 weight = 1.0
	    // 2 --> 6 weight = 1.0
	    // 3 --> 7 weight = 1.0
	    // ...
    
        System.out.println("");
        System.out.println(g.getOutDegree(2));				// 2
        System.out.println(g.getSuccessorVertexSet(2));	// 5, 6
        System.out.println(g.getInDegree(6));				// 2
        System.out.println(g.getPredecessorVertexSet(6));	// 2, 4
    
        System.out.println("");
        System.out.println(g.containsEdge(1,2));	// true
        System.out.println(g.containsEdge(2,1));	// false
        System.out.println(g.getWeight(1,2));	// 1.0	
        g.addEdge(1, 2, 5.0);
        System.out.println(g.getWeight(1,2));	// 5.0	
    
        System.out.println("");
        System.out.println(g.invert());
	    // 1 --> 5 weight = 1.0
	    // 2 --> 1 weight = 5.0
	    // 3 --> 4 weight = 1.0 
	    // 4 --> 7 weight = 1.0
	    // ...
	    
        Set<Integer> s = g.getSuccessorVertexSet(2);
        System.out.println(s);
        // s.remove(5);	// Laufzeitfehler! Warum? Weil getSuccessorVertexSet eine unveränderbare Menge zurückgibt und remove nicht unterstützt.
    }
}
