package nl.rav.codegraph.service;

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

    public Drawing createDrawingFor(String fqn) {

        List<PackageDependency> dependencies = generateDependencies(fqn);

        Drawing drawing = new Drawing();
        drawing.generate(dependencies);
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

            List<JavaPackage> afferents = packageService.findAfferents(brother.getFqn());
            for (JavaPackage afferent : afferents) {
                if (parentPackage.equalsOrContains(afferent) && !brother.equalsOrContains(afferent)) {
                    // ignore dependencies
                    // on other classes/interfaces outside parent-package
                    // on other classes/interfaces in its own package or its sub-packages

                    // assume for now: all dependencies are downwards
                    JavaPackage sister = findChildPackage(afferent, childPackages);
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
