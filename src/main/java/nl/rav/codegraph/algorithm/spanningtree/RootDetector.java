package nl.rav.codegraph.algorithm.spanningtree;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *  Find all nodes without a parent
 *
 */
public class RootDetector extends BaseDetector {

    public RootDetector(List<Edge> edges) {
        super(edges);
    }

    public List<Edge> findRootEdges() {
        List<Edge> result = new ArrayList<>();

        clearAnalyzedEdges();
        for (Edge edge : allEdges()) {
            List<Edge> parents = findParentEdges(edge)
                    .stream().filter(edge1 -> edge1.getEdgeType() == EdgeType.TREE)
                    .collect(Collectors.toList());

            if (parents.isEmpty()) {
                // no parents, found a new root edge
                // add edge to the result
                result.add(edge);
            }
        }
        return result;
    }
}
