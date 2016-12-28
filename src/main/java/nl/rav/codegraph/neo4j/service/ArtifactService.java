package nl.rav.codegraph.neo4j.service;

import nl.rav.codegraph.domain.Artifact;
import nl.rav.codegraph.neo4j.domain.ArtifactEntity;
import nl.rav.codegraph.neo4j.repository.ArtifactRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by rene on 20-11-16.
 */
@Service
public class ArtifactService {

    private final ArtifactRepository artifactRepository;

    public ArtifactService(ArtifactRepository artifactRepository) {
        this.artifactRepository = artifactRepository;
    }


    public Artifact findArtifact(String packageFqn, String type) {
        List<ArtifactEntity> entities = artifactRepository.findByPackageFqnAndType(packageFqn, type);
        if (entities.isEmpty()) {
            return null;
        }

        return convertToArtifact(entities.get(0));
    }

    private Artifact convertToArtifact(ArtifactEntity artifactEntity) {
        Artifact artifact = new Artifact();

        artifact.setId(artifactEntity.getId());
        artifact.setFqn(artifactEntity.getFqn());
        artifact.setGroupId(artifactEntity.getGroupId());
        artifact.setName(artifactEntity.getName());
        artifact.setType(artifactEntity.getType());
        artifact.setVersion(artifactEntity.getVersion());

        return artifact;
    }


}

