package nl.rav.codegraph.algorithm.spanningtree;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by rene on 11-12-16.
 */
public class Tree {

    private Edge rootEdge;
    private Set<Edge> edges = new HashSet<>();
    private Set<Long> nodes = new HashSet<>();

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

    public boolean addEdge(Edge edge) {
        if (edges.contains(edge)) {
            return true;
        }

        if (rootEdge == null) {
            //first edge
            rootEdge = edge;
        } else if (!nodes.contains(edge.getFromId()) && !nodes.contains(edge.getToId())) {
            //not any connection possible
            return false;
        } else if (rootEdge.getFromId() == edge.getToId()) {
            //new root
            rootEdge = edge;
        } else if (nodes.contains(edge.getToId())) {
            //cycle or forward
            return addCycleOrForward(edge);
        }

        edges.add(edge);

        addNode(edge.getFromId());
        addNode(edge.getToId());

        return true;
    }

    private boolean addCycleOrForward(Edge edge) {
        if (!nodes.contains(edge.getFromId())) {
            // edge belongs to a new tree
            return false;
        }


        return true;
    }

    private void addNode(long id) {
        nodes.add(id);
    }
}
