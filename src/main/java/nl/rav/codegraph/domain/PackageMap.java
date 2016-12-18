package nl.rav.codegraph.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by rene on 20-11-16.
 */
public class PackageMap {

    private Map<Long, JavaPackage> packageMap = new HashMap<>();

    public JavaPackage getJavaPackage(long id) {
        return packageMap.get(id);
    }

    public void addJavaPackage(JavaPackage javaPackage) {
        packageMap.put(javaPackage.getId(), javaPackage);
    }

    public List<JavaPackage> allJavaPackages() {
        return packageMap.values().stream().collect(Collectors.toList());
    }
}
