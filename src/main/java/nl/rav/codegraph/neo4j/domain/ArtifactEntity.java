package nl.rav.codegraph.neo4j.domain;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

/**
 *
 exampleMaven node:
 fqn	nl.rav:codegraph:1.0-SNAPSHOT
 groupId	nl.rav
 name	codegraph
 artifactId	codegraph
 packaging	jar
 version	1.0-SNAPSHOT
 */
@NodeEntity(label="Artifact")
public class ArtifactEntity {

    @GraphId
    Long id;

    String fqn;
    String groupId;
    String name;
    String type;
    String version;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFqn() {
        return fqn;
    }

    public void setFqn(String fqn) {
        this.fqn = fqn;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "ArtifactEntity{" +
                "id=" + id +
                ", fqn='" + fqn + '\'' +
                ", groupId='" + groupId + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
