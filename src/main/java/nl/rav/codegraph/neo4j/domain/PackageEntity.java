package nl.rav.codegraph.neo4j.domain;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

/**
 * Created by rene on 18-11-16.
 */
@NodeEntity(label="Package")
public class PackageEntity {

    @GraphId
    Long id;

    String fileName;
    String fqn;
    String name;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFqn() {
        return fqn;
    }

    public void setFqn(String fqn) {
        this.fqn = fqn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "PackageEntity{" +
                "id=" + id +
                ", fileName='" + fileName + '\'' +
                ", fqn='" + fqn + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
