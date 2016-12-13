package nl.rav.test.codegraph.algorithm;

import nl.rav.codegraph.algorithm.spanningtree.Edge;
import nl.rav.codegraph.algorithm.spanningtree.Tree;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
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
    public void testIndependentNewTree() {

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
        List<Tree> treeList = tree.addEdge(edge3);

        // tree (original)
        assertThat(treeList.get(0), is(tree));
        assertThat(tree.getRootEdge(), is(edge1));

        assertThat(tree.edgeSize(), is(1));
        assertThat(tree.nodeSize(), is(2));

        assertTrue(tree.containsEdge(edge1));

        assertTrue(tree.containsNode(1));
        assertTrue(tree.containsNode(3));

        // new tree (containing the added edge)
        Tree newTree = treeList.get(1);
        assertFalse(newTree.isLibrary());
        assertThat(newTree.getRootEdge(), is(edge3));

        assertThat(newTree.edgeSize(), is(1));
        assertThat(newTree.nodeSize(), is(2));

        assertTrue(newTree.containsEdge(edge3));

        assertTrue(newTree.containsNode(2));
        assertTrue(newTree.containsNode(3));

        // new tree (the library)
        Tree libTree = treeList.get(2);
        assertTrue(libTree.isLibrary());
        assertThat(libTree.getRootEdge(), is(edge2));

        assertThat(libTree.edgeSize(), is(1));
        assertThat(libTree.nodeSize(), is(2));

        assertTrue(libTree.containsEdge(edge2));

        assertTrue(libTree.containsNode(3));
        assertTrue(libTree.containsNode(4));

        // verify presence of cross edges
        assertTrue(tree.hasCrossEdge(edge1));
        assertTrue(newTree.hasCrossEdge(edge3));
    }

    @Test
    public void testCycle() {

        Edge edge1 = new Edge(1, 2);
        Edge edge2 = new Edge(2, 3);
        Edge edge3 = new Edge(3, 1);

        Tree tree = new Tree();
        assertThat(tree.addEdge(edge1).get(0), is(tree));
        assertThat(tree.addEdge(edge2).get(0), is(tree));
        List<Tree> treeList = tree.addEdge(edge3);
        assertThat(treeList.size(), is(1));

        //TODO implement cycle support
        assertTrue(tree.hasCycleEdge(edge3));
    }

    @Test
    public void testForward() {

        Edge edge1 = new Edge(1, 2);
        Edge edge2 = new Edge(2, 3);
        Edge edge3 = new Edge(1, 3);

        Tree tree = new Tree();
        assertThat(tree.addEdge(edge1).get(0), is(tree));
        assertThat(tree.addEdge(edge2).get(0), is(tree));
        List<Tree> treeList = tree.addEdge(edge3);
        assertThat(treeList.size(), is(1));

        //TODO implement forward support
        assertTrue(tree.hasForwardEdge(edge3));
    }

    @Test
    public void testAddTree() {
        Edge edge1 = new Edge(1, 2);
        Edge edge2 = new Edge(2, 3);
        Edge edge3 = new Edge(3, 4);

        Tree tree1 = new Tree();
        tree1.addEdge(edge1);

        Tree tree2 = new Tree();
        tree2.addEdge(edge2);
        tree2.addEdge(edge3);

        // TODO implement
        //if (tree1.canAddTree(tree2))
        //tree1.addTree(tree2);
    }

    @Test
    public void testAddTree2() {
        Edge edge1 = new Edge(1, 2);
        Edge edge2 = new Edge(2, 3);
        Edge edge3 = new Edge(3, 4);

        Tree tree1 = new Tree();
        tree1.addEdge(edge1);

        Tree tree2 = new Tree();
        tree2.addEdge(edge2);
        tree2.addEdge(edge3);

        // TODO implement
        //if (tree2.canAddTree(tree1))
        //tree2.addTree(tree1);
    }


}