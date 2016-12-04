package nl.rav.codegraph.domain;

import nl.rav.codegraph.algorithm.spanningtree.Edge;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by rene on 20-11-16.
 */
public class Graph {

    private List<Edge> edges;
    private Map<Long, JavaPackage> packageMap = new HashMap<>();

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

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
