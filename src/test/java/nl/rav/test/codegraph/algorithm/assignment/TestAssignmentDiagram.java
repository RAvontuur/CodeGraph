package nl.rav.test.codegraph.algorithm.assignment;

import nl.rav.codegraph.algorithm.spanningtree.AssignmentDiagram;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * The concept:
 * https://en.wikipedia.org/wiki/Depth-first_search#Output_of_a_depth-first_search
 * TODO: refactor such that this concept is expressed in a better way
 *
 *
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
