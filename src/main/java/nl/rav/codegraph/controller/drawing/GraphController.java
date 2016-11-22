package nl.rav.codegraph.controller.drawing;

import nl.rav.codegraph.controller.drawing.domain.Arrow;
import nl.rav.codegraph.controller.drawing.domain.Rectangle;
import nl.rav.codegraph.domain.Drawing;
import nl.rav.codegraph.domain.JavaPackage;
import nl.rav.codegraph.domain.Level;
import nl.rav.codegraph.domain.PackageDependency;
import nl.rav.codegraph.service.GraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rene on 9-3-16.
 */
@RestController
public class GraphController {

    private final GraphService graphService;

    @Autowired
    public GraphController(GraphService graphService) {
        this.graphService = graphService;
    }

    @RequestMapping("**/data")
    Collection<Rectangle> dataroot(HttpServletRequest request) throws IOException {
        String url = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String packagePath = RequestParser.parseToPackage(url, "data");
        return generateRectangles(packagePath);
    }

    @RequestMapping("**/arrows")
    List<Arrow> arrowsRoot(HttpServletRequest request) throws IOException {
        String url = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String packagePath = RequestParser.parseToPackage(url, "arrows");
        return generateArrows(packagePath);
    }

    private Map<String, Rectangle> generateRectangleMap(String fqn) {
        Map<String, Rectangle> rectangles = new HashMap<>();

        Drawing drawing = graphService.createDrawingFor(fqn);
        int y = 0;
        for (Level level: drawing.getLevels()) {
            int x = 0;
            for (PackageDependency aPackage : level.getDependencies()) {
                Rectangle rectangle = new Rectangle();
                String name = aPackage.getFrom().getName();
                rectangle.setName(name);
                rectangle.setX(x);
                rectangle.setY(y);
                rectangles.put(name, rectangle);
                x++;
            }
            y++;
        }
        return rectangles;
    }

    private Collection<Rectangle> generateRectangles(String fqn) {
        return generateRectangleMap(fqn).values();
    }

    private List<Arrow> generateArrows(String fqn) {
        List<Arrow> arrows = new ArrayList<>();

        Map<String, Rectangle> map = generateRectangleMap(fqn);

        Drawing drawing = graphService.createDrawingFor(fqn);
        for (Level level: drawing.getLevels()) {
            for (PackageDependency aPackage : level.getDependencies()) {
                Rectangle fromRectangle = map.get(aPackage.getFrom().getName());

                for (JavaPackage javaPackage: aPackage.getDownwards()) {
                    Rectangle toRectangle = map.get(javaPackage.getName());
                    Arrow arrow = new Arrow(
                            fromRectangle.getX(), fromRectangle.getY(),
                            toRectangle.getX(), toRectangle.getY());
                    arrows.add(arrow);
                }
                for (JavaPackage javaPackage: aPackage.getUpwards()) {
                    Rectangle toRectangle = map.get(javaPackage.getName());
                    Arrow arrow = new Arrow(
                            fromRectangle.getX(), fromRectangle.getY(),
                            toRectangle.getX(), toRectangle.getY());
                    arrows.add(arrow);
                }
            }
        }
        return arrows;
    }

}
