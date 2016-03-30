package nl.rav.codegraph;

import jdepend.framework.JDepend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rene on 13-3-16.
 */
@Service
public class GraphService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final String[] sourceLocations;
    private final String[] acceptedPackages;
    private final String mavenRepoDir;
    private final String[] mavenModules;

    //naive cache
    private Map<String, AggregatedPackages> cache = new HashMap<>();
    private Collection jPackages;

    @Autowired
    public GraphService(@Value("${sourceLocations:}") String[] sourceLocations,
                        @Value("${acceptedPackages}") String[] acceptedPackages,
                        @Value("${mavenRepo:}") String mavenRepoDir,
                        @Value("${mavenModules:}") String[] mavenModules) {
        this.sourceLocations = sourceLocations;
        this.acceptedPackages = acceptedPackages;
        this.mavenRepoDir = mavenRepoDir;
        this.mavenModules = mavenModules;
    }

    public Collection<Package> doData(String path) throws IOException {
        return cache.get("@" + path).getPackages().values();
    }

    public List<Arrow> doArrows(String path) throws IOException {
        return cache.get("@" + path).getArrows();
    }

    public void doView(String path) throws IOException {

        if (cache.get("@" + path) != null) {
            return;
        }
        updateCache(path);
    }

    private void updateCache(String path) throws IOException {

        if (jPackages == null) {
            JDepend jDepend = new JDepend();
            for (String sourceLocation : sourceLocations) {
                jDepend.addDirectory(sourceLocation);
                log.info("added: " + sourceLocation);
            }
            for (String module: mavenModules) {
                String pck = module.split(":")[0];
                String version = module.split(":")[1];
                String[] names = pck.split("\\.");
                String moduleName = names[names.length-1];
                String sourceLocation = mavenRepoDir
                        + "/" + pck.replaceAll("\\.", "/")
                        + "/" + version + "/" + moduleName + "-" + version;
                if (fileExists(sourceLocation + ".jar")) {
                    jDepend.addDirectory(sourceLocation + ".jar");
                }
                if (fileExists(sourceLocation + ".war")) {
                    jDepend.addDirectory(sourceLocation + ".war");
                }
                log.info("added: " + sourceLocation);
            }
            log.info("start analyzing ..");
            jPackages = jDepend.analyze();
            log.info("n=" + jPackages.size());
        }

        AggregatedPackages cacheItem = new AggregatedPackages(jPackages);

        cacheItem.aggregate(path, acceptedPackages);
        cache.put("@" + path, cacheItem);
    }

    private boolean fileExists(String location) {
        return (new File(location)).isFile();
    }


}
