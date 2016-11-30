package nl.rav.codegraph.algorithm.spanningtree;

import java.util.List;

/**
 * Created by rene on 23-11-16.
 */
public interface AssignmentDiagram<L extends AssignmentOrder> {
    List<L> getLevels();

    L getLevel(int index);
}
