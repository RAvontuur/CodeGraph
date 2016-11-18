package nl.rav.codegraph.service;

import nl.rav.codegraph.model.*;
import nl.rav.codegraph.model.Package;
import nl.rav.codegraph.neo4j.domain.PackageEntity;
import nl.rav.codegraph.neo4j.repository.PackageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final PackageRepository packageRepository;
    //naive cache
    private Map<String, CacheItem> cache = new HashMap<>();

    @Autowired
    public GraphService(PackageRepository packageRepository) {
        this.packageRepository = packageRepository;
    }

    public Collection<nl.rav.codegraph.model.Package> doData(String path) throws IOException {
        return cache.get("@" + path).getPackages().values();
    }

    public List<Arrow> doArrows(String path) {
        return cache.get("@" + path).getArrows();
    }

    public void doView(String path) {
        //always update cache
        updateCache(path);
    }

    private void updateCache(String path) {
        cache.put("@" + path, aggregate(path));
    }

    public CacheItem aggregate(String path) {
        CacheItem cacheItem = new CacheItem();

        List<PackageEntity> children = packageRepository.findChildren(path);
        int x = 0;
        int y = 0;
        for (PackageEntity child: children) {
            Package pckage = new Package();
            pckage.setFullName(child.getFqn());
            pckage.setName(child.getName());
            pckage.setX(x);
            pckage.setY(y);
            cacheItem.getPackages().put(child.getName(), pckage);
            y++;
        }
        for (Package brother: cacheItem.getPackages().values()) {
            List<PackageEntity> sisterEntities = packageRepository.findAfferents(brother.getFullName());
            for (PackageEntity sisterEntity: sisterEntities) {
                Package sister = cacheItem.getPackages().get(sisterEntity.getName());
                if (sister == null || brother.getFullName().equals(sister.getFullName())) {
                    continue;
                }
                Arrow arrow = new Arrow(sister.getX(), sister.getY(), brother.getX(), brother.getY());
                cacheItem.getArrows().add(arrow);
            }
        }

        return cacheItem;
    }

}
