package nl.rav.test.codegraph.service;

import nl.rav.codegraph.neo4j.configuration.PersistenceContext;
import nl.rav.codegraph.neo4j.domain.PackageEntity;
import nl.rav.codegraph.neo4j.repository.PackageRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Iterator;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by rene on 18-11-16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({
        @ContextConfiguration(classes = {PersistenceContext.class}),
})
public class PackageRepositoryNeo4jTest {


    @Autowired
    private PackageRepository packageRepository;

    @Test
    public void testFindAllPackages() {
        Iterator<PackageEntity> list = packageRepository.findAll().iterator();

        while (list.hasNext()) {
            PackageEntity result = list.next();

            PackageEntity result2 =  packageRepository.findByFqn(result.getFqn());

            assertThat(result2.getFqn(), is(result.getFqn()));
            System.out.println(result);
        }
    }

    @Test
    public void findChildren() {
        List<PackageEntity> children = packageRepository.findChildren("nl.rav.codegraph.neo4j");

        for(PackageEntity child: children) {
            System.out.println(child);
        }
    }

    @Test
    public void testFindSisters() {
        List<PackageEntity> packagesDependingOn = packageRepository.findSistersDependingOnBrother("nl.rav.codegraph.controller");
        System.out.println(" results: " + packagesDependingOn.size());
        for(PackageEntity dependantPackage: packagesDependingOn) {
            System.out.println(dependantPackage);
        }
    }


    @Test
    public void testFindChildrenDependingOnParent() {
        List<PackageEntity> packagesDependingOn = packageRepository.findChildrenDependingOnParent("nl.rav.codegraph.controller.drawing");
        System.out.println(" results: " + packagesDependingOn.size());
        for(PackageEntity dependantPackage: packagesDependingOn) {
            System.out.println(dependantPackage);
        }
    }

    @Test
    public void testFindChildrenDependingOnParent2() {
        List<PackageEntity> packagesDependingOn = packageRepository.findChildrenDependingOnParent("nl.rav.codegraph");
        System.out.println(" results: " + packagesDependingOn.size());
        for(PackageEntity dependantPackage: packagesDependingOn) {
            System.out.println(dependantPackage);
        }
    }


    @Test
    public void testFindParentDependingOnChild() {
        List<PackageEntity> packagesDependingOn = packageRepository.findParentDependingOnChild("nl.rav.codegraph.controller.drawing.domain");
        System.out.println(" results: " + packagesDependingOn.size());
        for(PackageEntity dependantPackage: packagesDependingOn) {
            System.out.println(dependantPackage);
        }
    }

}
