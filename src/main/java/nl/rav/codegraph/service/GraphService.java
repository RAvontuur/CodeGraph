package nl.rav.codegraph.service;

import nl.rav.codegraph.algorithm.spanningtree.TopDownOrganizer;
import nl.rav.codegraph.controller.drawing.domain.Rectangle;
import nl.rav.codegraph.domain.Drawing;
import nl.rav.codegraph.domain.JavaPackage;
import nl.rav.codegraph.domain.PackageDependency;
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

    public Rectangle justToCreateACyclicDependencyInThisProject() {
        return new Rectangle();
    }

    public Drawing createDrawingFor(String fqn) {

        List<PackageDependency> dependencies = generateDependencies(fqn);

        //CycleDetector detector = new CycleDetector(edges);
        //detector.resolveCycles(dependencies);

        Drawing drawing = new Drawing();
        drawing.generate(dependencies);

        TopDownOrganizer calculator = new TopDownOrganizer();
        calculator.organize(drawing);

        return drawing;
    }

    private List<PackageDependency> generateDependencies(String fqn) {

        List<JavaPackage> childPackages = packageService.findChildren(fqn);
        List<PackageDependency> dependencies = new ArrayList<>();

        for (JavaPackage brother : childPackages) {
            PackageDependency dependency = new PackageDependency();
            dependency.setFrom(brother);
            dependencies.add(dependency);

            List<JavaPackage> sisters = packageService.findPackagesDependingOn(brother.getFqn());
            for (JavaPackage sister : sisters) {
                    dependency.addDownwards(sister);
            }
        }

        return dependencies;
    }
}
