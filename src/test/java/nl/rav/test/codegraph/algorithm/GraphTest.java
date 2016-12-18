package nl.rav.test.codegraph.algorithm;

import nl.rav.codegraph.algorithm.spanningtree.Edge;
import nl.rav.codegraph.algorithm.spanningtree.Graph;
import nl.rav.codegraph.algorithm.spanningtree.Tree;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertTrue;

public class GraphTest {


    @Test
    public void simpleTest() {

        Graph graph = new Graph();

        Edge edge1 = new Edge(1, 2);
        Edge edge2 = new Edge(2, 3);
        Edge edge3 = new Edge(3, 4);

        graph.addEdge(edge1);
        graph.addEdge(edge2);
        graph.addEdge(edge3);

        assertThat(graph.getSortedTrees().size(), is(1));

        Tree tree1 = graph.getSortedTrees().get(0);

        assertThat(tree1.edgeSize(), is(3));
        assertTrue(tree1.containsEdge(edge1));
        assertTrue(tree1.containsEdge(edge2));
        assertTrue(tree1.containsEdge(edge3));
    }

    @Test
    public void simpleTest2() {

        Graph graph = new Graph();

        Edge edge1 = new Edge(1, 2);
        Edge edge2 = new Edge(3, 4);
        Edge edge3 = new Edge(2, 3);

        graph.addEdge(edge1);
        graph.addEdge(edge2);
        graph.addEdge(edge3);

        assertThat(graph.getSortedTrees().size(), is(1));

        Tree tree1 = graph.getSortedTrees().get(0);

        assertThat(tree1.edgeSize(), is(3));
        assertTrue(tree1.containsEdge(edge1));
        assertTrue(tree1.containsEdge(edge2));
        assertTrue(tree1.containsEdge(edge3));
    }

    @Test
    public void TestThreeTrees() {

        Graph graph = new Graph();

        Edge edge1 = new Edge(1, 2);
        Edge edge2 = new Edge(5, 6);
        Edge edge3 = new Edge(3, 4);

        graph.addEdge(edge1);
        graph.addEdge(edge2);
        graph.addEdge(edge3);

        assertThat(graph.getSortedTrees().size(), is(3));

        Tree tree1 = graph.getSortedTrees().get(0);
        assertThat(tree1.edgeSize(), is(1));
        assertTrue(tree1.containsEdge(edge1));

        Tree tree2 = graph.getSortedTrees().get(1);
        assertThat(tree2.edgeSize(), is(1));
        assertTrue(tree2.containsEdge(edge3));

        Tree tree3 = graph.getSortedTrees().get(2);
        assertThat(tree3.edgeSize(), is(1));
        assertTrue(tree3.containsEdge(edge2));
    }

    @Test
    public void TestLibraryTree() {

        Graph graph = new Graph();

        Edge edge1 = new Edge(1, 3);
        Edge edge2 = new Edge(2, 3);
        Edge edge3 = new Edge(3, 4);

        graph.addEdge(edge1);
        graph.addEdge(edge2);
        graph.addEdge(edge3);

        assertThat(graph.getSortedTrees().size(), is(3));
        assertTrue(graph.hasCrossNode(edge3.getFromId()));

        Tree tree1 = graph.getSortedTrees().get(0);
        assertThat(tree1.edgeSize(), is(1));
        assertTrue(tree1.containsEdge(edge1));

        Tree tree2 = graph.getSortedTrees().get(1);
        assertThat(tree2.edgeSize(), is(1));
        assertTrue(tree2.containsEdge(edge2));

        Tree tree3 = graph.getSortedTrees().get(2);
        assertThat(tree3.edgeSize(), is(1));
        assertTrue(tree3.containsEdge(edge3));
    }

    @Test
    public void TestLibraryTree3() {

        Graph graph = new Graph();

        Edge edge1 = new Edge(1, 4);
        Edge edge2 = new Edge(2, 4);
        Edge edge3 = new Edge(3, 4);

        graph.addEdge(edge1);
        graph.addEdge(edge2);
        graph.addEdge(edge3);

        assertThat(graph.getSortedTrees().size(), is(4));
        assertTrue(graph.hasCrossNode(4));

        Tree tree1 = graph.getSortedTrees().get(0);
        assertThat(tree1.edgeSize(), is(1));
        assertTrue(tree1.containsEdge(edge1));

        Tree tree2 = graph.getSortedTrees().get(1);
        assertThat(tree2.edgeSize(), is(1));
        assertTrue(tree2.containsEdge(edge2));

        Tree tree3 = graph.getSortedTrees().get(2);
        assertThat(tree3.edgeSize(), is(1));
        assertTrue(tree3.containsEdge(edge3));


        Tree tree4 = graph.getSortedTrees().get(3);
        assertThat(tree4.edgeSize(), is(0));
        assertThat(tree4.getRootNode(), is(4L));
    }

