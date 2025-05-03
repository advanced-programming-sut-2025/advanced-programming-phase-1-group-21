package models.map;

import models.App;
import models.result.Result;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Map {
    public ArrayList<ArrayList<Tile>> tiles = new ArrayList<>();
    public ArrayList<Building> buildings;

    protected House house = new House();
    protected ArrayList<Lake> lakes = new ArrayList<>();
    protected ArrayList<Shop> shops = new ArrayList<>();
    protected Mines mines = new Mines();
    protected GreenHouse greenHouses = new GreenHouse();
    protected Barn barn;
    protected Coop coop;
    public final MapType mapType;

    public Map(MapType mapType) {
        this.mapType = mapType;
    }

    private static <T> T getElementOrNull(List<T> list, int index) {
        if (index >= 0 && index < list.size()) {
            return list.get(index);
        }
        return null;
    }

    public Tile getTile(Coord coord) {
        return getElementOrNull(getElementOrNull(tiles, coord.getY()), coord.getX());
    }

    public int calculateDistance(Coord start, Coord end) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ArrayList<String> printMap(Coord center, int radius) {
        ArrayList<String> output = new ArrayList<>();
        for(int i = center.getY() ; i < center.getY() + radius ; i++) {
            for(int j = center.getX() ; j < center.getX() + radius; j++) {
                Coord coord = new Coord(i, j);
                Tile tile = getTile(coord);
                if (tile == null) continue;
                if (App.game.getCurrentPlayer().getCoord().equals(coord))
                    output.add("@");
                else
                    output.add(tile.getSprite());
            }
            output.add("\n");
        }
        return output;
    }

    public ArrayList<ArrayList<Tile>> getTiles() {
        return tiles;
    }

    public House getHouse() {
        return house;
    }

    public ArrayList<Lake> getLakes() {
        return lakes;
    }

    public Mines getMines() {
        return mines;
    }

    public GreenHouse getGreenHouses() {
        return greenHouses;
    }

    public ArrayList<Shop> getShops() {
        return shops;
    }

    public Barn getBarn() {
        return barn;
    }

    public Coop getCoop() {
        return coop;
    }

    public static Result<String> helpReadingMap() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
