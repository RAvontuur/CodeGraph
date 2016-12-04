package nl.rav.codegraph.controller.drawing;

import nl.rav.codegraph.controller.drawing.domain.Arrow;
import nl.rav.codegraph.controller.drawing.domain.GraphConverter;
import nl.rav.codegraph.controller.drawing.domain.Rectangle;
import nl.rav.codegraph.domain.Graph;
import nl.rav.codegraph.service.GraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

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
        String fqn = RequestParser.parseToPackage(url, "data");
        Graph graph = graphService.createGraph(fqn);
        GraphConverter converter = new GraphConverter();
        return  converter.createDrawing(graph).getRectangles().values();
    }

    @RequestMapping("**/arrows")
    List<Arrow> arrowsRoot(HttpServletRequest request) throws IOException {
        String url = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String fqn = RequestParser.parseToPackage(url, "arrows");
        Graph graph = graphService.createGraph(fqn);
        GraphConverter converter = new GraphConverter();
        return converter.createDrawing(graph).getArrows();
    }
}
