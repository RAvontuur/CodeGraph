package nl.rav.test.codegraph.algorithm.assignment;

import nl.rav.codegraph.algorithm.spanningtree.AssignmentOrder;

import java.util.List;

/**
 * Created by rene on 23-11-16.
 */
public class TestAssignmentOrder implements AssignmentOrder<TestAssignmentNode> {

    private int y;
    private List<TestAssignmentNode> dependencies;

    public TestAssignmentOrder(int y, List<TestAssignmentNode> dependencies) {
        this.y = y;
        this.dependencies = dependencies;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public List<TestAssignmentNode> getDependencies() {
        return dependencies;
    }
}
