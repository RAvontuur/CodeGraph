package nl.rav.codegraph.algorithm.spanningtree;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * a graph is a set of trees
 */
public class Graph {

    private Set<Tree> trees = new HashSet<>();
    private Set<Long> crossNodes = new HashSet<>();

    public List<Tree> getSortedTrees() {
        return trees.stream()
                .sorted((o1, o2) -> (int) (o1.getRootNode() - o2.getRootNode()))
                .collect(Collectors.toList());
    }

    public void addEdge(Edge edge) {
        Set<Tree> relevantTrees = trees.stream()
                .filter(tree1 -> !tree1.isIndependentEdge(edge))
                .collect(Collectors.toSet());

        if (relevantTrees.isEmpty()) {
            trees.add(new Tree(edge));
            return;
        }

        if (crossNodes.contains(edge.getFromId())) {
            Tree libTree = relevantTrees.stream()
                    .filter(tree -> tree.getRootNode() == edge.getFromId())
                    .findFirst().orElseThrow(() -> new IllegalStateException("libTree expected"));
            libTree.addEdge(edge);

            Tree commonTree = relevantTrees.stream()
                    .filter(tree -> tree.hasCommonDestination(edge))
                    .findFirst().orElse(null);
            if (commonTree != null) {
                crossNodes.add(edge.getToId());
                trees.add(new Tree(edge.getToId()));
            }
            return;
        }

        if (crossNodes.contains(edge.getToId())) {
            Tree existingTree = relevantTrees.stream()
                    .filter(tree -> tree.containsNode(edge.getFromId()))
                    .findFirst().orElse(null);
            if (existingTree != null) {
                existingTree.addEdge(edge);
            } else {
                trees.add(new Tree(edge));
            }
            return;
        }


        Tree commonTree = relevantTrees.stream()
                .filter(tree -> tree.hasCommonDestination(edge))
                .findFirst().orElse(null);
        if (commonTree != null) {
            crossNodes.add(edge.getToId());
            Tree existingTree = relevantTrees.stream()
                    .filter(tree -> tree.containsNode(edge.getFromId()))
                    .findFirst().orElse(null);
            if (existingTree != null) {
                existingTree.addEdge(edge);
            } else {
                trees.add(new Tree(edge));
            }
            Set<Edge> cycleEdges = commonTree.extractCycleEdges();
            Set<Edge> forwardEdges = commonTree.extractForwardEdges();

            Tree libTree = commonTree.splitTree(edge.getToId());
            trees.add(libTree);

            addEdges(cycleEdges);
            addEdges(forwardEdges);
            return;
        }


        if (relevantTrees.size() == 1) {
            relevantTrees.iterator().next().addEdge(edge);
            return;
        }

        if (relevantTrees.size() == 2) {
            Tree headTree = relevantTrees.stream()
                    .filter(tree -> tree.getRootNode() == edge.getToId())
                    .findFirst().orElseThrow(() -> new IllegalStateException("headTree expected"));
            Tree tailTree = relevantTrees.stream()
                    .filter(tree -> tree.getRootNode() != edge.getToId())
                    .findFirst().orElseThrow(() -> new IllegalStateException("tailTree expected"));
            headTree.addEdge(edge);
            headTree.merge(tailTree);
            trees.remove(tailTree);
            return;
        }

        //not handled
        throw new IllegalStateException("No handler for edge found: " + edge);
    }

    public void addEdges(Iterable<Edge> edges) {
        edges.spliterator().forEachRemaining(edge -> {
            addEdge(edge);
        });
    }

    public boolean hasCrossNode(long id) {
        return crossNodes.contains(id);
    }
}
