package nl.rav.codegraph;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping("/data")
    Collection<Package> dataroot() throws IOException {
        return graphService.doData("");
    }

    @RequestMapping("{sub1}/data")
    Collection<Package> data(@PathVariable String sub1) throws IOException {
        return graphService.doData("." + sub1);
    }

    @RequestMapping("{sub1}/{sub2}/data")
    Collection<Package> data(@PathVariable String sub1, @PathVariable String sub2) throws IOException {
        return graphService.doData("." + sub1 + "." + sub2);
    }

    @RequestMapping("{sub1}/{sub2}/{sub3}/data")
    Collection<Package> data(@PathVariable String sub1, @PathVariable String sub2, @PathVariable String sub3) throws IOException {
        return graphService.doData("." + sub1 + "." + sub2 + "." + sub3);
    }

    @RequestMapping("{sub1}/{sub2}/{sub3}/{sub4}/data")
    Collection<Package> data(@PathVariable String sub1, @PathVariable String sub2, @PathVariable String sub3, @PathVariable String sub4) throws IOException {
        return graphService.doData("." + sub1 + "." + sub2 + "." + sub3 + "." + sub4);
    }


    @RequestMapping("/arrows")
    List<Arrow> arrowsRoot() throws IOException {
        return graphService.doArrows("");
    }

    @RequestMapping("{sub1}/arrows")
    List<Arrow> arrows(@PathVariable String sub1) throws IOException {
        return graphService.doArrows("." + sub1);
    }

    @RequestMapping("{sub1}/{sub2}/arrows")
    List<Arrow> arrows(@PathVariable String sub1, @PathVariable String sub2) throws IOException {
        return graphService.doArrows("." + sub1 + "." + sub2);
    }

    @RequestMapping("{sub1}/{sub2}/{sub3}/arrows")
    List<Arrow> arrows(@PathVariable String sub1, @PathVariable String sub2, @PathVariable String sub3) throws IOException {
        return graphService.doArrows("." + sub1 + "." + sub2 + "." + sub3);
    }

    @RequestMapping("{sub1}/{sub2}/{sub3}/{sub4}/arrows")
    List<Arrow> arrows(@PathVariable String sub1, @PathVariable String sub2, @PathVariable String sub3, @PathVariable String sub4) throws IOException {
        return graphService.doArrows("." + sub1 + "." + sub2 + "." + sub3 + "." + sub4);
    }
}
