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
        ;
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
        ;
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

    //TODO implement library tree support

}
