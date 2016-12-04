package nl.rav.codegraph.neo4j.service;

import nl.rav.codegraph.domain.JavaPackage;
import nl.rav.codegraph.neo4j.domain.PackageEntity;
import nl.rav.codegraph.neo4j.repository.PackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rene on 20-11-16.
 */
@Service
public class PackageService {

    private final PackageRepository packageRepository;

    @Autowired
    public PackageService(PackageRepository packageRepository) {
        this.packageRepository = packageRepository;
    }

    public JavaPackage findPackage(String fqn) {
        return convertToPackage(packageRepository.findByFqn(fqn));
    }

    public List<JavaPackage> findChildren(String fqn) {
        return convertToPackages(packageRepository.findChildren(fqn));
    }

    public List<JavaPackage> findPackagesDependingOn(String fqn) {
        return convertToPackages(packageRepository.findPackagesDependingOn(fqn));
    }

    private List<JavaPackage> convertToPackages(List<PackageEntity> childPackageEntities) {
        List<JavaPackage> childPackages = new ArrayList<>();
        for (PackageEntity child: childPackageEntities) {
            JavaPackage pckage = convertToPackage(child);
            childPackages.add(pckage);
        }
        return childPackages;
    }

    private JavaPackage convertToPackage(PackageEntity child) {
        JavaPackage pckage = new JavaPackage(child.getId(), child.getName(), child.getFqn());
        return pckage;
    }
}
