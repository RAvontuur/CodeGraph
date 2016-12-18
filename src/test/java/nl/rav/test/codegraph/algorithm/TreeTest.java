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
    public void testRootNodeOnly() {
        Tree tree = new Tree(1L);
        assertTrue(tree.containsNode(1L));
        assertThat(tree.nodeSize(), is(1));
    }

    @Test
    public void testSimple() {

        Edge edge1 = new Edge(1, 2);
        Edge edge2 = new Edge(2, 3);

        Tree tree = new Tree(edge1);
        tree.addEdge(edge2);

        assertThat(tree.getRootNode(), is(1L));

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

        Tree tree = new Tree(edge1);

        assertFalse(tree.hasCommonDestination(edge2));
        tree.addEdge(edge2);

        assertThat(tree.getRootNode(), is(1L));

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

        Tree tree = new Tree(edge1);

        assertFalse(tree.hasCommonDestination(edge2));
        tree.addEdge(edge2);

        assertThat(tree.getRootNode(), is(1L));

        assertThat(tree.edgeSize(), is(1));
        assertThat(tree.nodeSize(), is(2));

        assertTrue(tree.containsEdge(edge1));
        assertTrue(tree.containsEdge(edge2));

        assertTrue(tree.containsNode(1));
        assertTrue(tree.containsNode(2));

    }

    @Test
    public void testNoCommonRootEdge() {

        Edge edge1 = new Edge(1, 2);
        Edge edge2 = new Edge(1, 3);

        Tree tree = new Tree(edge1);

        assertFalse(tree.hasCommonDestination(edge2));
        tree.addEdge(edge2);

        assertThat(tree.getRootNode(), is(1L));

        assertThat(tree.edgeSize(), is(2));
        assertThat(tree.nodeSize(), is(3));

        assertTrue(tree.containsEdge(edge1));
        assertTrue(tree.containsEdge(edge2));

        assertTrue(tree.containsNode(1));
        assertTrue(tree.containsNode(2));
        assertTrue(tree.containsNode(3));

    }

    @Test
    public void testIndependentEdge() {

        Edge edge1 = new Edge(1, 2);
        Edge edge2 = new Edge(3, 4);
        Edge edge3 = new Edge(1, 4);
        Edge edge4 = new Edge(3, 2);

        Tree tree = new Tree(edge1);

        assertTrue(tree.isIndependentEdge(edge2));

        assertFalse(tree.isIndependentEdge(edge1));
        assertFalse(tree.isIndependentEdge(edge3));
        assertFalse(tree.isIndependentEdge(edge4));
    }

    @Test
    public void testNewLibraryTree() {

        Edge edge1 = new Edge(1, 4);
        Edge edge2 = new Edge(2, 4);

        Tree tree = new Tree(edge1);

        assertTrue(tree.hasCommonDestination(edge2));
        Tree newTree = tree.splitTree(edge2.getToId());

        // tree (original)
        assertThat(tree.getRootNode(), is(1L));
        assertThat(tree.edgeSize(), is(1));
        assertTrue(tree.containsEdge(edge1));

        // new tree (no edges)
        assertThat(newTree.getRootNode(), is(4L));
        assertThat(newTree.edgeSize(), is(0));

    }

    @Test
    public void testNewLibraryTreeWithEdges() {

        Edge edge1 = new Edge(1, 4);
        Edge edge2 = new Edge(4, 5);
        Edge edge3 = new Edge(4, 6);
        Edge edge4 = new Edge(6, 7);
        Edge edge5 = new Edge(2, 4);

        Tree tree = new Tree(edge1);
        tree.addEdge(edge2);
        tree.addEdge(edge3);
        tree.addEdge(edge4);

        assertTrue(tree.hasCommonDestination(edge5));
        Tree newTree = tree.splitTree(edge5.getToId());

        // tree (original)
        assertThat(tree.getRootNode(), is(1L));
        assertThat(tree.edgeSize(), is(1));
        assertTrue(tree.containsEdge(edge1));

        // new tree
        assertThat(newTree.getRootNode(), is(4L));
        assertThat(newTree.edgeSize(), is(3));

        assertTrue(newTree.containsEdge(edge2));
        assertTrue(newTree.containsEdge(edge3));
        assertTrue(newTree.containsEdge(edge4));

        assertTrue(newTree.containsNode(4));
        assertTrue(newTree.containsNode(5));
        assertTrue(newTree.containsNode(6));
        assertTrue(newTree.containsNode(7));

    }


    @Test(expected = IllegalStateException.class)
    public void testSplitTreeWithCycle() {

        Edge edge1 = new Edge(1, 2);
        Edge edge2 = new Edge(2, 3);
        Edge edge3 = new Edge(3, 1);

        Tree tree = new Tree(edge1);
        tree.addEdge(edge2);
        tree.addEdge(edge3);

        tree.splitTree(2L);
    }

    @Test(expected = IllegalStateException.class)
    public void testSplitTreeWithForward() {

        Edge edge1 = new Edge(1, 2);
        Edge edge2 = new Edge(2, 3);
        Edge edge3 = new Edge(1, 3);

        Tree tree = new Tree(edge1);
        tree.addEdge(edge2);
        tree.addEdge(edge3);

        tree.splitTree(2L);
    }

    @Test
    public void testCycle() {

        Edge edge1 = new Edge(1, 2);
        Edge edge2 = new Edge(2, 3);
        Edge edge3 = new Edge(3, 1);

        Tree tree = new Tree(edge1);
        tree.addEdge(edge2);
        assertFalse(tree.hasCommonDestination(edge3));
        tree.addEdge(edge3);

        assertTrue(tree.hasCycleEdge(edge3));
        assertFalse(tree.containsEdge(edge3));
    }

    @Test
    public void testCycle2() {

        Edge edge1 = new Edge(1, 2);
        Edge edge2 = new Edge(3, 1);
        Edge edge3 = new Edge(2, 3);

        Tree tree = new Tree(edge1);
        tree.addEdge(edge2);
        assertFalse(tree.hasCommonDestination(edge3));
        tree.addEdge(edge3);

        assertTrue(tree.hasCycleEdge(edge3));
        assertFalse(tree.containsEdge(edge3));
    }

    @Test
    public void testForward() {

        Edge edge1 = new Edge(1, 2);
        Edge edge2 = new Edge(2, 3);
        Edge edge3 = new Edge(1, 3);

        Tree tree = new Tree(edge1);
        tree.addEdge(edge2);
        tree.addEdge(edge3);

        assertTrue(tree.hasForwardEdge(edge3));
        assertFalse(tree.containsEdge(edge3));

    }

    @Test
    public void testForward2() {

        Edge edge1 = new Edge(1, 2);
        Edge edge2 = new Edge(1, 3);
        Edge edge3 = new Edge(2, 3);

        Tree tree = new Tree(edge1);
        tree.addEdge(edge2);

        assertFalse(tree.hasCommonDestination(edge3));
        tree.addEdge(edge3);

        assertTrue(tree.hasForwardEdge(edge2));
        assertFalse(tree.containsEdge(edge2));
    }

    @Test
    public void testForwardComplex() {

        Edge edge1 = new Edge(1, 2);
        Edge edge2 = new Edge(2, 3);
        Edge edge3 = new Edge(3, 4);
        Edge edge4 = new Edge(3, 5);
        Edge edge5 = new Edge(4, 6);
        Edge edge6 = new Edge(1, 5);
        Edge edge7 = new Edge(2, 5);
        Edge edge8 = new Edge(2, 6);

        Tree tree = new Tree(edge1);
        tree.addEdge(edge2);
        tree.addEdge(edge3);
        tree.addEdge(edge4);
        tree.addEdge(edge5);
        tree.addEdge(edge6);
        tree.addEdge(edge7);
        tree.addEdge(edge8);

        assertTrue(tree.hasForwardEdge(edge6));
        assertFalse(tree.containsEdge(edge6));

        assertTrue(tree.hasForwardEdge(edge7));
        assertFalse(tree.containsEdge(edge7));

        assertTrue(tree.hasForwardEdge(edge8));
        assertFalse(tree.containsEdge(edge8));
    }

    @Test
    public void testForwardComplex2() {

        Edge edge1 = new Edge(4, 6);
        Edge edge2 = new Edge(3, 4);
        Edge edge3 = new Edge(2, 3);
        Edge edge4 = new Edge(2, 5);
        Edge edge5 = new Edge(1, 2);
        Edge edge6 = new Edge(1, 5);
        Edge edge7 = new Edge(2, 6);
        Edge edge8 = new Edge(3, 5);

        Tree tree = new Tree(edge1);
        tree.addEdge(edge2);
        tree.addEdge(edge3);
        tree.addEdge(edge4);
        tree.addEdge(edge5);
        tree.addEdge(edge6);
        tree.addEdge(edge7);
        tree.addEdge(edge8);

        assertTrue(tree.hasForwardEdge(edge4));
        assertFalse(tree.containsEdge(edge4));

        assertTrue(tree.hasForwardEdge(edge6));
        assertFalse(tree.containsEdge(edge6));

        assertTrue(tree.hasForwardEdge(edge7));
        assertFalse(tree.containsEdge(edge7));
    }

}