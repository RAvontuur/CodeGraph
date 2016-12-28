package nl.rav.test.codegraph.service;

import nl.rav.codegraph.neo4j.configuration.PersistenceContext;
import nl.rav.codegraph.neo4j.domain.ArtifactEntity;
import nl.rav.codegraph.neo4j.repository.ArtifactRepository;
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

/**
 * Created by rene on 18-11-16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({
        @ContextConfiguration(classes = {PersistenceContext.class}),
})
public class ArtifactRepositoryNeo4JTest {


    @Autowired
    private ArtifactRepository artifactRepository;

    @Test
    public void testFindAllPackages() {
        Iterator<ArtifactEntity> list = artifactRepository.findAll().iterator();

        while (list.hasNext()) {
            ArtifactEntity result = list.next();
            System.out.println(result);

            if (result.getFqn() == null) {
                continue;
            }
            ArtifactEntity result2 =  artifactRepository.findByFqn(result.getFqn());

            assertThat(result2.getFqn(), is(result.getFqn()));
        }
    }

    @Test
    public void testFindByPackageFqn() {
        List<ArtifactEntity> artifacts = artifactRepository.findByPackageFqnAndType("nl.rav", "jar");
        System.out.println("artifacts: " + artifacts);

        artifacts = artifactRepository.findByPackageFqnAndType("nl.rav.codegraph.controller.drawing", "jar");
        System.out.println("artifacts: " + artifacts);

        artifacts = artifactRepository.findByPackageFqnAndType("nl.rav", "test-jar");
        System.out.println("artifacts: " + artifacts);
    }

}
