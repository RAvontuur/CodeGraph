package nl.rav.codegraph.controller.drawing.domain;

import nl.rav.codegraph.algorithm.spanningtree.Edge;
import nl.rav.codegraph.algorithm.spanningtree.RootDetector;
import nl.rav.codegraph.domain.Graph;
import nl.rav.codegraph.domain.JavaPackage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rene on 4-12-16.
 */
public class GraphConverter {

    public Drawing createDrawing(Graph graph) {
        Drawing drawing = new Drawing();

        Browser browser = new Browser(graph, drawing);

        //add all packages with dependencies
        browser.traverseAllDepthFirst(browser.findRootEdges());

        //add packages without dependendies
        graph.allJavaPackages().stream().forEach(javaPackage -> {
            Rectangle fromRectangle = drawing.getRectangles().get(javaPackage.getName());
            if (fromRectangle == null) {
                int y1 = 0;
                int x1 = browser.addToLevel(y1);
                drawing.addRectangle(javaPackage.getName(), x1, y1);
            }
        });


        return drawing;
    }


    class Browser extends RootDetector {
        private final Graph graph;
        private final Drawing drawing;

        private final Map<Integer, Integer> levelMap = new HashMap<>();

        public Browser(Graph graph, Drawing drawing) {
            super(graph.getEdges());
            this.graph = graph;
            this.drawing = drawing;
        }

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

        @Override
        protected boolean onVisitEdge(Edge edge, Edge parentEdge, List<Edge> pathEdges) {

            JavaPackage from = graph.getJavaPackage(edge.getFromId());
            Rectangle fromRectangle = drawing.getRectangles().get(from.getName());
            if (fromRectangle == null) {
                int y1 = pathEdges.size();
                int x1 = addToLevel(y1);
                drawing.addRectangle(from.getName(), x1, y1);
            }

            JavaPackage to = graph.getJavaPackage(edge.getToId());
            Rectangle toRectangle = drawing.getRectangles().get(to.getName());
            if (toRectangle == null) {
                int y2 = pathEdges.size();
                int x2 = addToLevel(y2);
                drawing.addRectangle(to.getName(), x2, y2);
            }

            //TODO arrows

            return true;
        }
    }

}
