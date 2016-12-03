package nl.rav.codegraph.algorithm.spanningtree;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The concept:
 * https://en.wikipedia.org/wiki/Depth-first_search#Output_of_a_depth-first_search
 */
public class BaseDetector {

    private final List<Edge> edges;
    private final List<Edge> analyzedEdges = new ArrayList<>();

    public BaseDetector(List<Edge> edges) {
        this.edges = edges;
    }

    public List<Edge> allEdges() {
        return edges;
    }

    public Edge findEdge(Long fromId, long toId) {
        return allEdges().stream().filter(edge -> (edge.getFromId() == fromId) && (edge.getToId() == toId)).findFirst().orElse(null);
    }

    public int distanceToRoot(Edge edge) {
        int distance = 0;
        Edge current = edge;
        while ((current != null) && (current.getAssignedParentId() != null)) {
            distance++;
            current = findEdge(current.getAssignedParentId(), current.getFromId());
        }
        return distance;
    }

    public void clearAnalyzedEdges() {
        analyzedEdges.clear();
    }

    public boolean isAnalyzed(Edge edge) {
        return analyzedEdges.contains(edge);
    }

    public void addToAnalyzed(Edge edge) {
        analyzedEdges.add(edge);
    }

    public List<Edge> findParentEdges(Edge edge) {
        return edges.stream()
                .filter(edge1 -> (edge1.getToId() == edge.getFromId()))
                .collect(Collectors.toList());
    }

    public void traverseAllDepthFirst() {
        clearAnalyzedEdges();
        List<Edge> pathEdges = new ArrayList<>();
        allEdges().stream().filter(edge -> !isAnalyzed(edge)).forEach(edge -> {
            onVisitEdge(edge, null, pathEdges);
            traverse(edge, pathEdges);
            addToAnalyzed(edge);
        });
    }

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
            }
            addToAnalyzed(nextEdge);
        }
        pathEdges.remove(pathEdges.size() - 1);
    }

    protected boolean onVisitEdge(Edge edge, Edge parentEdge, List<Edge> pathEdges) {
        //visit all
        return true;
    }

    ;

    public Edge findNextEdge(Edge edge) {
        return edges.stream()
                .filter(edge1 -> edge1.getFromId() == edge.getToId())
                .filter(edge2 -> !analyzedEdges.contains(edge2))
                .findFirst().orElse(null);
    }

    protected List<Edge> findAnalyzedEdgesWithToId(long toId) {
        return analyzedEdges.stream()
                .filter(edge -> edge.getToId() == toId)
                .collect(Collectors.toList());
    }
}
