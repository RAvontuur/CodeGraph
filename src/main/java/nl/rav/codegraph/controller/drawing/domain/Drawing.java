package nl.rav.codegraph.controller.drawing.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rene on 4-12-16.
 */
public class Drawing {

    private Map<String, Rectangle> rectangles = new HashMap<>();
    private List<Arrow> arrows = new ArrayList<>();

    public Map<String, Rectangle> getRectangles() {
        return rectangles;
    }

    public List<Arrow> getArrows() {
        return arrows;
    }

    public void addRectangle(String name, int x, int y) {
        Rectangle rectangle = new Rectangle();
        rectangle.setName(name);
        rectangle.setX(x);
        rectangle.setY(y);
        rectangles.put(name, rectangle);
    }

    public void addArrow(int x1, int y1, int x2, int y2) {
        Arrow arrow = new Arrow(x1, y1, x2, y2);
        arrows.add(arrow);
    }
}
