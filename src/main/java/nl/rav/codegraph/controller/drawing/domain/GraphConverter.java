package nl.rav.codegraph.controller.drawing.domain;

import nl.rav.codegraph.algorithm.spanningtree.DepthFirstTraversal;
import nl.rav.codegraph.algorithm.spanningtree.Edge;
import nl.rav.codegraph.algorithm.spanningtree.Graph;
import nl.rav.codegraph.algorithm.spanningtree.Tree;
import nl.rav.codegraph.domain.JavaPackage;
import nl.rav.codegraph.domain.PackageMap;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rene on 4-12-16.
 */
public class GraphConverter {

    public Drawing createDrawing(Graph graph, PackageMap packageMap) {
        Drawing drawing = new Drawing();
        LevelMap levelMap = new LevelMap();

        graph.getSortedTrees().stream().forEach(tree -> {
            addTree(drawing, tree, packageMap, levelMap);
        });

        //add packages without dependendies
        packageMap.allJavaPackages().stream().forEach(javaPackage -> {
            Rectangle fromRectangle = drawing.getRectangles().get(javaPackage.getName());
            if (fromRectangle == null) {
                int y1 = 0;
                int x1 = levelMap.addToLevel(y1);
                drawing.addRectangle(javaPackage.getName(), x1, y1);
            }
        });

        graph.getSortedTrees().stream().forEach(tree -> {
            ArrowsBrowser arrowsBrowser = new ArrowsBrowser(tree, packageMap, drawing);
            arrowsBrowser.drawArrows();
        });

        return drawing;
    }

    public void addTree(Drawing drawing, Tree tree, PackageMap packageMap, LevelMap levelMap) {

        //add all packages with dependencies
        RectanglesBrowser rectanglesBrowser = new RectanglesBrowser(tree, packageMap, drawing, levelMap);
        rectanglesBrowser.traverse();
    }

    class LevelMap {
        private final Map<Integer, Integer> levelMap = new HashMap<>();

        private int addToLevel(int level) {
            Integer result = levelMap.get(level);
            if (result == null) {
                result = 1;
            } else {
                result++;
            }
            levelMap.put(level, result);
            return result;
        }

    }

    class RectanglesBrowser extends DepthFirstTraversal {
        private final PackageMap packageMap;
        private final Drawing drawing;
        private final LevelMap levelMap;

        public RectanglesBrowser(Tree tree, PackageMap packageMap, Drawing drawing, LevelMap levelMap) {
            super(tree);
            this.packageMap = packageMap;
            this.drawing = drawing;
            this.levelMap = levelMap;
        }


        @Override
        public boolean onVisitEdge(Edge edge, int depth) {

            JavaPackage from = packageMap.getJavaPackage(edge.getFromId());
            Rectangle fromRectangle = drawing.getRectangles().get(from.getName());
            if (fromRectangle == null) {
                int y1 = depth;
                int x1 = levelMap.addToLevel(y1);
                drawing.addRectangle(from.getName(), x1, y1);
            }

            JavaPackage to = packageMap.getJavaPackage(edge.getToId());
            Rectangle toRectangle = drawing.getRectangles().get(to.getName());
            if (toRectangle == null) {
                int y2 = depth + 1;
                int x2 = levelMap.addToLevel(y2);
                drawing.addRectangle(to.getName(), x2, y2);
            }

            return true;
        }
    }

    class ArrowsBrowser {
        private final Tree tree;
        private final PackageMap packageMap;
        private final Drawing drawing;

        public ArrowsBrowser(Tree tree, PackageMap packageMap, Drawing drawing) {
            this.tree = tree;
            this.packageMap = packageMap;
            this.drawing = drawing;
        }


        public void drawArrows() {
            tree.getEdges().stream()
                    .forEach(edge -> drawEdge(edge, "tree"));
            tree.getCycleEdges().stream()
                    .forEach(edge -> drawEdge(edge, "back"));
            tree.getForwardEdges().stream()
                    .forEach(edge -> drawEdge(edge, "forward"));
        }

        private void drawEdge(Edge edge, String edgeType) {
            JavaPackage from = packageMap.getJavaPackage(edge.getFromId());
            Rectangle fromRectangle = drawing.getRectangles().get(from.getName());
            JavaPackage to = packageMap.getJavaPackage(edge.getToId());
            Rectangle toRectangle = drawing.getRectangles().get(to.getName());

            drawing.addArrow(
                    fromRectangle.getX(), fromRectangle.getY(),
                    toRectangle.getX(), toRectangle.getY(),
                    edgeType
            );
        }
    }
}
