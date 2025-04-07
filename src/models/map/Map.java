package models.map;

import models.result.Result;

import java.util.List;

public class Map {
    private int id;
    List<Tile> tiles;

    public int calculateDistance(Cord start, Cord end) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    //TODO
    public Result<String> printMap(Cord center, int radius) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static Result<String> helpReadingMap() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
