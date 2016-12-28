package nl.rav.codegraph.neo4j.repository;

import nl.rav.codegraph.neo4j.domain.PackageEntity;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import java.util.List;

/**
 * Created by rene on 18-11-16.
 */
public interface PackageRepository extends GraphRepository<PackageEntity> {

    PackageEntity findByFqn(String fqn);

    @Query("MATCH (p:Package)-[:CONTAINS]->(c:Package) " +
            " WHERE p.fqn = {0}" +
            " RETURN DISTINCT c")
    List<PackageEntity> findChildren(String fqn);

    @Query("MATCH (b:Package)-[:CONTAINS*]->(x)-[:DEPENDS_ON]" +
            "->(y)<-[:CONTAINS*]-(s:Package) " +
            "<-[:CONTAINS]-(t:Package)-[:CONTAINS]->(b:Package)" +
            " WHERE (b.fqn = {0})  " +
            " RETURN DISTINCT s")
    List<PackageEntity> findSistersDependingOnBrother(String fqn);


    @Query("MATCH (p:Package)-[:CONTAINS]->(x)-[:DEPENDS_ON]" +
            "->(y)<-[:CONTAINS*]-(c:Package) " +
            "<-[:CONTAINS]-(p:Package)" +
            " WHERE (p.fqn = {0})  " +
            " RETURN DISTINCT c")
    List<PackageEntity> findChildrenDependingOnParent(String fqn);

    @Query("MATCH (p:Package)-[:CONTAINS]->(x)-[:DEPENDS_ON]" +
            "->(y)<-[:CONTAINS*]-(c:Package) " +
            "<-[:CONTAINS]-(p:Package)" +
            " WHERE (c.fqn = {0})  " +
            " RETURN DISTINCT p")
    List<PackageEntity> findParentDependingOnChild(String fqn);

}
