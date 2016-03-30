package nl.rav.codegraph;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by rene on 13-3-16.
 */
@Controller
public class ViewController {

    private final GraphService graphService;

    @Autowired
    public ViewController(GraphService graphService) {
        this.graphService = graphService;
    }

    @RequestMapping("**/view")
    public String view(HttpServletRequest request) throws IOException {
        String url = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);

        String packagePath = RequestParser.parseToPackage(url, "view");
        graphService.doView(packagePath);
        return "view";
    }
}
