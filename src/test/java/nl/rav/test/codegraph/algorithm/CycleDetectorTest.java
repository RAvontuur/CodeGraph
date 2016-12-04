package nl.rav.test.codegraph.algorithm;

import nl.rav.codegraph.algorithm.spanningtree.CycleDetector;
import nl.rav.codegraph.algorithm.spanningtree.Edge;
import nl.rav.codegraph.algorithm.spanningtree.EdgeType;
import nl.rav.codegraph.algorithm.spanningtree.RootDetector;
import org.hamcrest.core.Is;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;

/**
 * testing Spanning Tree analyses
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
        List<Edge> edges = Arrays.asList(edge1, edge2);

        CycleDetector detector = new CycleDetector(edges);
        detector.resolveCycles();

        assertThat(edge1.getEdgeType(), Is.is(EdgeType.TREE));
        assertThat(edge2.getEdgeType(), is(EdgeType.TREE));

        List<Edge> parents = detector.findParentEdges(edge1);
        assertThat(parents.size(), is(0));

        RootDetector rootDetector = new RootDetector(edges);
        List<Edge> roots =  rootDetector.findRootEdges();
        assertThat(roots, hasItems(edge1));

        assertThat(detector.distanceToRoot(edge1), is(0));
        assertThat(detector.distanceToRoot(edge2), is(1));
    }

    @Test
    public void testDetectCycle() throws Exception {

        Edge edge1 = new Edge(1, 2);
        Edge edge2 = new Edge(2, 1);
        List<Edge> edges = Arrays.asList(edge1, edge2);

        CycleDetector detector = new CycleDetector(edges);
        detector.resolveCycles();

        assertThat(edge1.getEdgeType(), is(EdgeType.TREE));
        assertThat(edge2.getEdgeType(), is(EdgeType.BACK));

        RootDetector rootDetector = new RootDetector(edges);
        List<Edge> roots = rootDetector.findRootEdges();
        assertThat(roots, hasItems(edge1));

        assertThat(detector.distanceToRoot(edge1), is(0));
        assertThat(detector.distanceToRoot(edge2), is(1));
    }

    @Test
    public void testDetectCross() throws Exception {

        Edge edge1 = new Edge(1, 3);
        Edge edge2 = new Edge(2, 3);
        List<Edge> edges = Arrays.asList(edge1, edge2);

        CycleDetector detector = new CycleDetector(edges);
        detector.resolveCycles();

        assertThat(edge1.getEdgeType(), is(EdgeType.TREE));
        assertThat(edge2.getEdgeType(), is(EdgeType.CROSS));

        assertThat(detector.distanceToRoot(edge1), is(0));
        assertThat(detector.distanceToRoot(edge2), is(0));
    }

    @Test
    public void testDetectForward() throws Exception {

        Edge edge1 = new Edge(1, 3);
        Edge edge2 = new Edge(1, 2);
        Edge edge3 = new Edge(2, 3);
        Edge edge4 = new Edge(3, 4);

        List<Edge> edges = Arrays.asList(edge1, edge2, edge3, edge4);

        CycleDetector detector = new CycleDetector(edges);
        detector.resolveCycles();

        assertThat(edge1.getEdgeType(), is(EdgeType.FORWARD));
        assertThat(edge2.getEdgeType(), is(EdgeType.TREE));
        assertThat(edge3.getEdgeType(), is(EdgeType.TREE));
        assertThat(edge4.getEdgeType(), is(EdgeType.TREE));

        assertThat(detector.distanceToRoot(edge1), is(0));
        assertThat(detector.distanceToRoot(edge2), is(0));
        assertThat(detector.distanceToRoot(edge3), is(1));
        assertThat(detector.distanceToRoot(edge4), is(2));
    }


    @Test
    public void testDetectCrossLongTree() throws Exception {

        Edge edge1 = new Edge(1, 2);
        Edge edge2 = new Edge(2, 3);
        Edge edge3 = new Edge(4, 5);
        Edge edge4 = new Edge(5, 6);
        Edge edge5 = new Edge(5, 3);
        List<Edge> edges = Arrays.asList(edge1, edge2, edge3, edge4, edge5);

        CycleDetector detector = new CycleDetector(edges);
        detector.resolveCycles();

        assertThat(edge1.getEdgeType(), is(EdgeType.TREE));
        assertThat(edge2.getEdgeType(), is(EdgeType.TREE));
        assertThat(edge3.getEdgeType(), is(EdgeType.TREE));
        assertThat(edge4.getEdgeType(), is(EdgeType.TREE));
        assertThat(edge5.getEdgeType(), is(EdgeType.CROSS));
    }

    @Test
    public void testDetectTwoIndependentSimpleCycle() throws Exception {

        Edge edge1 = new Edge(1, 2);
        Edge edge2 = new Edge(2, 1);
        Edge edge3 = new Edge(3, 4);
        Edge edge4 = new Edge(4, 3);
        List<Edge> edges = Arrays.asList(edge1, edge2, edge3, edge4);

        CycleDetector detector = new CycleDetector(edges);
        detector.resolveCycles();

        assertThat(edge1.getEdgeType(), is(EdgeType.TREE));
        assertThat(edge2.getEdgeType(), is(EdgeType.BACK));
        assertThat(edge3.getEdgeType(), is(EdgeType.TREE));
        assertThat(edge4.getEdgeType(), is(EdgeType.BACK));

        RootDetector rootDetector = new RootDetector(edges);
        List<Edge> roots = rootDetector.findRootEdges();
        assertThat(roots, hasItems(edge1, edge3));
    }

    @Test
    public void testDetectLongCycle() throws Exception {

        Edge edge1 = new Edge(1, 2);
        Edge edge2 = new Edge(2, 3);
        Edge edge3 = new Edge(3, 4);
        Edge edge4 = new Edge(4, 1);
        List<Edge> edges = Arrays.asList(edge1, edge2, edge3, edge4);

        CycleDetector detector = new CycleDetector(edges);
        detector.resolveCycles();

        assertThat(edge1.getEdgeType(), is(EdgeType.TREE));
        assertThat(edge2.getEdgeType(), is(EdgeType.TREE));
        assertThat(edge3.getEdgeType(), is(EdgeType.TREE));
        assertThat(edge4.getEdgeType(), is(EdgeType.BACK));

        RootDetector rootDetector = new RootDetector(edges);
        List<Edge> roots = rootDetector.findRootEdges();
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
        List<Edge> edges = Arrays.asList(edge1, edge2, edge3, edge4, edge5, edge6);

        CycleDetector detector = new CycleDetector(edges);
        detector.resolveCycles();

        assertThat(edge1.getEdgeType(), is(EdgeType.TREE));
        assertThat(edge2.getEdgeType(), is(EdgeType.TREE));
        assertThat(edge3.getEdgeType(), is(EdgeType.TREE));
        assertThat(edge4.getEdgeType(), is(EdgeType.TREE));
        assertThat(edge5.getEdgeType(), is(EdgeType.BACK));
        assertThat(edge6.getEdgeType(), is(EdgeType.TREE));

        RootDetector rootDetector = new RootDetector(edges);
        List<Edge> roots = rootDetector.findRootEdges();
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
        List<Edge> edges = Arrays.asList(edge1, edge2, edge3, edge4, edge5, edge6);

        CycleDetector detector = new CycleDetector(edges);
        detector.resolveCycles();

        assertThat(edge1.getEdgeType(), is(EdgeType.TREE));
        assertThat(edge2.getEdgeType(), is(EdgeType.FORWARD));
        assertThat(edge3.getEdgeType(), is(EdgeType.TREE));
        assertThat(edge4.getEdgeType(), is(EdgeType.BACK));
        assertThat(edge5.getEdgeType(), is(EdgeType.BACK));
        assertThat(edge6.getEdgeType(), is(EdgeType.BACK));

        RootDetector rootDetector = new RootDetector(edges);
        List<Edge> roots = rootDetector.findRootEdges();
        assertThat(roots, hasItems(edge1));
    }

    @Test
    public void testDetectThreeCycles_v2() throws Exception {

        Edge edge1 = new Edge(2, 1);
        Edge edge2 = new Edge(3, 1);
        Edge edge3 = new Edge(4, 3);
        Edge edge4 = new Edge(1, 2);
        Edge edge5 = new Edge(1, 3);
        Edge edge6 = new Edge(1, 4);
        List<Edge> edges = Arrays.asList(edge1, edge2, edge3, edge4, edge5, edge6 );

        CycleDetector detector = new CycleDetector(edges);
        detector.resolveCycles();

        assertThat(edge1.getEdgeType(), is(EdgeType.FORWARD));
        assertThat(edge2.getEdgeType(), is(EdgeType.BACK));
        assertThat(edge3.getEdgeType(), is(EdgeType.BACK));
        assertThat(edge4.getEdgeType(), is(EdgeType.BACK));
        assertThat(edge5.getEdgeType(), is(EdgeType.FORWARD));
        assertThat(edge6.getEdgeType(), is(EdgeType.TREE));

        List<Edge> parents = detector.findParentEdges(edge2);
        assertThat(parents.size(), is(2));
        assertThat(parents.get(0), is(edge3));
        assertThat(parents.get(1), is(edge5));

        RootDetector rootDetector = new RootDetector(edges);
        List<Edge> roots = rootDetector.findRootEdges();
        assertThat(roots, hasItems(edge1));
    }


    @Test
    public void testFindNextEdgeSimpleTree() {

        Edge edge0 = new Edge(0, 1);
        Edge edge1 = new Edge(1, 2);
        Edge edge2 = new Edge(1, 3);
        List<Edge> edges = Arrays.asList(edge1, edge2);

        CycleDetector detector = new CycleDetector(edges);

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