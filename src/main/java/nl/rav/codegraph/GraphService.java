package nl.rav.codegraph;

import jdepend.framework.JDepend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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

    //naive cache
    private Map<String, AggregatedPackages> cache = new HashMap<>();
    private Collection jPackages;

    @Autowired
    public GraphService(@Value("${sourceLocations}") String[] sourceLocations,
                        @Value("${acceptedPackages}") String[] acceptedPackages) {
        this.sourceLocations = sourceLocations;
        this.acceptedPackages = acceptedPackages;
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
            log.info("start analyzing ..");
            jPackages = jDepend.analyze();
            log.info("n=" + jPackages.size());
        }

        AggregatedPackages cacheItem = new AggregatedPackages(jPackages);

        cacheItem.aggregate(path, acceptedPackages);
        cache.put("@" + path, cacheItem);
    }


}
