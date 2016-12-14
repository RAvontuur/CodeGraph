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

    /**
     * visits alle edges in depth first order
     * cross edges will be skipped
     *
     * @param tree
     */
    public DepthFirstTraversal(Tree tree) {
        this.tree = tree;
        analyzedEdges.clear();

        traverse(tree.getRootNode());
    }

    public abstract boolean onVisitEdge(Edge edge);

    // depth first search
    private void traverse(Long nodeId) {

        while (true) {
            Edge nextEdge = findNextEdge(nodeId);
            if (nextEdge == null) {
                break;
            }
            if (onVisitEdge(nextEdge)) {
                traverse(nextEdge.getToId());
                analyzedEdges.add(nextEdge);
            } else {
                break;
            }
        }
    }


    private Edge findNextEdge(long nodeId) {
        return tree.getEdges().stream()
                .filter(edge1 -> edge1.getFromId() == nodeId)
                .filter(edge2 -> !analyzedEdges.contains(edge2))
                .findFirst().orElse(null);
    }

}
