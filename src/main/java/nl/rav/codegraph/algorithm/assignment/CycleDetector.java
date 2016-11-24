package nl.rav.codegraph.algorithm.assignment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rene on 24-11-16.
 */
public class CycleDetector<N extends AssignmentNode> {

    private List<N> analyzed = new ArrayList<>();

    public void resolveCycles(List<N> nodes) {
        if (nodes.isEmpty()) {
            return;
        }

        List<N> path = new ArrayList<>();
        traverse(nodes.get(0), nodes, path);
    }

    private void traverse(N node, List<N> nodes, List<N> path) {
        if (analyzed.contains(nodes)) {
            return;
        }

        path.add(node);

        int i = 0;
        while (i < node.getDownwards().size()) {
            N downwardsNode = findNode(node.getDownwards().get(i), nodes);
            if (path.contains(downwardsNode)) {
                //a cycle has been detected, resolve the cycle
                N lastNode = path.get(path.size() - 1);
                lastNode.getUpwards().add(lastNode.getDownwards().remove(i));
            } else {
                traverse(downwardsNode, nodes, path);
                i++;
            }
        }

        analyzed.add(node);
        path.remove(path.size() - 1);
    }

    private N findNode(Object o, List<N> nodes) {
        for (N node: nodes) {
            if (node.getFrom().equals(o)) {
                return node;
            }
        }
        return null;
    }

}
