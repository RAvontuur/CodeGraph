package nl.rav.codegraph.algorithm.spanningtree;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * a tree is a special kind of a graph
 * this tree class keeps also track of cross, cycle and forward edges
 */
public class Tree {

    private long rootNode;
    private Set<Edge> edges = new HashSet<>();

    // a cycle edge: an edge that causes a cycle within this tree
    private Set<Edge> cycleEdges = new HashSet<>();
    // a forward edge:
    private Set<Edge> forwardEdges = new HashSet<>();

    public Tree(Edge edge) {
        this.rootNode = edge.getFromId();
        edges.add(edge);
    }

    public Tree(long rootNode) {
        this.rootNode = rootNode;
    }

    public Long getRootNode() {
        return rootNode;
    }

    public boolean hasCycleEdge(Edge edge) {
        return cycleEdges.contains(edge);
    }

    public boolean hasForwardEdge(Edge edge) {
        return forwardEdges.contains(edge);
    }

    public boolean containsEdge(Edge edge) {
        return edges.contains(edge);
    }

    public int edgeSize() {
        return edges.size();
    }

    public Set<Edge> getEdges() {
        return edges;
    }

    public boolean containsNode(long id) {
        return (rootNode == id) || edges.stream().anyMatch(edge -> (edge.getFromId() == id) || (edge.getToId() == id));
    }

    public int nodeSize() {
        Set<Long> nodes = new HashSet<>();
        nodes.add(rootNode);
        edges.stream().forEach(edge ->  {
            nodes.add(edge.getFromId());
            nodes.add(edge.getToId());
        });
        return nodes.size();
    }

    /**
     * add an edge to this tree object
     *
     * @param edge
     */
    public void addEdge(Edge edge) {
        if (isIndependentEdge(edge) || hasCommonDestination(edge)) {
            throw new IllegalStateException("Cannot add edge: " + edge);
        }

        if (rootNode == edge.getToId()) {
            rootNode = edge.getFromId();
        }

        if (isCycleMakingEdge(edge)) {
            cycleEdges.add(edge);
        } else if (isForwardMakingEdge(edge)) {
            makeForward(edge);
        } else {
            edges.add(edge);
        }

    }

    public Tree splitTree(long id) {
        if (!forwardEdges.isEmpty() || !cycleEdges.isEmpty()) {
            throw new IllegalStateException("do not call splitTree when tree has forwards or cycles");
        }

        Tree tree = new Tree(id);
        transferEdges(tree, id);
        return tree;
    }

    public boolean isDuplicate(Edge edge) {
        return edges.contains(edge);
    }

    public boolean isIndependentEdge(Edge edge) {
        return !containsNode(edge.getFromId()) && !containsNode(edge.getToId());
    }

    public boolean hasCommonDestination(Edge edge) {
        return containsNode(
                edge.getToId())
                && (rootNode != edge.getToId())
                && !isCycleMakingEdge(edge)
                && !isForwardMakingEdge(edge)
                && !isDuplicate(edge);
    }

    private boolean containsBothNodes(Edge edge) {
        return containsNode(edge.getToId()) && containsNode(edge.getFromId());
    }

    private boolean isCycleMakingEdge(Edge edge) {
        if (containsBothNodes(edge)) {
            long fromNode = edge.getFromId();
            while (true) {
                Edge parent = findEdgeTo(fromNode);
                if (parent == null) {
                    return false;
                }
                if (parent.getFromId() == edge.getToId()) {
                    return true;
                }
                fromNode = parent.getFromId();
            }
        }
        return false;
    }

    private boolean isForwardMakingEdge(Edge edge) {
        if (containsBothNodes(edge)) {

            // A -> B -> C  A -> C  (edge = A -> C)
            long fromNode = edge.getToId();
            while (true) {
                Edge parent = findEdgeTo(fromNode);
                if (parent == null) {
                    break;
                }
                if (parent.getFromId() == edge.getFromId()) {
                    return true;
                }

                fromNode = parent.getFromId();
            }

            // A -> B -> C  A -> C  (edge = B -> C)
            Edge candidate = findEdgeTo(edge.getToId());
            if (candidate != null) {
                fromNode = edge.getFromId();
                while (true) {
                    Edge parent = findEdgeTo(fromNode);
                    if (parent == null) {
                        break;
                    }
                    if (parent.getFromId() == candidate.getFromId()) {
                        return true;
                    }

                    fromNode = parent.getFromId();
                }
            }
        }
        return false;
    }

    private void makeForward(Edge edge) {
        // A -> B -> C  A -> C  (edge = A -> C)
        long fromNode = edge.getToId();
        while (true) {
            Edge parent = findEdgeTo(fromNode);
            if (parent == null) {
                break;
            }
            if (parent.getFromId() == edge.getFromId()) {
                forwardEdges.add(edge);
                return;
            }
            fromNode = parent.getFromId();
        }

        // A -> B -> C  A -> C  (edge = B -> C)
        Edge candidate = findEdgeTo(edge.getToId());
        if (candidate != null) {
            fromNode = edge.getFromId();
            while (true) {
                Edge parent = findEdgeTo(fromNode);
                if (parent == null) {
                    break;
                }
                if (parent.getFromId() == candidate.getFromId()) {
                    forwardEdges.add(candidate);
                    edges.add(edge);
                    edges.remove(candidate);
                }
                fromNode = parent.getFromId();
            }
        }
    }

    private void transferEdges(Tree tree, long fromId) {
        Set<Edge> edgesFound = findEdgesFrom(fromId);
        edgesFound.stream().forEach(edge -> {
            tree.edges.add(edge);
            edges.remove(edge);
            transferEdges(tree, edge.getToId());
        });
    }

    private Set<Edge> findEdgesFrom(long fromId) {
        return edges.stream().filter(edge -> edge.getFromId() == fromId).collect(Collectors.toSet());
    }

    private Edge findEdgeTo(long toId) {
        return edges.stream().filter(edge -> edge.getToId() == toId).findFirst().orElse(null);
    }

    public void merge(Tree tailTree) {
        this.edges.addAll(tailTree.edges);
        this.cycleEdges.addAll(tailTree.cycleEdges);
        this.forwardEdges.addAll(tailTree.forwardEdges);
    }
}
