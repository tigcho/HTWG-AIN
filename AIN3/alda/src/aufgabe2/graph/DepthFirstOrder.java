package aufgabe2.graph;

import java.util.*;

public class DepthFirstOrder<V> {

    private final List<V> preOrder = new LinkedList<>();
    private final List<V> postOrder = new LinkedList<>();
    private final DirectedGraph<V> myGraph;
    private int numberOfDFTrees = 0;

    public DepthFirstOrder(DirectedGraph<V> g) {
        myGraph = g;
        Set<V> allVertices = myGraph.getVertexSet();
        Set<V> besucht = new HashSet<>();
        for (V v : allVertices) {
            if (!preOrder.contains(v)) {
                numberOfDFTrees++;
                dfs(v, besucht);
            }
        }
    }

    private void dfs(V v, Set<V> besucht) {
        preOrder.add(v);
        besucht.add(v);
        for (V w : myGraph.getSuccessorVertexSet(v)) {
            if (!besucht.contains(w)) {
                dfs(w, besucht);
            }
        }
        postOrder.add(v);
    }

    public List<V> preOrder() {
        return Collections.unmodifiableList(preOrder);
    }

    public List<V> postOrder() {
        return Collections.unmodifiableList(postOrder);
    }

    public int numberOfDFTrees() {
        return numberOfDFTrees;
    }

    public static void main(String[] args) {
        DirectedGraph<Integer> g = new AdjacencyListDirectedGraph<>();
        g.addEdge(1, 2);
        g.addEdge(2, 5);
        g.addEdge(5, 1);
        g.addEdge(2, 6);
        g.addEdge(3, 7);
        g.addEdge(4, 3);
        g.addEdge(4, 6);
        //g.addEdge(7,3);
        g.addEdge(7, 4);

        DepthFirstOrder<Integer> dfs = new DepthFirstOrder<>(g);
        System.out.println(dfs.numberOfDFTrees());	// 2
        System.out.println(dfs.preOrder());		// [1, 2, 5, 6, 3, 7, 4]
        System.out.println(dfs.postOrder());		// [5, 6, 2, 1, 4, 7, 3]
    }
}