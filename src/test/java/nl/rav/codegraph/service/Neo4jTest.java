package nl.rav.codegraph.service;

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

import static org.junit.Assert.assertTrue;

/**
 * Created by rene on 18-11-16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({
        @ContextConfiguration(classes = {PersistenceContext.class}),
})
public class Neo4jTest {


    @Autowired
    private PackageRepository packageRepository;

    @Test
    public void testFindAllPackages() {
        Iterator<PackageEntity> list = packageRepository.findAll().iterator();

        while (list.hasNext()) {
            PackageEntity result = list.next();
            System.out.println(result);
        }
    }
}
