package nl.rav.codegraph.algorithm.spanningtree;

import java.util.ArrayList;
import java.util.List;

/**
 * detect cycles in the graph, by depth-first traversal
 * mark the edge that causes a cycle as EdgeType: BACK
 */
public class CycleDetector extends BaseDetector {


    private List<Edge> longestPathEdges = new ArrayList<>();
    private boolean hasCycle = false;
    private boolean hasCrossOrForward = false;

    public CycleDetector(List<Edge> edges) {
        super(edges);
    }

    public void resolveCycles() {
        while (!completed(allEdges())) {
            traverseAllDepthFirst();
            markAsTree(longestPathEdges, hasCrossOrForward, hasCycle);
            longestPathEdges.clear();
            hasCycle = false;
            hasCrossOrForward = false;
        }
    }


    protected boolean onVisitEdge(Edge edge, Edge parentEdge, List<Edge> pathEdges) {
        boolean pathHasCrossOrForward = false;
        boolean pathHasCycle = false;

        if ((edge.getEdgeType() != null) && (parentEdge == null)) {
            //do not start with an edge that has been identified
            return false;
        }

        if (pathEdges.contains(edge)) {
            pathHasCycle = true;
        } else if (edge.getEdgeType() != null) {
            pathHasCrossOrForward = true;
        }

        if (pathEdges.size() + 1 > longestPathEdges.size()) {
            longestPathEdges.clear();
            longestPathEdges.addAll(pathEdges);
            longestPathEdges.add(edge);
            hasCrossOrForward = pathHasCrossOrForward;
            hasCycle = pathHasCycle;
        }

        return !(pathHasCycle || pathHasCrossOrForward);
    }

    private boolean completed(List<Edge> edges) {
        Edge edgeFound = edges.stream()
                .filter(edge -> edge.getEdgeType() == null)
                .findFirst().orElse(null);

        // completed if no more edge found without edgeType
        return edgeFound == null;
    }

    private void markAsTree(List<Edge> longestPathEdges, boolean hasCrossOrForward, boolean hasCycle) {

        if (hasCycle) {
            longestPathEdges.get(longestPathEdges.size() - 2).setEdgeType(EdgeType.BACK);
        }
        if (hasCrossOrForward) {
            longestPathEdges.get(longestPathEdges.size() - 2).setEdgeType(EdgeType.CROSS);
        }

        longestPathEdges.stream()
                .filter(edge1 -> edge1.getEdgeType() == null)
                .forEach(edge -> edge.setEdgeType(EdgeType.TREE));

    }


}
