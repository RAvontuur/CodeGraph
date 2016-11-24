package nl.rav.test.codegraph.algorithm.assignment;

import nl.rav.codegraph.algorithm.assignment.TopDownOrganizer;
import nl.rav.codegraph.algorithm.assignment.AssignmentDiagram;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by rene on 23-11-16.
 */
public class TopDownOrganizerTest {

    @Test
    public void testCalculateNoDependencies() throws Exception {

        AssignmentDiagram diagram = createDiagramNoDependencies();

        TopDownOrganizer calculator = new TopDownOrganizer();
        calculator.organize(diagram);

        assertThat(diagram.getLevels().size(), is(1));
        TestAssignmentNode obj0 = (TestAssignmentNode) diagram.getLevel(0).getDependencies().get(0);
        TestAssignmentNode obj1 = (TestAssignmentNode) diagram.getLevel(0).getDependencies().get(1);
        TestAssignmentNode obj2 = (TestAssignmentNode) diagram.getLevel(0).getDependencies().get(2);
        assertThat(obj0.getFrom(), is("obj-0"));
        assertThat(obj1.getFrom(), is("obj-1"));
        assertThat(obj2.getFrom(), is("obj-2"));
    }

    private AssignmentDiagram createDiagramNoDependencies() {

        TestAssignmentNode node0x0 = new TestAssignmentNode("obj-0", new ArrayList<>(), new ArrayList<>());
        TestAssignmentNode node0x1 = new TestAssignmentNode("obj-1", new ArrayList<>(), new ArrayList<>());
        TestAssignmentNode node0x2 = new TestAssignmentNode("obj-2", new ArrayList<>(), new ArrayList<>());

        List<TestAssignmentNode> dependencies0 = new ArrayList<>();
        dependencies0.add(node0x0);
        dependencies0.add(node0x1);
        dependencies0.add(node0x2);

        TestAssignmentOrder level0 = new TestAssignmentOrder(0, dependencies0);

        List<TestAssignmentOrder> levels = new ArrayList<>();
        levels.add(level0);

        return new TestAssignmentDiagram(levels);
    }

    @Test
    public void testCalculateOneDependency() throws Exception {

        AssignmentDiagram diagram = createDiagramOneDependency();

        TopDownOrganizer calculator = new TopDownOrganizer();
        calculator.organize(diagram);

        assertThat(diagram.getLevels().size(), is(2));
        TestAssignmentNode obj0 = (TestAssignmentNode) diagram.getLevel(0).getDependencies().get(0);
        TestAssignmentNode obj1 = (TestAssignmentNode) diagram.getLevel(1).getDependencies().get(0);

        assertThat(obj0.getFrom(), is("obj-0"));
        assertThat(obj1.getFrom(), is("obj-1"));
        assertThat(obj1.getDownwards().get(0), is("obj-0"));
    }

    private AssignmentDiagram createDiagramOneDependency() {

        TestAssignmentNode node0x0 = new TestAssignmentNode("obj-0", new ArrayList<>(), new ArrayList<>());
        TestAssignmentNode node0x1 = new TestAssignmentNode("obj-1", Arrays.asList("obj-0"), new ArrayList<>());

        List<TestAssignmentNode> dependencies0 = new ArrayList<>();
        dependencies0.add(node0x0);
        dependencies0.add(node0x1);

        TestAssignmentOrder level0 = new TestAssignmentOrder(0, dependencies0);

        List<TestAssignmentOrder> levels = new ArrayList<>();
        levels.add(level0);

        return new TestAssignmentDiagram(levels);
    }
}