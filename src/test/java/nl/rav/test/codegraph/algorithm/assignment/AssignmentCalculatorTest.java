package nl.rav.test.codegraph.algorithm.assignment;

import nl.rav.codegraph.algorithm.assignment.AssignmentCalculator;
import nl.rav.codegraph.algorithm.assignment.AssignmentDiagram;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by rene on 23-11-16.
 */
public class AssignmentCalculatorTest {

    @Test
    public void testCalculateNoDependencies() throws Exception {

        AssignmentDiagram diagram = createDiagramNoDependencies();

        AssignmentCalculator calculator = new AssignmentCalculator();
        calculator.calculate(diagram);

        assertThat(diagram.getLevels().size(), is(1));

    }

    private AssignmentDiagram createDiagramNoDependencies() {

        TestAssignmentNode node0x0 = new TestAssignmentNode("from0", new ArrayList<>(), new ArrayList<>());
        TestAssignmentNode node0x1 = new TestAssignmentNode("from1", new ArrayList<>(), new ArrayList<>());
        TestAssignmentNode node0x2 = new TestAssignmentNode("from2", new ArrayList<>(), new ArrayList<>());

        List<TestAssignmentNode> dependencies0 = new ArrayList<>();
        dependencies0.add(node0x0);
        dependencies0.add(node0x1);
        dependencies0.add(node0x2);

        TestAssignmentOrder level0 = new TestAssignmentOrder(0, dependencies0);

        List<TestAssignmentOrder> levels = new ArrayList<>();
        levels.add(level0);

        return new TestAssignmentDiagram(levels);
    }
}