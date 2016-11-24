package nl.rav.test.codegraph.algorithm.assignment;

import nl.rav.codegraph.algorithm.assignment.AssignmentNode;
import nl.rav.codegraph.algorithm.assignment.CycleDetector;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Created by rene on 24-11-16.
 */
public class CycleDetectorTest {

    @Test
    public void testDetectSimpleCycle() throws Exception {
        CycleDetector detector = new CycleDetector();

        List<AssignmentNode> nodes = createSimpleCycle();

        detector.resolveCycles(nodes);

        assertThat(nodes.get(0).getDownwards().size(), is(1));
        assertThat(nodes.get(0).getUpwards().size(), is(0));
        assertThat(nodes.get(1).getDownwards().size(), is(0));
        assertThat(nodes.get(1).getUpwards().size(), is(1));

        assertThat(nodes.get(0).getDownwards().get(0), is("B"));
        assertThat(nodes.get(1).getUpwards().get(0), is("A"));
    }

    private List<AssignmentNode> createSimpleCycle() {
        TestAssignmentNode node1 = new TestAssignmentNode("A", asList("B"),  new ArrayList<>());
        TestAssignmentNode node2 = new TestAssignmentNode("B", asList("A"),  new ArrayList<>());
        return Arrays.asList(node1, node2);
    }

    @Test
    public void testDetectDoubleCycle() throws Exception {
        CycleDetector detector = new CycleDetector();

        List<AssignmentNode> nodes = createDoubleCycle();

        detector.resolveCycles(nodes);

        assertThat(nodes.get(0).getDownwards().size(), is(2));
        assertThat(nodes.get(0).getUpwards().size(), is(0));
        assertThat(nodes.get(1).getDownwards().size(), is(0));
        assertThat(nodes.get(1).getUpwards().size(), is(1));
        assertThat(nodes.get(2).getDownwards().size(), is(0));
        assertThat(nodes.get(2).getUpwards().size(), is(1));

        assertThat(nodes.get(0).getDownwards().get(0), is("B"));
        assertThat(nodes.get(0).getDownwards().get(1), is("C"));
        assertThat(nodes.get(1).getUpwards().get(0), is("A"));
        assertThat(nodes.get(2).getUpwards().get(0), is("A"));
    }

    private List<AssignmentNode> createDoubleCycle() {
        TestAssignmentNode node1 = new TestAssignmentNode("A", asList("B", "C"),  new ArrayList<>());
        TestAssignmentNode node2 = new TestAssignmentNode("B", asList("A"),  new ArrayList<>());
        TestAssignmentNode node3 = new TestAssignmentNode("C", asList("A"),  new ArrayList<>());
        return Arrays.asList(node1, node2, node3);
    }


    @Test
    public void testDetectLongCycle() throws Exception {
        CycleDetector detector = new CycleDetector();

        List<AssignmentNode> nodes = createLongCycle();

        detector.resolveCycles(nodes);

        assertThat(nodes.get(0).getDownwards().size(), is(1));
        assertThat(nodes.get(0).getUpwards().size(), is(0));
        assertThat(nodes.get(1).getDownwards().size(), is(1));
        assertThat(nodes.get(1).getUpwards().size(), is(0));
        assertThat(nodes.get(2).getDownwards().size(), is(0));
        assertThat(nodes.get(2).getUpwards().size(), is(1));

        assertThat(nodes.get(0).getDownwards().get(0), is("B"));
        assertThat(nodes.get(1).getDownwards().get(0), is("C"));
        assertThat(nodes.get(2).getUpwards().get(0), is("A"));

    }

    private List<AssignmentNode> createLongCycle() {
        TestAssignmentNode node1 = new TestAssignmentNode("A", asList("B"), new ArrayList<>());
        TestAssignmentNode node2 = new TestAssignmentNode("B", asList("C"), new ArrayList<>());
        TestAssignmentNode node3 = new TestAssignmentNode("C", asList("A"), new ArrayList<>());
        return Arrays.asList(node1, node2, node3);
    }


    //this supports remove
    private static <String> List<String> asList(String... a) {
        List<String> list = new ArrayList<String>();
        for (String s: a) {
            list.add(s);
        }
        return list;
    }
}