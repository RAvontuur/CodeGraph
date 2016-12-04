package nl.rav.codegraph.service;

import nl.rav.codegraph.algorithm.spanningtree.CycleDetector;
import nl.rav.codegraph.algorithm.spanningtree.Edge;
import nl.rav.codegraph.domain.Graph;
import nl.rav.codegraph.domain.JavaPackage;
import nl.rav.codegraph.neo4j.service.PackageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rene on 13-3-16.
 */
@Service
public class GraphService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final PackageService packageService;

    @Autowired
    public GraphService(PackageService packageService) {
        this.packageService = packageService;
    }

    public Graph createGraph(String fqn) {

        Graph graph = new Graph();

        List<JavaPackage> childPackages = packageService.findChildren(fqn);
        childPackages.stream().forEach(javaPackage -> graph.addJavaPackage(javaPackage));

        List<Edge> edges = new ArrayList<>();

        for (JavaPackage brother : childPackages) {
            List<JavaPackage> sisters = packageService.findPackagesDependingOn(brother.getFqn());
            for (JavaPackage sister : sisters) {
                edges.add(new Edge(brother.getId(), sister.getId()));
            }
        }

        CycleDetector detector = new CycleDetector(edges);
        detector.resolveCycles();

        graph.setEdges(edges);
        return graph;
    }
}
