package nl.rav.codegraph.algorithm.spanningtree;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by rene on 11-12-16.
 */
public class Tree {

    private Edge rootEdge;
    private Set<Edge> edges = new HashSet<>();
    private Set<Long> nodes = new HashSet<>();
    //edges that connect this tree to the roots of other trees
    private Set<Edge> crossEdges = new HashSet<>();
    private boolean library = false;

    public Edge getRootEdge() {
        return rootEdge;
    }

    public boolean isLibrary() {
        return library;
    }

    public void setLibrary(boolean library) {
        this.library = library;
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

    /**
     * @param edge
     * @return list of modified or new trees
     */
    public List<Tree> addEdge(Edge edge) {
        List<Tree> result = new ArrayList<>();

        if (isDuplicate(edge)) {
            //do nothing
        } else if (isRootEdge(edge)) {
            //new root
            rootEdge = edge;
            addEdgeAndNodes(result, edge);
        } else if (isLibraryMakingEdge(edge)) {
            makeLibrary(result, edge);
        } else if (isIsolatedEdge(edge)) {
            makeNewTree(result, edge);
        } else {
            addEdgeAndNodes(result, edge);
        }

        return result;
    }

    private void addEdgeAndNodes(List<Tree> result, Edge edge) {
        edges.add(edge);
        nodes.add(edge.getFromId());
        nodes.add(edge.getToId());
        result.add(this);
    }

    private boolean isDuplicate(Edge edge) {
        return edges.contains(edge);
    }

    private boolean isRootEdge(Edge edge) {
        return (rootEdge == null) ||
                ((rootEdge.getFromId() == edge.getToId()) && (!nodes.contains(edge.getFromId())));
    }

    private boolean isIsolatedEdge(Edge edge) {
        return !nodes.contains(edge.getFromId()) && !nodes.contains(edge.getToId());
    }

    private void makeNewTree(List<Tree> result, Edge edge) {
        Tree tree = new Tree();
        tree.addEdge(edge);
        result.add(tree);
    }

    private boolean isLibraryMakingEdge(Edge edge) {
        return nodes.contains(edge.getToId()) && (rootEdge != null) && (rootEdge.getFromId() != edge.getToId());
    }

    private void makeLibrary(List<Tree> result, Edge edge) {

        // 0. add the existing tree
        result.add(this);
        // 1. add the new edge as a new tree
        makeNewTree(result, edge);
        // 2. add the library tree
        result.add(splitTree(edge.getToId()));

        result.get(0).crossEdges.add(result.get(0).findEdgeTo(edge.getToId()));
        result.get(1).crossEdges.add(edge);
        result.get(2).setLibrary(true);
    }

    private Tree splitTree(long id) {
        Tree tree = new Tree();
        transferEdges(tree, id);
        //add removed root-node, as it is still a destination node
        nodes.add(id);
        return tree;
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
}
