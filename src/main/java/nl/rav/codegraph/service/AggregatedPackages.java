package nl.rav.codegraph.service;

import jdepend.framework.JavaPackage;
import nl.rav.codegraph.model.*;
import nl.rav.codegraph.model.Package;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

/**
 * Created by rene on 24-2-16.
 */
public class AggregatedPackages {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final Collection jPackages;

    private Map<String, nl.rav.codegraph.model.Package> packages = new HashMap<>();

    public AggregatedPackages(Collection jPackages) {
        this.jPackages = jPackages;
    }

    public void aggregate(String path, String[] acceptedPackages) throws IOException {
        packages.clear();

        Iterator iter = jPackages.iterator();
        while (iter.hasNext()) {
            JavaPackage p = (JavaPackage) iter.next();
            if (!accept(acceptedPackages, p)) {
                log.info("rejected: " + p.getName());
                continue;
            }

            String childPath = getPackagePathChild(path, p.getName());
            if (childPath == null) {
                continue;
            }

            Package currentPackage = packages.get(childPath);
            if (currentPackage == null) {
                currentPackage = new Package();
                currentPackage.setFullName(childPath);
                currentPackage.setName(getPackageNameChild(path, p.getName()));
                currentPackage.setX(0);
                currentPackage.setY(0);
                packages.put(childPath, currentPackage);
            }

            log.info("package: " + p.getName() + ", stem: " + currentPackage.getName());
            addEfferents(path, acceptedPackages, currentPackage, p);
            addAfferents(path, acceptedPackages, currentPackage, p);

        }
        recalculate();
    }


    private void recalculate() {
        if (packages.isEmpty()) {
            return;
        }

        //start with the empty packages
        List<Package> emptyPackages = findEmptyPackages();
        if (emptyPackages.isEmpty() || hasOnlyIndependent(emptyPackages)) {
            log.info("cyclic dependency, just take the first as root.");
            for (Map.Entry<String, Package> entry : packages.entrySet()) {
                Package p = packages.get(entry.getKey());
                if (p.getEfferents().size() == 0) {
                    continue;
                }
                if (!emptyPackages.contains(p)) {
                    emptyPackages.add(packages.get(entry.getKey()));
                    break;
                }
            };
        }

        Stack<String> callers = new Stack<>();
        calcY(emptyPackages, 0, callers);

        removeGaps();
        int maxY = calcMaxY();
        log.info("maxY: " + maxY);
        for (int i = 0; i <= maxY; i++) {
            calcX(i);
        }

    }

    private boolean hasOnlyIndependent(List<Package> packages) {
        for (Package p: packages) {
            if (p.getAfferents().size() > 0 || p.getEfferents().size() > 0) {
                return false;
            }
        }
        return true;
    }

    public Map<String, Package> getPackages() {
        return packages;
    }

    public void export() {
        log.info(Package.toHeader());
        for (Package aPackage : packages.values()) {
            log.info(aPackage.toString());
        }
    }

    public List<Arrow> getArrows() {
        List<Arrow> result = new ArrayList<>();
        for (Package p : packages.values()) {
            for (Package aff : namesToPackages(p.getAfferents())) {
                Arrow arrow = new Arrow(p.getX(), p.getY(), aff.getX(), aff.getY());
                result.add(arrow);
            }
        }
        return result;
    }

    private String getPackageNameChild(String path, String name) {
        if (name.equals(path)) {
            //current path
            return ".";
        }
        if (name.startsWith(path + ".")) {
            String remainder = name.substring(path.length() + 1);
            int pos = remainder.indexOf(".");
            String childName = pos >= 0 ? remainder.substring(0, pos) : remainder;
            return childName;
        }
        return null;
    }

    private String getPackagePathChild(String path, String name) {
        String leafName = getPackageNameChild(path, name);
        if (leafName == null) {
            return null;
        }
        if (leafName.equals(".")) {
            //current path
            return name;
        }
        return path + "." + leafName;
    }

