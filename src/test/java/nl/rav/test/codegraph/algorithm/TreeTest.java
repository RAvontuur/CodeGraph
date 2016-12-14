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

        Tree tree = new Tree();
        assertThat(tree.addEdge(edge1).get(0), is(tree));
        assertThat(tree.addEdge(edge2).get(0), is(tree));

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

        Tree tree = new Tree();
        assertThat(tree.addEdge(edge1).get(0), is(tree));
        assertTrue(tree.addEdge(edge2).isEmpty());

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

        Tree tree = new Tree();
        assertThat(tree.addEdge(edge1).get(0), is(tree));
        assertThat(tree.addEdge(edge2).get(0), is(tree));

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
    public void testIndependentNewTree() {

        Edge edge1 = new Edge(1, 2);
        Edge edge2 = new Edge(3, 4);

        Tree tree = new Tree();
        assertThat(tree.addEdge(edge1).get(0), is(tree));
        Tree newTree = tree.addEdge(edge2).get(0);
        assertThat(newTree, not(is(tree)));

        assertThat(tree.getRootNode(), is(1L));

        assertThat(tree.edgeSize(), is(1));
        assertThat(tree.nodeSize(), is(2));

        assertTrue(tree.containsEdge(edge1));

        assertTrue(tree.containsNode(1));
        assertTrue(tree.containsNode(2));

        // new tree
        assertThat(newTree.getRootNode(), is(3L));

        assertThat(newTree.edgeSize(), is(1));
        assertThat(newTree.nodeSize(), is(2));

        assertTrue(newTree.containsEdge(edge2));

        assertTrue(newTree.containsNode(3));
        assertTrue(newTree.containsNode(4));
    }

    @Test
    public void testNewLibraryTree() {

        Edge edge1 = new Edge(1, 4);
        Edge edge2 = new Edge(2, 4);

        Tree tree = new Tree();
        assertThat(tree.addEdge(edge1).get(0), is(tree));
        List<Tree> treeList = tree.addEdge(edge2);

        assertThat(treeList.size(), is(2));

        // tree (original)
        assertThat(treeList.get(0), is(tree));
        assertThat(tree.getRootNode(), is(1L));
        assertThat(tree.edgeSize(), is(1));
        assertTrue(tree.containsEdge(edge1));

        // new tree (containing the added edge)
        Tree newTree = treeList.get(1);
        assertThat(newTree.getRootNode(), is(2L));
        assertThat(newTree.edgeSize(), is(1));
        assertTrue(newTree.containsEdge(edge2));

        // verify presence of cross edges
        assertTrue(tree.hasCrossEdge(edge1));
        assertTrue(newTree.hasCrossEdge(edge2));
    }


    @Test
    public void testNewLibraryTreeThreeNodes() {

        Edge edge1 = new Edge(1, 4);
        Edge edge2 = new Edge(2, 4);
        Edge edge3 = new Edge(3, 4);

        Tree tree = new Tree();
        assertThat(tree.addEdge(edge1).get(0), is(tree));
        assertThat(tree.addEdge(edge2).get(0), is(tree));
        List<Tree> treeList = tree.addEdge(edge3);

        assertThat(treeList.size(), is(2));

        // tree (original)
        assertThat(treeList.get(0), is(tree));
        assertThat(tree.getRootNode(), is(1L));
        assertThat(tree.edgeSize(), is(1));
        assertTrue(tree.containsEdge(edge1));

        // new tree (containing the added edge)
        Tree newTree = treeList.get(1);
        assertThat(newTree.getRootNode(), is(3L));
        assertThat(newTree.edgeSize(), is(1));
        assertTrue(newTree.containsEdge(edge3));

        // verify presence of cross edges
        assertTrue(tree.hasCrossEdge(edge1));
        assertTrue(newTree.hasCrossEdge(edge3));
    }

    @Test
    public void testNewLibraryTree2() {

        Edge edge1 = new Edge(1, 3);
        Edge edge2 = new Edge(3, 4);
        Edge edge3 = new Edge(2, 3);

        Tree tree = new Tree();
        assertThat(tree.addEdge(edge1).get(0), is(tree));
        assertThat(tree.addEdge(edge2).get(0), is(tree));
        List<Tree> treeList = tree.addEdge(edge3);

        // tree (original)
        assertThat(treeList.get(0), is(tree));
        assertThat(tree.getRootNode(), is(1L));
        assertThat(tree.edgeSize(), is(1));
        assertThat(tree.nodeSize(), is(2));

        assertTrue(tree.containsEdge(edge1));

        assertTrue(tree.containsNode(1));
        assertTrue(tree.containsNode(3));

        // new tree (containing the added edge)
        Tree newTree = treeList.get(1);
        assertThat(newTree.getRootNode(), is(2L));
        assertThat(newTree.edgeSize(), is(1));
        assertThat(newTree.nodeSize(), is(2));

        assertTrue(newTree.containsEdge(edge3));

        assertTrue(newTree.containsNode(2));
        assertTrue(newTree.containsNode(3));

        // new tree (the library)
        Tree libTree = treeList.get(2);
        assertThat(libTree.getRootNode(), is(3L));
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

        assertTrue(tree.hasCycleEdge(edge3));
        assertFalse(tree.containsEdge(edge3));
    }

    @Test
    public void testCycle2() {

        Edge edge1 = new Edge(1, 2);
        Edge edge2 = new Edge(3, 1);
        Edge edge3 = new Edge(2, 3);

        Tree tree = new Tree();
        assertThat(tree.addEdge(edge1).get(0), is(tree));
        assertThat(tree.addEdge(edge2).get(0), is(tree));
        List<Tree> treeList = tree.addEdge(edge3);
        assertThat(treeList.size(), is(1));

        assertTrue(tree.hasCycleEdge(edge3));
        assertFalse(tree.containsEdge(edge3));
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

        assertTrue(tree.hasForwardEdge(edge3));
        assertFalse(tree.containsEdge(edge3));
    }

    @Test
    public void testForward2() {

        Edge edge1 = new Edge(1, 2);
        Edge edge2 = new Edge(1, 3);
        Edge edge3 = new Edge(2, 3);

        Tree tree = new Tree();
        assertThat(tree.addEdge(edge1).get(0), is(tree));
        assertThat(tree.addEdge(edge2).get(0), is(tree));
        List<Tree> treeList = tree.addEdge(edge3);
        assertThat(treeList.size(), is(1));

        assertTrue(tree.hasForwardEdge(edge2));
        assertFalse(tree.containsEdge(edge2));
    }

    @Test
    public void testAddTree() {
        Edge edge1 = new Edge(1, 2);
        Edge edge2 = new Edge(2, 3);
        Edge edge3 = new Edge(3, 4);
        Edge edge4 = new Edge(3, 5);
        Edge edge5 = new Edge(5, 6);
        Edge edge6 = new Edge(5, 7);

        Tree tree1 = new Tree();
        tree1.addEdge(edge1);

        Tree tree2 = new Tree();
        tree2.addEdge(edge2);
        tree2.addEdge(edge3);
        tree2.addEdge(edge4);
        tree2.addEdge(edge5);
        tree2.addEdge(edge6);

        assertTrue(tree1.canMergeTree(tree2));
        tree1.addTree(tree2);

        assertTrue(tree1.containsEdge(edge1));
        assertTrue(tree1.containsEdge(edge2));
        assertTrue(tree1.containsEdge(edge3));
        assertTrue(tree1.containsEdge(edge4));
        assertTrue(tree1.containsEdge(edge5));
        assertTrue(tree1.containsEdge(edge6));
    }

    @Test
    public void testAddTreeWithForward() {

        Edge edge1 = new Edge(1, 2);
        Edge edge2 = new Edge(2, 3);

        Edge edge3 = new Edge(2, 4);
        Edge edge4 = new Edge(4, 3);
        Edge edge5 = new Edge(3, 5);
        Edge edge6 = new Edge(3, 6);

        Tree tree1 = new Tree();
        tree1.addEdge(edge1);
        tree1.addEdge(edge2);

        Tree tree2 = new Tree();
        tree2.addEdge(edge3);
        tree2.addEdge(edge4);
        tree2.addEdge(edge5);
        tree2.addEdge(edge6);

        assertTrue(tree1.canMergeTree(tree2));
        tree1.addTree(tree2);

        assertTrue(tree1.containsEdge(edge1));
        assertTrue(tree1.hasForwardEdge(edge2));
        assertTrue(tree1.containsEdge(edge3));
        assertTrue(tree1.containsEdge(edge4));
        assertTrue(tree1.containsEdge(edge5));
        assertTrue(tree1.containsEdge(edge6));
    }


    @Test
    public void testAddTreeWithCycle() {

        Edge edge1 = new Edge(1, 2);
        Edge edge2 = new Edge(2, 3);

        Edge edge3 = new Edge(2, 4);
        Edge edge4 = new Edge(4, 5);
        Edge edge5 = new Edge(5, 1);
        Edge edge6 = new Edge(5, 6);

        Tree tree1 = new Tree();
        tree1.addEdge(edge1);
        tree1.addEdge(edge2);

        Tree tree2 = new Tree();
        tree2.addEdge(edge3);
        tree2.addEdge(edge4);
        tree2.addEdge(edge5);
        tree2.addEdge(edge6);

        assertTrue(tree1.canMergeTree(tree2));
        tree1.addTree(tree2);

        assertTrue(tree1.containsEdge(edge1));
        assertTrue(tree1.containsEdge(edge2));
        assertTrue(tree1.containsEdge(edge3));
        assertTrue(tree1.containsEdge(edge4));
        assertTrue(tree1.hasCycleEdge(edge5));
        assertTrue(tree1.containsEdge(edge6));
    }

    @Test
    public void testAddTreeWithLibrary() {

        Edge edge1 = new Edge(1, 2);
        Edge edge2 = new Edge(2, 3);
        Edge edge3 = new Edge(3, 4);
        Edge edge4 = new Edge(4, 5);

        Edge edge5 = new Edge(6, 7);
        Edge edge6 = new Edge(7, 4);

        Tree tree1 = new Tree();
        tree1.addEdge(edge1);
        tree1.addEdge(edge2);
        tree1.addEdge(edge3);
        tree1.addEdge(edge4);

        Tree tree2 = new Tree();
        tree2.addEdge(edge5);
        tree2.addEdge(edge6);

        assertTrue(tree1.canMergeTree(tree2));
        List<Tree> trees = tree1.addTree(tree2);

        assertTrue(trees.get(0).containsEdge(edge5));

        assertTrue(trees.get(1).containsEdge(edge1));
        assertTrue(trees.get(1).containsEdge(edge2));
        assertTrue(trees.get(1).containsEdge(edge3));
        assertTrue(trees.get(1).hasCrossEdge(edge3));

        assertTrue(trees.get(2).containsEdge(edge6));
        assertTrue(trees.get(2).hasCrossEdge(edge6));

        assertTrue(trees.get(3).containsEdge(edge4));
    }

}