package nl.rav.codegraph;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by rene on 24-2-16.
 */
public class Package {

    private int x;
    private int y;
    private String name;
    private Set<String> efferents = new HashSet<>();
    private Set<String> afferents = new HashSet<>();

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static String toHeader() {
        return "x,y,name";
    }

    @Override
    public String toString() {
        return x + "," + y + "," + name; // + "," + efferents.toString() + "," + afferents.toString();
    }

    public void addEfferent(String name) {
        efferents.add(name);
    }

    public Set<String> getEfferents() {
        return efferents;
    }


    public void addAfferent(String name) {
        afferents.add(name);
    }

    public Set<String> getAfferents() {
        return afferents;
    }
}
