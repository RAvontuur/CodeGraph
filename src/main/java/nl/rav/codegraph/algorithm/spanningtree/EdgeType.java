package nl.rav.codegraph.algorithm.spanningtree;

/**
 * Created by rene on 30-11-16.
 */
public enum EdgeType {
    TREE, BACK, FORWARD, CROSS;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
