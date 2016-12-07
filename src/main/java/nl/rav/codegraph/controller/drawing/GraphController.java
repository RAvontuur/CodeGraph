package nl.rav.codegraph.controller.drawing;

import nl.rav.codegraph.controller.drawing.domain.Drawing;
import nl.rav.codegraph.controller.drawing.domain.GraphConverter;
import nl.rav.codegraph.domain.Graph;
import nl.rav.codegraph.service.GraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Rest-service returning objects to be drawn by d3.js
 */
@RestController
public class GraphController {

    private final GraphService graphService;

    @Autowired
    public GraphController(GraphService graphService) {
        this.graphService = graphService;
    }

    @RequestMapping("**/data")
    List<Object> dataroot(HttpServletRequest request) throws IOException {
        String url = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String fqn = RequestParser.parseToPackage(url, "data");
        Graph graph = graphService.createGraph(fqn);
        GraphConverter converter = new GraphConverter();
        Drawing drawing = converter.createDrawing(graph);

        List<Object> result = new ArrayList<>();
        result.addAll(drawing.getRectangles().values());
        result.addAll(drawing.getArrows());
        return result;
    }
}