    @Test
    public void TestMultipleLibraryTree3() {

        Graph graph = new Graph();

        Edge edgeLib1 = new Edge(4, 6);
        Edge edgeLib2 = new Edge(5, 7);
        Edge edge1 = new Edge(1, 4);
        Edge edge2 = new Edge(2, 4);
        Edge edge3 = new Edge(3, 4);
        Edge edge4 = new Edge(1, 5);
        Edge edge5 = new Edge(2, 5);
        Edge edge6 = new Edge(3, 5);

        graph.addEdge(edgeLib1);
        graph.addEdge(edgeLib2);
        graph.addEdge(edge1);
        graph.addEdge(edge2);
        graph.addEdge(edge3);
        graph.addEdge(edge4);
        graph.addEdge(edge5);
        graph.addEdge(edge6);

        assertThat(graph.getSortedTrees().size(), is(5));
        assertTrue(graph.hasCrossNode(4));
        assertTrue(graph.hasCrossNode(5));

        Tree tree1 = graph.getSortedTrees().get(0);
        assertThat(tree1.edgeSize(), is(2));

        Tree tree2 = graph.getSortedTrees().get(1);
        assertThat(tree2.edgeSize(), is(2));

        Tree tree3 = graph.getSortedTrees().get(2);
        assertThat(tree3.edgeSize(), is(2));

        Tree libTree1 = graph.getSortedTrees().get(3);
        assertThat(libTree1.edgeSize(), is(1));
        assertTrue(libTree1.containsEdge(edgeLib1));

        Tree libTree2 = graph.getSortedTrees().get(4);
        assertThat(libTree2.edgeSize(), is(1));
        assertTrue(libTree2.containsEdge(edgeLib2));

    }

    @Test
    public void TestNestedLibrary() {

        Graph graph = new Graph();

        Edge edge1 = new Edge(1, 3);
        Edge edge2 = new Edge(2, 3);
        Edge edge3 = new Edge(3, 5);
        Edge edge4 = new Edge(4, 5);

        graph.addEdge(edge1);
        graph.addEdge(edge2);
        graph.addEdge(edge3);
        graph.addEdge(edge4);

        assertThat(graph.getSortedTrees().size(), is(5));
        assertTrue(graph.hasCrossNode(3));
        assertTrue(graph.hasCrossNode(5));

        Tree tree1 = graph.getSortedTrees().get(0);
        assertThat(tree1.edgeSize(), is(1));
        assertTrue(tree1.containsEdge(edge1));

        Tree tree2 = graph.getSortedTrees().get(1);
        assertThat(tree2.edgeSize(), is(1));
        assertTrue(tree2.containsEdge(edge2));

        Tree tree3 = graph.getSortedTrees().get(2);
        assertThat(tree3.edgeSize(), is(1));
        assertTrue(tree3.containsEdge(edge3));

        Tree tree4 = graph.getSortedTrees().get(3);
        assertThat(tree4.edgeSize(), is(1));
        assertTrue(tree4.containsEdge(edge4));

        Tree tree5 = graph.getSortedTrees().get(4);
        assertThat(tree5.edgeSize(), is(0));
        assertThat(tree5.getRootNode(), is(5L));

    }

    @Test
    public void TestNestedLibrary2() {

        Graph graph = new Graph();

        Edge edge1 = new Edge(1, 3);
        Edge edge2 = new Edge(2, 3);
        Edge edge3 = new Edge(3, 5);
        Edge edge4 = new Edge(4, 5);

        //add first edge 4
        graph.addEdge(edge4);
        graph.addEdge(edge1);
        graph.addEdge(edge2);
        graph.addEdge(edge3);

        assertThat(graph.getSortedTrees().size(), is(5));
        assertTrue(graph.hasCrossNode(3));
        assertTrue(graph.hasCrossNode(5));

        Tree tree1 = graph.getSortedTrees().get(0);
        assertThat(tree1.edgeSize(), is(1));
        assertTrue(tree1.containsEdge(edge1));

        Tree tree2 = graph.getSortedTrees().get(1);
        assertThat(tree2.edgeSize(), is(1));
        assertTrue(tree2.containsEdge(edge2));

        Tree tree3 = graph.getSortedTrees().get(2);
        assertThat(tree3.edgeSize(), is(1));
        assertTrue(tree3.containsEdge(edge3));

        Tree tree4 = graph.getSortedTrees().get(3);
        assertThat(tree4.edgeSize(), is(1));
        assertTrue(tree4.containsEdge(edge4));

        Tree tree5 = graph.getSortedTrees().get(4);
        assertThat(tree5.edgeSize(), is(0));
        assertThat(tree5.getRootNode(), is(5L));

    }

