package nl.rav.codegraph.controller.drawing;

import nl.rav.codegraph.service.GraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
        return "view";
    }
}
