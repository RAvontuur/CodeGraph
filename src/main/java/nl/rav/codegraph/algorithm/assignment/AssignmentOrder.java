package nl.rav.codegraph.algorithm.assignment;

import java.util.List;

/**
 * Created by rene on 23-11-16.
 */
public interface AssignmentOrder<D extends AssignmentNode> {
    int getY();
    List<D> getDependencies();
}
