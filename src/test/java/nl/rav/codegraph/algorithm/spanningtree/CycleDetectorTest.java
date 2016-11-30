package nl.rav.codegraph.algorithm.spanningtree;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;

/**
 * Created by rene on 24-11-16.
 */
public class CycleDetectorTest {

    @Test
    public void testDetectNothing() throws Exception {
        CycleDetector detector = new CycleDetector(Arrays.asList());
        detector.resolveCycles();
    }

    @Test
    public void testDetectNoCycle() throws Exception {

        Edge edge1 = new Edge(1, 2);
        Edge edge2 = new Edge(2, 3);

        CycleDetector detector = new CycleDetector(Arrays.asList(edge1, edge2));
        detector.resolveCycles();

        assertThat(edge1.getEdgeType(), is(EdgeType.TREE));
        assertThat(edge2.getEdgeType(), is(EdgeType.TREE));

        List<Edge> parents = detector.findParentEdges(edge1);
        assertThat(parents.size(), is(0));


        List<Edge> roots = detector.findRootEdges();
        assertThat(roots, hasItems(edge1));
    }


    @Test
    public void testDetectSimpleCycle() throws Exception {

        Edge edge1 = new Edge(1, 2);
        Edge edge2 = new Edge(2, 1);

        CycleDetector detector = new CycleDetector(Arrays.asList(edge1, edge2));
        detector.resolveCycles();

        assertThat(edge1.getEdgeType(), is(EdgeType.TREE));
        assertThat(edge2.getEdgeType(), is(EdgeType.BACK));

        List<Edge> roots = detector.findRootEdges();
        assertThat(roots, hasItems(edge1));
    }

    @Test
    public void testDetectTwoIndependentSimpleCycle() throws Exception {

        Edge edge1 = new Edge(1, 2);
        Edge edge2 = new Edge(2, 1);
        Edge edge3 = new Edge(3, 4);
        Edge edge4 = new Edge(4, 3);

        CycleDetector detector = new CycleDetector(Arrays.asList(edge1, edge2, edge3, edge4));
        detector.resolveCycles();

        assertThat(edge1.getEdgeType(), is(EdgeType.TREE));
        assertThat(edge2.getEdgeType(), is(EdgeType.BACK));
        assertThat(edge3.getEdgeType(), is(EdgeType.TREE));
        assertThat(edge4.getEdgeType(), is(EdgeType.BACK));

        List<Edge> roots = detector.findRootEdges();
        assertThat(roots, hasItems(edge1, edge3));
    }

    @Test
    public void testDetectLongCycle() throws Exception {

        Edge edge1 = new Edge(1, 2);
        Edge edge2 = new Edge(2, 3);
        Edge edge3 = new Edge(3, 4);
        Edge edge4 = new Edge(4, 1);

        CycleDetector detector = new CycleDetector(Arrays.asList(edge1, edge2, edge3, edge4));
        detector.resolveCycles();

        assertThat(edge1.getEdgeType(), is(EdgeType.TREE));
        assertThat(edge2.getEdgeType(), is(EdgeType.TREE));
        assertThat(edge3.getEdgeType(), is(EdgeType.TREE));
        assertThat(edge4.getEdgeType(), is(EdgeType.BACK));

        List<Edge> roots = detector.findRootEdges();
        assertThat(roots, hasItems(edge1));
    }

    @Test
    public void testDetectOneCycle() throws Exception {

        Edge edge1 = new Edge(1, 2);
        Edge edge2 = new Edge(1, 3);
        Edge edge3 = new Edge(1, 4);
        Edge edge4 = new Edge(2, 5);
        Edge edge5 = new Edge(3, 1);
        Edge edge6 = new Edge(4, 6);

        CycleDetector detector = new CycleDetector(Arrays.asList(edge1, edge2, edge3, edge4, edge5, edge6));
        detector.resolveCycles();

        assertThat(edge1.getEdgeType(), is(EdgeType.TREE));
        assertThat(edge2.getEdgeType(), is(EdgeType.TREE));
        assertThat(edge3.getEdgeType(), is(EdgeType.TREE));
        assertThat(edge4.getEdgeType(), is(EdgeType.TREE));
        assertThat(edge5.getEdgeType(), is(EdgeType.BACK));
        assertThat(edge6.getEdgeType(), is(EdgeType.TREE));

        List<Edge> roots = detector.findRootEdges();
        assertThat(roots, hasItems(edge1));
    }

