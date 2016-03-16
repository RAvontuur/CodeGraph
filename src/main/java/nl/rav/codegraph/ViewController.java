package nl.rav.codegraph;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @RequestMapping("/view")
    String view() throws IOException {
        graphService.doView("");
        return "view";
    }

    @RequestMapping("{sub1}/view")
    String data(@PathVariable String sub1) throws IOException {
        graphService.doView("." + sub1);
        return "view";
    }

    @RequestMapping("{sub1}/{sub2}/view")
    String data(@PathVariable String sub1, @PathVariable String sub2) throws IOException {
        graphService.doView("." + sub1 + "." + sub2);
        return "view";
    }

    @RequestMapping("{sub1}/{sub2}/{sub3}/view")
    String data(@PathVariable String sub1, @PathVariable String sub2, @PathVariable String sub3) throws IOException {
        graphService.doView("." + sub1 + "." + sub2 + "." + sub3);
        return "view";
    }

    @RequestMapping("{sub1}/{sub2}/{sub3}/{sub4}/view")
    String data(@PathVariable String sub1, @PathVariable String sub2, @PathVariable String sub3, @PathVariable String sub4) throws IOException {
        graphService.doView("." + sub1 + "." + sub2 + "." + sub3 + "." + sub4);
        return "view";
    }
}
