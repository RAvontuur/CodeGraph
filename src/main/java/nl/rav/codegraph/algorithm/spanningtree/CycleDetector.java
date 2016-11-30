package nl.rav.codegraph.algorithm.spanningtree;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The concept:
 * https://en.wikipedia.org/wiki/Depth-first_search#Output_of_a_depth-first_search
 *
 */
public class CycleDetector {

    private final List<Edge> edges;
    private final List<Edge> analyzedEdges = new ArrayList<>();

    public CycleDetector(List<Edge> edges) {
        this.edges = edges;
    }

    public void resolveCycles() {
        analyzedEdges.clear();
        List<Edge> pathEdges = new ArrayList<>();
        for (Edge edge : edges) {
            if (!analyzedEdges.contains(edge)) {
                traverse(edge, pathEdges);
                addToAnalyzed(edge);
            }
        }
    }

    /**
     * run resolveCycle first
     *
     * @return
     */
    public List<Edge> findRootEdges() {
        List<Edge> result = new ArrayList<>();

        analyzedEdges.clear();
        for (Edge edge : edges) {
            traverseToRoot(edge, result);
        }
        return result;
    }

    private void traverseToRoot(Edge edge, List<Edge> result) {
        if (analyzedEdges.contains(edge)) {
            return;
        }

        List<Edge> parents = findParentEdges(edge)
                .stream().filter(edge1 -> edge1.getEdgeType() != EdgeType.BACK)
                .collect(Collectors.toList());

        if (parents.isEmpty()) {
            //no more parent, root found
            result.add(edge);
        } else {
            for (Edge parent : parents) {
                traverseToRoot(parent, result);
            }
        }
        addToAnalyzed(edge);
    }

    public List<Edge> findParentEdges(Edge edge) {
        return edges.stream()
                .filter(edge1 -> (edge1.getToId() == edge.getFromId()))
                .collect(Collectors.toList());
    }

    public Edge findNextEdge(Edge edge) {
        return edges.stream()
                .filter(edge1 -> edge1.getFromId() == edge.getToId())
                .filter(edge2 -> !analyzedEdges.contains(edge2))
                .findFirst().orElse(null);
    }

    public void addToAnalyzed(Edge edge) {
        analyzedEdges.add(edge);
    }

    // depth first search
    private void traverse(Edge edge, List<Edge> pathEdges) {
        pathEdges.add(edge);

        while (true) {
            Edge nextEdge = findNextEdge(edge);
            if (nextEdge == null) {
                break;
            }
            if (pathEdges.contains(nextEdge)) {
                //a cycle has been detected, resolve the cycle
                edge.setEdgeType(EdgeType.BACK);
            } else {
                traverse(nextEdge, pathEdges);
            }
            addToAnalyzed(nextEdge);
        }

        pathEdges.remove(pathEdges.size() - 1);
    }
}
