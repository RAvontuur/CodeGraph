package nl.rav.codegraph.algorithm.spanningtree;

import java.util.List;

/**
 * detect cycles in the graph, by depth-first traversal
 * mark the edge that causes a cycle as EdgeType: BACK
 */
public class CycleDetector extends BaseDetector {

    public CycleDetector(List<Edge> edges) {
        super(edges);
    }

    public void resolveCycles() {
        traverseAllDepthFirst();
    }

    protected boolean onVisitEdge(Edge edge, Edge parentEdge, List<Edge> pathEdges) {

        if (pathEdges.contains(edge)) {
            //a cycle has been detected, resolve the cycle
            parentEdge.setEdgeType(EdgeType.BACK);
            return false;
        }

//        List<Edge> crossEdges = findAnalyzedEdgesWithToId(edge.getToId());
//        if (!crossEdges.isEmpty()) {
//            boolean crossFound = false;
//            for (Edge forwardCandidateEdge : crossEdges) {
//                boolean forwardFound = false;
//                for (Edge pathEdge : pathEdges) {
//                    if (pathEdge.equals(edge)) {
//                        continue;
//                    }
//                    if (pathEdge.getFromId() == forwardCandidateEdge.getFromId()) {
//                        forwardCandidateEdge.setEdgeType(EdgeType.FORWARD);
//                        forwardFound = true;
//                    }
//                }
//                if (!forwardFound) {
//                    crossFound = true;
//                }
//            }
//            if (crossFound) {
//                edge.setEdgeType(EdgeType.CROSS);
//            }
//        }

        return true;
    }

}
