package nl.rav.codegraph;

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
    Collection<Package> dataroot(HttpServletRequest request) throws IOException {
        String url = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String packagePath = RequestParser.parseToPackage(url, "data");
        return graphService.doData(packagePath);
    }

    @RequestMapping("**/arrows")
    List<Arrow> arrowsRoot(HttpServletRequest request) throws IOException {
        String url = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String packagePath = RequestParser.parseToPackage(url, "arrows");
        return graphService.doArrows(packagePath);
    }
}
