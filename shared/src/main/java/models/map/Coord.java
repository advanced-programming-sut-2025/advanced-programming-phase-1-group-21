package models.map;

import java.io.Serializable;
import java.util.Random;

public class Coord implements Serializable {
    private int x;
    private int y;

    public Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Coord addCoord(Coord coord) {
        return new Coord(x + coord.getX(), y + coord.getY());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coord coord = (Coord) o;
        return x == coord.x && y == coord.y;
    }

    public static Coord getRandomCoord(int maxX, int maxY, Random random) {
        int x = random.nextInt(maxX); // [0, maxX]
        int y = random.nextInt(maxY); // [0, maxY]
        return new Coord(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public boolean isNeighbor(Coord other) {
        int dx = Math.abs(this.x - other.x);
        int dy = Math.abs(this.y - other.y);
        return (dx + dy) == 1;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
