package io.github.StardewValley.models.map;

import io.github.StardewValley.models.App;

import java.io.Serializable;

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

    public static Coord getRandomCoord(int maxX, int maxY) {
        int x = App.getInstance().getInstance().getRandom().nextInt(maxX); // [0, maxX]
        int y = App.getInstance().getInstance().getRandom().nextInt(maxY); // [0, maxY]
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
}
