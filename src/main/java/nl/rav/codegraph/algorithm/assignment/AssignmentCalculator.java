package nl.rav.codegraph.algorithm.assignment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rene on 23-11-16.
 */
public class AssignmentCalculator {

    public void calculate(AssignmentDiagram diagram) {
        int curLevel = 0;
        while (true) {
            List unknowns = findUnknowns(diagram, curLevel);
            if (unknowns.isEmpty()) {
                break;
            }
        }
    }

    private List findUnknowns(AssignmentDiagram diagram, int curLevel) {
        List unknowns = new ArrayList<>();

        return unknowns;
    }
}
