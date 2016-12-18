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


    }

    public void traverse() {
        traverse(tree.getRootNode(), 0);
    }

    public abstract boolean onVisitEdge(Edge edge, int depth);

    // depth first search
    private void traverse(Long nodeId, int depth) {

        while (true) {
            Edge nextEdge = findNextEdge(nodeId);
            if (nextEdge == null) {
                break;
            }
            if (onVisitEdge(nextEdge, depth)) {
                traverse(nextEdge.getToId(), depth + 1);
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
