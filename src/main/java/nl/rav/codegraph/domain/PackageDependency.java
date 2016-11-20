package nl.rav.codegraph.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rene on 20-11-16.
 */
public class PackageDependency {

    private JavaPackage from;

    private List<JavaPackage> downwards = new ArrayList<>();
    //an upward dependency is a cyclic dependency
    private List<JavaPackage> upwards = new ArrayList<>();

    public JavaPackage getFrom() {
        return from;
    }

    public void setFrom(JavaPackage from) {
        this.from = from;
    }

    public List<JavaPackage> getDownwards() {
        return downwards;
    }

    public List<JavaPackage> getUpwards() {
        return upwards;
    }

    public void addDownwards(JavaPackage to) {
        downwards.add(to);
    }

    public void addUpwards(JavaPackage to) {
        upwards.add(to);
    }

    @Override
    public String toString() {
        return "PackageDependency{" +
                "from=" + from +
                '}';
    }
}
