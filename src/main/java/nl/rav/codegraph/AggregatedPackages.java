package nl.rav.codegraph;

import jdepend.framework.JavaPackage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

/**
 * Created by rene on 24-2-16.
 */
public class AggregatedPackages {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private Map<String, Package> packages = new HashMap<>();
    private List<String> rootPackages = new ArrayList<>();

    private final Collection jPackages;

    public AggregatedPackages(Collection jPackages) {
        this.jPackages = jPackages;
    }

    public void aggregate(String rootPackageString) throws IOException {
        setRootPackage(rootPackageString);

        Iterator iter = jPackages.iterator();
        while (iter.hasNext()) {
            JavaPackage p = (JavaPackage) iter.next();
            if (!accept(p)) {
                log.info("rejected: " + p.getName());
                continue;
            }

            String stem = getStemPackageName(p.getName());
            Package currentPackage = packages.get(stem);
            if (currentPackage == null) {
                currentPackage = new Package();
                currentPackage.setFullName(stem);
                currentPackage.setName(getStemSubPackageName(p.getName()));
                currentPackage.setX(0);
                currentPackage.setY(0);
                packages.put(stem, currentPackage);
            }

            log.info("package: " + p.getName() + ", stem: " + currentPackage.getName());
            addEfferents(currentPackage, p);
            addAfferents(currentPackage, p);

        }
        recalculate();
    }

    private void setRootPackage(String rootPackageString) {
        for (String p : rootPackageString.split(";")) {
            rootPackages.add(p);
        }
    }

    private void recalculate() {
        if (packages.isEmpty()) {
            return;
        }

        //start with the empty packages
        List<Package> emptyPackages = findEmptyPackages();
        if (emptyPackages.isEmpty()) {
            log.info("cyclic dependency, just take the first as root.");
            emptyPackages.add(packages.get(packages.keySet().iterator().next()));
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

    private String getStemPackageName(String name) {
        for (String rootPackage : rootPackages) {
            if (name.equals(rootPackage)) {
                return name;
            }
            if (name.startsWith(rootPackage + ".")) {
                String remainder = name.substring(rootPackage.length() + 1);
                int pos = remainder.indexOf(".");
                String stem =  pos >= 0 ? remainder.substring(0, pos) : remainder;
                return rootPackage + "." + stem;
            }
        }
        return name;
    }

    private String getStemSubPackageName(String name) {
        for (String rootPackage : rootPackages) {
            if (name.equals(rootPackage)) {
                return name;
            }
            if (name.startsWith(rootPackage + ".")) {
                String remainder = name.substring(rootPackage.length() + 1);
                int pos = remainder.indexOf(".");
                String stem =  pos >= 0 ? remainder.substring(0, pos) : remainder;
                return stem;
            }
        }
        return name;
    }

    private boolean accept(JavaPackage p) {
        for (String rootPackage : rootPackages) {
            if (p.getName().startsWith(rootPackage)) {
                return true;
            }
        }
        return false;
    }

    //efferent = depends on
    private void addEfferents(Package currentPackage, JavaPackage p) {
        Collection efferents = p.getEfferents();
        Iterator iter = efferents.iterator();
        while (iter.hasNext()) {
            JavaPackage a = (JavaPackage) iter.next();
            if (!accept(a)) {
                continue;
            }
            String stem = getStemPackageName(a.getName());
            if (!currentPackage.getFullName().equals(stem)) {
                currentPackage.addEfferent(stem);
            }
            log.info("Efferent: " + a.getName() + ", stem: " + stem);
        }
    }


    private void addAfferents(Package currentPackage, JavaPackage p) {
        Collection afferents = p.getAfferents();
        Iterator iter = afferents.iterator();
        while (iter.hasNext()) {
            JavaPackage a = (JavaPackage) iter.next();
            if (!accept(a)) {
                continue;
            }
            String stem = getStemPackageName(a.getName());
            if (!currentPackage.getFullName().equals(stem)) {
                currentPackage.addAfferent(stem);
            }
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
            result.add(packages.get(eff));
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
