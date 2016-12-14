package nl.rav.codegraph.algorithm.spanningtree;

import java.util.ArrayList;
import java.util.List;

/**
 * The concept:
 * https://en.wikipedia.org/wiki/Depth-first_search#Output_of_a_depth-first_search
 */
public abstract class DepthFirstTraversal {

    private Tree tree;
    private final List<Edge> analyzedEdges = new ArrayList<>();

    public DepthFirstTraversal(Tree tree) {
        this.tree = tree;
        analyzedEdges.clear();
        List<Edge> pathEdges = new ArrayList<>();

        if (onVisitEdge(tree.getRootEdge(), null, pathEdges)) {
            traverse(tree.getRootEdge(), pathEdges);
            analyzedEdges.add(tree.getRootEdge());
        }
    }

    public abstract boolean onVisitEdge(Edge edge, Edge parentEdge, List<Edge> pathEdges);

    // depth first search
    private void traverse(Edge edge, List<Edge> pathEdges) {
        pathEdges.add(edge);

        while (true) {
            Edge nextEdge = findNextEdge(edge);
            if (nextEdge == null) {
                break;
            }
            if (onVisitEdge(nextEdge, edge, pathEdges)) {
                traverse(nextEdge, pathEdges);
                analyzedEdges.add(nextEdge);
            } else {
                break;
            }
        }
        pathEdges.remove(pathEdges.size() - 1);
    }


    private Edge findNextEdge(Edge edge) {
        return tree.getEdges().stream()
                .filter(edge1 -> edge1.getFromId() == edge.getToId())
                .filter(edge2 -> !analyzedEdges.contains(edge2))
                .findFirst().orElse(null);
    }

}