    @Test
    public void TestCycleToLibrary() {

        Graph graph = new Graph();

        Edge edge1 = new Edge(1, 3);
        Edge edge2 = new Edge(2, 3);
        Edge edge3 = new Edge(3, 4);
        Edge edge4 = new Edge(4, 5);
        Edge edge5 = new Edge(5, 3);

        graph.addEdge(edge1);
        graph.addEdge(edge2);
        graph.addEdge(edge3);
        graph.addEdge(edge4);
        graph.addEdge(edge5);

        assertThat(graph.getSortedTrees().size(), is(3));
        assertTrue(graph.hasCrossNode(3));

        Tree tree1 = graph.getSortedTrees().get(0);
        assertThat(tree1.edgeSize(), is(1));
        assertTrue(tree1.containsEdge(edge1));

        Tree tree2 = graph.getSortedTrees().get(1);
        assertThat(tree2.edgeSize(), is(1));
        assertTrue(tree2.containsEdge(edge2));

        Tree tree3 = graph.getSortedTrees().get(2);
        assertThat(tree3.edgeSize(), is(2));
        assertTrue(tree3.containsEdge(edge3));
        assertTrue(tree3.containsEdge(edge4));
        assertTrue(tree3.hasCycleEdge(edge5));
    }

    @Test
    public void TestCycleToLibrary2() {

        Graph graph = new Graph();

        Edge edge1 = new Edge(1, 3);
        Edge edge2 = new Edge(2, 3);
        Edge edge3 = new Edge(3, 4);
        Edge edge4 = new Edge(4, 5);
        Edge edge5 = new Edge(5, 3);

        graph.addEdge(edge1);
        graph.addEdge(edge3);
        graph.addEdge(edge4);
        graph.addEdge(edge5);
        // make the library to an existing cycle
        graph.addEdge(edge2);

        assertThat(graph.getSortedTrees().size(), is(3));
        assertTrue(graph.hasCrossNode(3));

        Tree tree1 = graph.getSortedTrees().get(0);
        assertThat(tree1.edgeSize(), is(1));
        assertTrue(tree1.containsEdge(edge1));

        Tree tree2 = graph.getSortedTrees().get(1);
        assertThat(tree2.edgeSize(), is(1));
        assertTrue(tree2.containsEdge(edge2));

        Tree tree3 = graph.getSortedTrees().get(2);
        assertThat(tree3.edgeSize(), is(2));
        assertTrue(tree3.containsEdge(edge3));
        assertTrue(tree3.containsEdge(edge4));
        assertTrue(tree3.hasCycleEdge(edge5));
    }


    @Test
    public void TestForwardToLibrary2() {

        Graph graph = new Graph();

        Edge edge1 = new Edge(1, 3);
        Edge edge2 = new Edge(2, 3);
        Edge edge3 = new Edge(3, 4);
        Edge edge4 = new Edge(4, 5);
        Edge edge5 = new Edge(3, 5);

        graph.addEdge(edge1);
        graph.addEdge(edge3);
        graph.addEdge(edge4);
        graph.addEdge(edge5);
        // make the library to an existing cycle
        graph.addEdge(edge2);

        assertThat(graph.getSortedTrees().size(), is(3));
        assertTrue(graph.hasCrossNode(3));

        Tree tree1 = graph.getSortedTrees().get(0);
        assertThat(tree1.edgeSize(), is(1));
        assertTrue(tree1.containsEdge(edge1));

        Tree tree2 = graph.getSortedTrees().get(1);
        assertThat(tree2.edgeSize(), is(1));
        assertTrue(tree2.containsEdge(edge2));

        Tree tree3 = graph.getSortedTrees().get(2);
        assertThat(tree3.edgeSize(), is(2));
        assertTrue(tree3.containsEdge(edge3));
        assertTrue(tree3.containsEdge(edge4));
        assertTrue(tree3.hasForwardEdge(edge5));
    }


}
