package nl.rav.codegraph.algorithm.assignment;

import java.util.List;

/**
 * Created by rene on 23-11-16.
 */
public interface AssignmentDiagram<L extends AssignmentOrder> {
    List<L> getLevels();

    L getLevel(int index);
}
