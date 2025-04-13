package models.map;

import models.result.Result;

import java.util.List;

public class Map {
    private int id;
    List<Tile> tiles;
    List<House> houses;
    List<Lake> lakes;
    List<Mines> mines;
    List<GreenHouse> greenHouses;


    public int calculateDistance(Coord start, Coord end) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    //TODO
    public Result<String> printMap(Coord center, int radius) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static Result<String> helpReadingMap() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
