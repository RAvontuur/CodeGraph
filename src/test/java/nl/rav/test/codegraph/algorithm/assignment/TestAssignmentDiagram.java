package nl.rav.test.codegraph.algorithm.assignment;

import nl.rav.codegraph.algorithm.assignment.AssignmentDiagram;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rene on 23-11-16.
 */
public class TestAssignmentDiagram implements AssignmentDiagram<TestAssignmentOrder> {

    private List<TestAssignmentOrder> levels;

    public TestAssignmentDiagram(List<TestAssignmentOrder> levels) {
        this.levels = levels;
    }

    @Override
    public List<TestAssignmentOrder> getLevels() {
        return levels;
    }

    @Override
    public TestAssignmentOrder getLevel(int index) {
        if (levels.size() <= index) {
            levels.add(new TestAssignmentOrder(index, new ArrayList<TestAssignmentNode>()));
        }
        return levels.get(index);
    }
}
