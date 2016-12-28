package nl.rav.codegraph.neo4j.repository;

import nl.rav.codegraph.neo4j.domain.ArtifactEntity;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import java.util.List;

/**
 * Created by rene on 18-11-16.
 */
public interface ArtifactRepository extends GraphRepository<ArtifactEntity> {

    ArtifactEntity findByFqn(String fqn);

    @Query("MATCH (a:Artifact)-[:CONTAINS]->(p:Package)" +
            " WHERE ((p.fqn = {0}) and (a.type = {1})) " +
            " RETURN a")
    List<ArtifactEntity> findByPackageFqnAndType(String fqn, String type);
}
