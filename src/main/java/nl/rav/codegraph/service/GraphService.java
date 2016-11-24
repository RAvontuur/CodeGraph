package nl.rav.codegraph.service;

import nl.rav.codegraph.algorithm.assignment.CycleDetector;
import nl.rav.codegraph.algorithm.assignment.TopDownOrganizer;
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

        CycleDetector detector = new CycleDetector();
        detector.resolveCycles(dependencies);

        Drawing drawing = new Drawing();
        drawing.generate(dependencies);

        TopDownOrganizer calculator = new TopDownOrganizer();
        calculator.organize(drawing);

        return drawing;
    }

    private List<PackageDependency> generateDependencies(String fqn) {

        JavaPackage parentPackage = packageService.findPackage(fqn);
        List<JavaPackage> childPackages = packageService.findChildren(fqn);

        List<PackageDependency> dependencies = new ArrayList<>();

        for (JavaPackage brother : childPackages) {
            PackageDependency dependency = new PackageDependency();
            dependency.setFrom(brother);
            dependencies.add(dependency);

            List<JavaPackage> packagesDependingOn = packageService.findPackagesDependingOn(brother.getFqn());
            for (JavaPackage packageDependingOn : packagesDependingOn) {
                // ignore dependencies
                // on other classes/interfaces outside parent-package
                // on other classes/interfaces in its own package or its sub-packages
                if (parentPackage.equalsOrContains(packageDependingOn) && !brother.equalsOrContains(packageDependingOn)) {
                    JavaPackage sister = findChildPackage(packageDependingOn, childPackages);
                    // assume for now that all dependencies are downwards, later we will find the cycles
                    // outwards directed = downwards
                    dependency.addDownwards(sister);
                }
            }
        }

        return dependencies;
    }

    private JavaPackage findChildPackage(JavaPackage afferent, List<JavaPackage> childPackages) {
        for (JavaPackage aPackage : childPackages) {
            if (aPackage.equalsOrContains(afferent)) {
                return aPackage;
            }
        }

        return null;
    }


}
