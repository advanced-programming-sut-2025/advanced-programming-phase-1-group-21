package models.map;

import models.App;

import java.util.ArrayList;
import java.util.List;

public class Coord {
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

    public static Coord getValidCoord(int x, int y) {
        Coord coord = new Coord(x, y);
        if (coord.getX() < 0 || coord.getY() < 0) return null;
        ArrayList<ArrayList<Tile>> tiles = App.game.getCurrentPlayer().currentLocationTiles();
        if(y >= tiles.size() || x >= tiles.get(0).size())
            return null;
        return coord;
    }

    public Coord getValidCoord() {
        return getValidCoord(x, y);
    }

    public Coord addCoord(Coord coord) {
        return new Coord(x + coord.getX(), y + coord.getY());
    }
}