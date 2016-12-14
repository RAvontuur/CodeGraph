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
                .sorted((o1, o2) -> (int) (o1.getRootNode() - o2.getRootNode()))
                .collect(Collectors.toList());
    }

    public void addEdge(Edge edge) {

        List<Tree> changedTrees = new ArrayList<>();

        Tree singleEdgeTree = new Tree(edge);
        trees.stream().forEach(tree -> {
            if (tree.hasCrossNode(edge.getToId())) {
                singleEdgeTree.getCrossEdges().add(edge);
            }
        });

        changedTrees.add(singleEdgeTree);

        while (!changedTrees.isEmpty()) {
            Tree headTree = changedTrees.get(0);
            Tree firstMergeTree = trees.stream()
                    .filter(mergeTree -> mergeTree.canMergeTree(headTree))
                    .findFirst().orElse(null);
            if (firstMergeTree != null) {
                List<Tree> newChangedTrees = firstMergeTree.addTree(headTree);
                changedTrees.addAll(newChangedTrees);
                trees.remove(headTree);
            } else {
                trees.add(headTree);
            }
            changedTrees.remove(0);
        }
    }
}
