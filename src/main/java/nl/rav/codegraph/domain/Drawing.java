package nl.rav.codegraph.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rene on 20-11-16.
 */
public class Drawing {

    List<Level> levels = new ArrayList<>();


    public List<Level> getLevels() {
        return levels;
    }

    public void generate(List<PackageDependency> dependencies) {
        for (PackageDependency dependency: dependencies) {
            Level level = new Level(levels.size());
            level.addDependeny(dependency);
            levels.add(level);
        }
    }
}
