package nl.rav.codegraph.algorithm.spanningtree;

/**
 * Created by rene on 23-11-16.
 */
public class TopDownOrganizer {

    public void organize(AssignmentDiagram diagram) {

        int curLevel = 0;
        while (levelExists(diagram, curLevel)) {
            while (findUnknowns(diagram, curLevel)) {
            }
            curLevel++;
        }
    }

    private boolean levelExists(AssignmentDiagram diagram, int curLevel) {
        return curLevel < diagram.getLevels().size();
    }

    private boolean findUnknowns(AssignmentDiagram<AssignmentOrder> diagram, int curLevelIndex) {

        AssignmentOrder<AssignmentNode> curLevel = diagram.getLevel(curLevelIndex);
        int maxLevelIndex = diagram.getLevels().size() - 1;

        for (AssignmentNode node : curLevel.getDependencies()) {
            for (Object object : node.getDownwards()) {
                for (int levelIndex = curLevelIndex; levelIndex <= maxLevelIndex; levelIndex++) {
                    if (hasNodeInLevel(diagram, levelIndex, object)) {
                        moveNodeDownwards(diagram, curLevelIndex, node);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void moveNodeDownwards(AssignmentDiagram<AssignmentOrder> diagram, int curLevelIndex, AssignmentNode node) {
        AssignmentOrder<AssignmentNode> curLevel = diagram.getLevel(curLevelIndex);
        AssignmentOrder<AssignmentNode> newLevel = diagram.getLevel(curLevelIndex+1);
        curLevel.getDependencies().remove(node);
        newLevel.getDependencies().add(node);
    }

    private boolean hasNodeInLevel(AssignmentDiagram<AssignmentOrder> diagram, int levelIndex, Object object) {
        AssignmentOrder<AssignmentNode> curLevel = diagram.getLevel(levelIndex);
        for (AssignmentNode node : curLevel.getDependencies()) {
            if (object.equals(node.getFrom())) {
                return true;
            }
        }
        return false;
    }
}
