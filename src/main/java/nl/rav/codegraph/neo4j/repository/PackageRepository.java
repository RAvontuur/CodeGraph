package nl.rav.codegraph.neo4j.repository;

import nl.rav.codegraph.neo4j.domain.PackageEntity;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import java.util.List;

/**
 * Created by rene on 18-11-16.
 */
public interface PackageRepository extends GraphRepository<PackageEntity> {

    @Query("MATCH (p:Package)-[:CONTAINS]->(q:Package) " +
            "WHERE p.fqn = {0} " +
            "RETURN DISTINCT q")
    List<PackageEntity> findChildren(String fqn);

    @Query("MATCH (p:Package)-[:CONTAINS]->(:Class)-[:DEPENDS_ON]" +
            "->(:Class)<-[:CONTAINS]-(q:Package) " +
            "WHERE p.fqn = {0} " +
            "RETURN DISTINCT q")
    List<PackageEntity> findAfferents(String fqn);


    @Query("MATCH (p:Package)-[:CONTAINS]->(:Class)-[:DEPENDS_ON]" +
            "->(:Class)<-[:CONTAINS]-(q:Package) " +
            "WHERE q.fqn = {0} " +
            "RETURN DISTINCT p")
    List<PackageEntity> findEfferents(String fqn);
}