    @Test
    public void testDetectThreeCycles() throws Exception {

        Edge edge1 = new Edge(1, 2);
        Edge edge2 = new Edge(1, 3);
        Edge edge3 = new Edge(1, 4);
        Edge edge4 = new Edge(2, 1);
        Edge edge5 = new Edge(3, 1);
        Edge edge6 = new Edge(4, 3);

        CycleDetector detector = new CycleDetector(Arrays.asList(edge1, edge2, edge3, edge4, edge5, edge6));
        detector.resolveCycles();

        assertThat(edge1.getEdgeType(), is(EdgeType.TREE));
        assertThat(edge2.getEdgeType(), is(EdgeType.TREE));
        assertThat(edge3.getEdgeType(), is(EdgeType.TREE));
        assertThat(edge4.getEdgeType(), is(EdgeType.BACK));
        assertThat(edge5.getEdgeType(), is(EdgeType.BACK));
        assertThat(edge6.getEdgeType(), is(EdgeType.BACK));

        List<Edge> roots = detector.findRootEdges();
        assertThat(roots, hasItems(edge1));
    }

    @Test
    public void testDetectThreeCycles_v2() throws Exception {

        Edge edge1 = new Edge(1, 2);
        Edge edge2 = new Edge(1, 3);
        Edge edge3 = new Edge(1, 4);
        Edge edge4 = new Edge(2, 1);
        Edge edge5 = new Edge(3, 1);
        Edge edge6 = new Edge(4, 3);

        //different order
        CycleDetector detector = new CycleDetector(Arrays.asList(edge4, edge5, edge6, edge1, edge2, edge3));
        detector.resolveCycles();

        assertThat(edge1.getEdgeType(), is(EdgeType.BACK));
        assertThat(edge2.getEdgeType(), is(EdgeType.TREE));
        assertThat(edge3.getEdgeType(), is(EdgeType.TREE));
        assertThat(edge4.getEdgeType(), is(EdgeType.TREE));
        assertThat(edge5.getEdgeType(), is(EdgeType.BACK));
        assertThat(edge6.getEdgeType(), is(EdgeType.BACK));

        List<Edge> parents = detector.findParentEdges(edge1);
        assertThat(parents.size(), is(2));
        assertThat(parents.get(0), is(edge4));
        assertThat(parents.get(1), is(edge5));

        List<Edge> roots = detector.findRootEdges();
        assertThat(roots, hasItems(edge4));
    }


    @Test
    public void testFindNextEdgeSimpleTree() {

        Edge edge0 = new Edge(0, 1);
        Edge edge1 = new Edge(1, 2);
        Edge edge2 = new Edge(1, 3);

        CycleDetector detector = new CycleDetector(Arrays.asList(edge0, edge1, edge2));

        Edge edgeFound = detector.findNextEdge(new Edge(0, 1));
        assertThat(edgeFound, is(edge1));

        detector.addToAnalyzed(edgeFound);
        edgeFound = detector.findNextEdge(new Edge(0, 1));
        assertThat(edgeFound, is(edge2));

        detector.addToAnalyzed(edgeFound);
        edgeFound = detector.findNextEdge(new Edge(0, 1));
        assertThat(edgeFound, is(nullValue()));
    }

    @Test
    public void testFindNextEdgeNoEntries() {

        CycleDetector detector = new CycleDetector(Arrays.asList());

        Edge edgeFound = detector.findNextEdge(new Edge(0, 1));
        assertThat(edgeFound, is(nullValue()));

    }
}