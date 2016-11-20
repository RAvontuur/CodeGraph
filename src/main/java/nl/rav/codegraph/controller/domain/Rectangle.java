package nl.rav.codegraph.controller.domain;

/**
 * Created by rene on 20-11-16.
 */
public class Rectangle {
    private String name;
    private int x;
    private int y;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public static String toHeader() {
        return "x,y,name";
    }

    @Override
    public String toString() {
        return x + "," + y + "," + name;
    }
}
