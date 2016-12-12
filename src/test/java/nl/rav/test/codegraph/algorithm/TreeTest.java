package nl.rav.test.codegraph.algorithm;

import nl.rav.codegraph.algorithm.spanningtree.Edge;
import nl.rav.codegraph.algorithm.spanningtree.Tree;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
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
        assertThat(tree.addEdge(edge1).get(0), is(tree));
        assertThat(tree.addEdge(edge2).get(0), is(tree));

        assertThat(tree.getRootEdge(), is(edge1));

        assertThat(tree.edgeSize(), is(2));
        assertThat(tree.nodeSize(), is(3));

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
        assertThat(tree.addEdge(edge1).get(0), is(tree));
        assertThat(tree.addEdge(edge2).get(0), is(tree));

        assertThat(tree.getRootEdge(), is(edge2));

        assertThat(tree.edgeSize(), is(2));
        assertThat(tree.nodeSize(), is(3));

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
        assertThat(tree.addEdge(edge1).get(0), is(tree));
        assertTrue(tree.addEdge(edge2).isEmpty());

        assertThat(tree.getRootEdge(), is(edge1));

        assertThat(tree.edgeSize(), is(1));
        assertThat(tree.nodeSize(), is(2));

        assertTrue(tree.containsEdge(edge1));
        assertTrue(tree.containsEdge(edge2));

        assertTrue(tree.containsNode(1));
        assertTrue(tree.containsNode(2));

    }

    @Test
    public void testNewTree() {

        Edge edge1 = new Edge(1, 2);
        Edge edge2 = new Edge(3, 4);

        Tree tree = new Tree();
        assertThat(tree.addEdge(edge1).get(0), is(tree));
        Tree newTree = tree.addEdge(edge2).get(0);
        assertThat(newTree, not(is(tree)));

        assertThat(tree.getRootEdge(), is(edge1));

        assertThat(tree.edgeSize(), is(1));
        assertThat(tree.nodeSize(), is(2));

        assertTrue(tree.containsEdge(edge1));

        assertTrue(tree.containsNode(1));
        assertTrue(tree.containsNode(2));

        // new tree
        assertThat(newTree.getRootEdge(), is(edge2));

        assertThat(newTree.edgeSize(), is(1));
        assertThat(newTree.nodeSize(), is(2));

        assertTrue(newTree.containsEdge(edge2));

        assertTrue(newTree.containsNode(3));
        assertTrue(newTree.containsNode(4));
    }

    @Test
    public void testNewLibraryTree() {

        Edge edge1 = new Edge(1, 3);
        Edge edge2 = new Edge(3, 4);
        Edge edge3 = new Edge(2, 3);

        Tree tree = new Tree();
        assertThat(tree.addEdge(edge1).get(0), is(tree));
        assertThat(tree.addEdge(edge2).get(0), is(tree));
        Tree newTree = tree.addEdge(edge3).get(0);

        // tree
        assertThat(tree.getRootEdge(), is(edge1));

        assertThat(tree.edgeSize(), is(1));
        assertThat(tree.nodeSize(), is(2));

        assertTrue(tree.containsEdge(edge1));

        assertTrue(tree.containsNode(1));
        assertTrue(tree.containsNode(3));

        // new tree I
        assertThat(newTree.getRootEdge(), is(edge2));

        assertThat(newTree.edgeSize(), is(1));
        assertThat(newTree.nodeSize(), is(2));

        assertTrue(newTree.containsEdge(edge2));

        assertTrue(newTree.containsNode(3));
        assertTrue(newTree.containsNode(4));

        // new tree II
        // TODO
    }
}