package nl.rav.codegraph.service;

import nl.rav.codegraph.model.*;
import nl.rav.codegraph.model.Package;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rene on 18-11-16.
 */
public class CacheItem {

    private Map<String, Package> packages = new HashMap<>();
    private List<Arrow> arrows = new ArrayList<>();

    public Map<String, Package> getPackages() {
        return packages;
    }

    public List<Arrow> getArrows() {
        return arrows;
    }
}
