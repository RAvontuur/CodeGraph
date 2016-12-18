package nl.rav.codegraph.controller.drawing;

import nl.rav.codegraph.algorithm.spanningtree.Edge;
import nl.rav.codegraph.algorithm.spanningtree.Graph;
import nl.rav.codegraph.controller.drawing.domain.Drawing;
import nl.rav.codegraph.controller.drawing.domain.GraphConverter;
import nl.rav.codegraph.domain.JavaPackage;
import nl.rav.codegraph.domain.PackageMap;
import nl.rav.codegraph.neo4j.service.PackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Rest-service returning objects to be drawn by d3.js
 */
@RestController
public class GraphController {

    private final PackageService packageService;

    @Autowired
    public GraphController(PackageService packageService) {
        this.packageService = packageService;
    }

    @RequestMapping("**/data")
    List<Object> dataroot(HttpServletRequest request) throws IOException {
        String url = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String fqn = RequestParser.parseToPackage(url, "data");

        PackageMap packageMap = new PackageMap();

        List<JavaPackage> childPackages = packageService.findChildren(fqn);
        childPackages.stream().forEach(javaPackage -> packageMap.addJavaPackage(javaPackage));

        List<Edge> edges = new ArrayList<>();
        Set<Long> nodes = new HashSet<>();

        for (JavaPackage brother : childPackages) {
            List<JavaPackage> sisters = packageService.findPackagesDependingOn(brother.getFqn());
            for (JavaPackage sister : sisters) {
                edges.add(new Edge(brother.getId(), sister.getId()));
            }
            nodes.add(brother.getId());
        }

        Graph graph = new Graph();
        graph.addEdges(edges);

        GraphConverter converter = new GraphConverter();
        Drawing drawing = converter.createDrawing(graph, packageMap);

        List<Object> result = new ArrayList<>();
        result.addAll(drawing.getRectangles().values());
        result.addAll(drawing.getArrows());
        return result;
    }
}
