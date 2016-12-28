package nl.rav.codegraph.controller.drawing;

import nl.rav.codegraph.algorithm.spanningtree.Edge;
import nl.rav.codegraph.algorithm.spanningtree.Graph;
import nl.rav.codegraph.controller.drawing.domain.Drawing;
import nl.rav.codegraph.controller.drawing.domain.GraphConverter;
import nl.rav.codegraph.domain.Artifact;
import nl.rav.codegraph.domain.JavaPackage;
import nl.rav.codegraph.domain.PackageMap;
import nl.rav.codegraph.neo4j.service.ArtifactService;
import nl.rav.codegraph.neo4j.service.PackageService;
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

    private final PackageService packageService;
    private final ArtifactService artifactService;

    @Autowired
    public GraphController(PackageService packageService, ArtifactService artifactService) {
        this.packageService = packageService;
        this.artifactService = artifactService;
    }

    @RequestMapping("**/data")
    List<Object> dataroot(HttpServletRequest request) throws IOException {
        String url = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String fqn = RequestParser.parseToPackage(url, "data");

        Artifact artifact = artifactService.findArtifact(fqn, "jar");

        JavaPackage selectedPackage = packageService.findPackage(fqn);
        JavaPackage thisPackage = new JavaPackage(selectedPackage.getId(), ".", selectedPackage.getFqn());

        List<JavaPackage> childPackages = packageService.findChildren(fqn);

        PackageMap packageMap = new PackageMap();
        packageMap.addJavaPackage(thisPackage);
        childPackages.stream().forEach(javaPackage -> packageMap.addJavaPackage(javaPackage));

        List<Edge> edges = new ArrayList<>();

        List<JavaPackage> childDependents = packageService.findChildrenDependingOnParent(thisPackage.getFqn());
        for (JavaPackage childDependent : childDependents) {
            if (packageMap.getJavaPackage(childDependent.getId()) != null) {
                edges.add(new Edge(thisPackage.getId(), childDependent.getId()));
            }
        }

        for (JavaPackage child : childPackages) {
            JavaPackage parent = packageService.findParentPackageDependingOnChild(child.getFqn());
            if (parent != null) {
                edges.add(new Edge(child.getId(), parent.getId()));
            }
        }

        for (JavaPackage brother : childPackages) {
            List<JavaPackage> sisters = packageService.findSisterPackagesDependingOnBrother(brother.getFqn());
            for (JavaPackage sister : sisters) {
                edges.add(new Edge(brother.getId(), sister.getId()));
            }
        }

        Graph graph = new Graph();
        graph.addEdges(edges);

        GraphConverter converter = new GraphConverter();
        Drawing drawing = converter.createDrawing(graph, packageMap);

        List<Object> result = new ArrayList<>();
        //result.add(artifact);
        result.addAll(drawing.getRectangles().values());
        result.addAll(drawing.getArrows());
        return result;
    }
}
