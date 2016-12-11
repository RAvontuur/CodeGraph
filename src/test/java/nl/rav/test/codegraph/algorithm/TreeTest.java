package nl.rav.test.codegraph.algorithm;

import nl.rav.codegraph.algorithm.spanningtree.Edge;
import nl.rav.codegraph.algorithm.spanningtree.Tree;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by rene on 11-12-16.
 */
public class TreeTest {


    @Test
    public void testSimple() {

        Edge edge1 = new Edge(1, 2);
        Edge edge2 = new Edge(2, 3);

        Tree tree = new Tree();
        assertTrue(tree.addEdge(edge1));
        assertTrue(tree.addEdge(edge2));

        assertThat(tree.getRootEdge(), is(edge1));

        assertThat(2, is(tree.edgeSize()));
        assertThat(3, is(tree.nodeSize()));

        assertTrue(tree.containsEdge(edge1));
        assertTrue(tree.containsEdge(edge2));

        assertTrue(tree.containsNode(1));
        assertTrue(tree.containsNode(2));
        assertTrue(tree.containsNode(3));

    }

    @Test
    public void testSimpleReverse() {

        Edge edge1 = new Edge(2, 3);
        Edge edge2 = new Edge(1, 2);

        Tree tree = new Tree();
        assertTrue(tree.addEdge(edge1));
        assertTrue(tree.addEdge(edge2));

        assertThat(tree.getRootEdge(), is(edge2));

        assertThat(2, is(tree.edgeSize()));
        assertThat(3, is(tree.nodeSize()));

        assertTrue(tree.containsEdge(edge1));
        assertTrue(tree.containsEdge(edge2));

        assertTrue(tree.containsNode(1));
        assertTrue(tree.containsNode(2));
        assertTrue(tree.containsNode(3));

    }

    @Test
    public void testDuplicate() {

        Edge edge1 = new Edge(1, 2);
        Edge edge2 = new Edge(1, 2);

        Tree tree = new Tree();
        assertTrue(tree.addEdge(edge1));
        assertTrue(tree.addEdge(edge2));

        assertThat(tree.getRootEdge(), is(edge1));

        assertThat(1, is(tree.edgeSize()));
        assertThat(2, is(tree.nodeSize()));

        assertTrue(tree.containsEdge(edge1));
        assertTrue(tree.containsEdge(edge2));

        assertTrue(tree.containsNode(1));
        assertTrue(tree.containsNode(2));

    }

    @Test
    public void testNoConnection() {

        Edge edge1 = new Edge(1, 2);
        Edge edge2 = new Edge(3, 4);

        Tree tree = new Tree();
        assertTrue(tree.addEdge(edge1));
        assertFalse(tree.addEdge(edge2));

        assertThat(tree.getRootEdge(), is(edge1));

        assertThat(1, is(tree.edgeSize()));
        assertThat(2, is(tree.nodeSize()));

        assertTrue(tree.containsEdge(edge1));

        assertTrue(tree.containsNode(1));
        assertTrue(tree.containsNode(2));

    }
}
