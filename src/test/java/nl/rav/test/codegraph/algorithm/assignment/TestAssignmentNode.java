package nl.rav.test.codegraph.algorithm.assignment;

import nl.rav.codegraph.algorithm.spanningtree.AssignmentNode;

import java.util.List;

/**
 * Created by rene on 23-11-16.
 */
public class TestAssignmentNode implements AssignmentNode<String> {


    private String from;
    private List<String> downwards;
    private List<String> upwards;

    public TestAssignmentNode(String from, List<String> downwards, List<String> upwards) {
        this.from = from;
        this.downwards = downwards;
        this.upwards = upwards;
    }

    @Override
    public String getFrom() {
        return from;
    }

    @Override
    public List<String> getDownwards() {
        return downwards;
    }

    @Override
    public List<String> getUpwards() {
        return upwards;
    }
}
