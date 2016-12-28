package nl.rav.codegraph.domain;

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
public class Artifact {

    private String clsid = "artifact";
    private Long id;
    private String fqn;
    private String groupId;
    private String name;
    private String type;
    private String version;

    public String getClsid() {
        return clsid;
    }

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
        return "Artifact{" +
                "id=" + id +
                ", fqn='" + fqn + '\'' +
                ", groupId='" + groupId + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
