package nl.rav.codegraph.neo4j.repository;

import nl.rav.codegraph.neo4j.domain.PackageEntity;
import org.springframework.data.neo4j.repository.GraphRepository;

/**
 * Created by rene on 18-11-16.
 */
public interface PackageRepository extends GraphRepository<PackageEntity> {

}
