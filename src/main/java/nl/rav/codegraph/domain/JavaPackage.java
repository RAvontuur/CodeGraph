package nl.rav.codegraph.domain;

/**
 * Created by rene on 24-2-16.
 */
public class JavaPackage {

    private String name;
    private String fqn;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFqn() {
        return fqn;
    }

    public void setFqn(String fqn) {
        this.fqn = fqn;
    }

    public boolean equalsOrContains(JavaPackage pckage) {
        return pckage.getFqn().contains(this.getFqn());
    }

    @Override
    public String toString() {
        return "JavaPackage{" +
                "fqn='" + fqn + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JavaPackage that = (JavaPackage) o;

        return !(fqn != null ? !fqn.equals(that.fqn) : that.fqn != null);

    }

    @Override
    public int hashCode() {
        return fqn != null ? fqn.hashCode() : 0;
    }
}