    private boolean accept(String[] acceptedPackages, JavaPackage p) {
        for (String rootPackage : acceptedPackages) {
            if (p.getName().startsWith(rootPackage)) {
                return true;
            }
        }
        return false;
    }

    //efferent = depends on
    private void addEfferents(String path, String[] acceptedPackages, Package currentPackage, JavaPackage p) {
        Collection efferents = p.getEfferents();
        Iterator iter = efferents.iterator();
        while (iter.hasNext()) {
            JavaPackage a = (JavaPackage) iter.next();
            if (!accept(acceptedPackages, a)) {
                continue;
            }
            String childName = getPackagePathChild(path, a.getName());

            if (childName == null) {
                //out of scope current path
                continue;
            }
            if (currentPackage.getFullName().equals(childName)) {
                //within same package
                continue;
            }
            currentPackage.addEfferent(childName);
            log.info("Efferent: " + a.getName() + ", childName: " + childName);
        }
    }


    private void addAfferents(String path, String[] acceptedPackages, Package currentPackage, JavaPackage p) {
        Collection afferents = p.getAfferents();
        Iterator iter = afferents.iterator();
        while (iter.hasNext()) {
            JavaPackage a = (JavaPackage) iter.next();
            if (!accept(acceptedPackages, a)) {
                continue;
            }
            String childName = getPackagePathChild(path, a.getName());
            if (childName == null) {
                //out of scope current path
                continue;
            }
            if (currentPackage.getFullName().equals(childName)) {
                //within same package
                continue;
            }
            currentPackage.addAfferent(childName);
            log.info("Afferent: " + a.getName());
        }
    }

    private void calcY(List<Package> roots, int level, Stack<String> callers) {
        for (Package root : roots) {
            log.info("calcY root:" + root.getName() + ", level:" + level + ", callers: " + callers.toString());
            if (callers.contains(root.getName())) {
                log.info("cyclic dependency, root:" + root.getName() + ", level:" + level);
                continue;
            }

            if (root.getY() < level) {
                root.setY(level);
            }
            callers.push(root.getName());
            calcY(namesToPackages(root.getEfferents()), root.getY() + 1, callers);
            callers.pop();
        }
    }

    private List<Package> namesToPackages(Set<String> xferents) {
        List<Package> result = new ArrayList<>();
        for (String eff : xferents) {
            Package packageFound = packages.get(eff);
            if (packageFound == null) {
                log.warn("Efferent {} not found.", eff);
                continue;
            }
            result.add(packageFound);
        }
        return result;
    }

    private void removeGaps() {
        int maxY = calcMaxY();
        log.info("maxY (before removeGaps): " + maxY);

        for (int i = maxY - 1; i >= 0; i--) {
            if (!hasPackagesOfLevel(i)) {
                log.info("remove Gap: " + i);
                removeGap(i);
            }
        }
    }

    private boolean hasPackagesOfLevel(int y) {
        for (Map.Entry<String, Package> entry : packages.entrySet()) {
            if (entry.getValue().getY() == y) {
                return true;
            }
        }

        return false;
    }

    private void removeGap(int y) {
        for (Map.Entry<String, Package> entry : packages.entrySet()) {
            if (entry.getValue().getY() > y) {
                entry.getValue().setY(entry.getValue().getY() - 1);
            }
        }
        ;
    }

    private List<Package> findEmptyPackages() {
        List<Package> result = new ArrayList<>();

        for (Map.Entry<String, Package> entry : packages.entrySet()) {
            if (entry.getValue().getAfferents().isEmpty()) {
                result.add(entry.getValue());
            }
        }

        return result;
    }


    private int calcMaxY() {
        int res = 0;
        for (Map.Entry<String, Package> entry : packages.entrySet()) {
            if (entry.getValue().getY() > res) {
                res = entry.getValue().getY();
            }
        }
        return res;
    }

    private void calcX(int y) {
        int x = 0;
        for (Map.Entry<String, Package> entry : packages.entrySet()) {
            if (entry.getValue().getY() == y) {
                entry.getValue().setX(x);
                x++;
            }
        }

    }
}
