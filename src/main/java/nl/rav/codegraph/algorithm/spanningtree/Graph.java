package nl.rav.codegraph.algorithm.spanningtree;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * a graph is a set of trees
 */
public class Graph {

    private Set<Tree> trees = new HashSet<>();

    public List<Tree> getSortedTrees() {
        return trees.stream()
                .sorted((o1, o2) -> (int) (o1.getRootEdge().getFromId() - o2.getRootEdge().getFromId()))
                .collect(Collectors.toList());
    }

    public void addEdge(Edge edge) {

        List<Tree> changedTrees = new ArrayList<>();
        changedTrees.add(new Tree(edge));

        while (!changedTrees.isEmpty()) {
            Tree mergeTree = trees.stream()
                    .filter(tree1 -> tree1.canAddTree(changedTrees.get(0)))
                    .findFirst().orElse(null);
            if (mergeTree != null) {
                mergeTree.addTree(changedTrees.get(0));
                changedTrees.remove(0);
                changedTrees.add(mergeTree);
            } else {
                Tree mergeableTree = trees.stream()
                        .filter(tree1 -> changedTrees.get(0).canAddTree(tree1))
                        .findFirst().orElse(null);
                if (mergeableTree != null) {
                    changedTrees.get(0).addTree(mergeableTree);
                    trees.add(changedTrees.get(0));
                    changedTrees.remove(0);
                    trees.remove(mergeableTree);
                } else {
                    trees.add(changedTrees.get(0));
                    changedTrees.remove(0);
                }
            }
        }
    }
}
