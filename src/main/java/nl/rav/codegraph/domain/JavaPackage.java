package nl.rav.codegraph.domain;

/**
 * Created by rene on 24-2-16.
 */
public class JavaPackage {

    private String clsid = "package";
    private final long id;
    private final String name;
    private final String fqn;

    public JavaPackage(long id, String name, String fqn) {
        this.id = id;
        this.name = name;
        this.fqn = fqn;
    }

    public String getClsid() {
        return clsid;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFqn() {
        return fqn;
    }

    public boolean equalsOrContains(JavaPackage pckage) {
        return pckage.getFqn().contains(this.getFqn());
    }

    @Override
    public String toString() {
        return "JavaPackage{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", fqn='" + fqn + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JavaPackage that = (JavaPackage) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
