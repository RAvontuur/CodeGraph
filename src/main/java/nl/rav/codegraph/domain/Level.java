package nl.rav.codegraph.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rene on 20-11-16.
 */
public class Level {

    private int y;
    private List<PackageDependency> dependencies = new ArrayList<>();

    public Level(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public List<PackageDependency> getDependencies() {
        return dependencies;
    }

    public void addDependeny(PackageDependency packageDependency) {
        dependencies.add(packageDependency);
    }

    @Override
    public String toString() {
        return "Level{" +
                "y=" + y +
                '}';
    }
}
