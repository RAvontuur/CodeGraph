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
    private Set<Long> nodes = new HashSet<>();

    // a cycle edge: an edge that causes a cycle within this tree
    private Set<Edge> cycleEdges = new HashSet<>();
    // a forward edge:
    private Set<Edge> forwardEdges = new HashSet<>();

    public Tree(Edge edge) {
        this.rootNode = edge.getFromId();
        edges.add(edge);
        nodes.add(edge.getFromId());
        nodes.add(edge.getToId());
    }

    public Tree(long rootNode) {
        this.rootNode = rootNode;
        nodes.add(rootNode);
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

    public boolean containsNode(long id) {
        return nodes.contains(id);
    }

    public boolean containsEdge(Edge edge) {
        return edges.contains(edge);
    }

    public int nodeSize() {
        return nodes.size();
    }

    public int edgeSize() {
        return edges.size();
    }

    public Set<Edge> getEdges() {
        return edges;
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
            nodes.add(edge.getFromId());
            nodes.add(edge.getToId());
        }

    }

    public Tree splitTree(long id) {
        Tree tree = new Tree(id);
        transferEdges(tree, id);
        //add removed root-node, as it is still a destination node
        nodes.add(id);
        return tree;
    }

    public boolean isDuplicate(Edge edge) {
        return edges.contains(edge);
    }

    public boolean isIndependentEdge(Edge edge) {
        return !nodes.contains(edge.getFromId()) && !nodes.contains(edge.getToId());
    }

    public boolean hasCommonDestination(Edge edge) {
        return nodes.contains(
                edge.getToId())
                && (rootNode != edge.getToId())
                && !isCycleMakingEdge(edge)
                && !isForwardMakingEdge(edge)
                && !isDuplicate(edge);
    }

    private boolean containsBothNodes(Edge edge) {
        return nodes.contains(edge.getToId()) && nodes.contains(edge.getFromId());
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
            tree.addEdge(edge);
            edges.remove(edge);
            transferEdges(tree, edge.getToId());
        });
        nodes.remove(fromId);
    }

    private Set<Edge> findEdgesFrom(long fromId) {
        return edges.stream().filter(edge -> edge.getFromId() == fromId).collect(Collectors.toSet());
    }

    private Edge findEdgeTo(long toId) {
        return edges.stream().filter(edge -> edge.getToId() == toId).findFirst().orElse(null);
    }

    public void merge(Tree tailTree) {
        this.edges.addAll(tailTree.edges);
        this.nodes.addAll(tailTree.nodes);
        this.cycleEdges.addAll(tailTree.cycleEdges);
        this.forwardEdges.addAll(tailTree.forwardEdges);
    }
}
