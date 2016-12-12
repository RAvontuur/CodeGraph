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

    public Edge getRootEdge() {
        return rootEdge;
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
        result.add(splitTree(edge.getToId()));
        //TODO add to crossEdges + new tree or attach to tree
    }

    private Tree splitTree(long id) {
        Tree tree = new Tree();
        transferEdges(tree, id);
        //add removed root-node, as it is still a destination node
        nodes.add(id);
        return tree;
    }

    private void transferEdges(Tree tree, long fromId) {
        Set<Edge> edgesFound = findEdges(fromId);
        edgesFound.stream().forEach(edge -> {
            tree.addEdge(edge);
            edges.remove(edge);
            transferEdges(tree, edge.getToId());
        });
        nodes.remove(fromId);
    }

    private Set<Edge> findEdges(long fromId) {
        return edges.stream().filter(edge -> edge.getFromId() == fromId).collect(Collectors.toSet());
    }
}
