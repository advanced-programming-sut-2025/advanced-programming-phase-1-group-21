package models.map;

import models.App;
import models.DailyUpdate;
import models.result.Result;

import java.util.ArrayList;
import java.util.List;

public class Map implements DailyUpdate {
    public ArrayList<ArrayList<Tile>> tiles;
    public ArrayList<Building> buildings;

    private House house;
    private ArrayList<Lake> lakes = new ArrayList<>();
    private ArrayList<Shop> shops = new ArrayList<>();
    private Mines mines;
    private GreenHouse greenHouses;
    private Barn barn;
    private Coop coop;
    public final MapType mapType;

    private void createRectangularMap(int X, int Y) {
        tiles = new ArrayList<>();
        for (int y = 0; y < Y; ++y) {
            ArrayList<Tile> row = new ArrayList<>();
            for (int x = 0; x < X; ++x) {
                row.add(Tile.createEmpty());
            }
            tiles.add(row);
        }
    }

    public Map(MapType mapType) {
        this.mapType = mapType;
        createRectangularMap(mapType.getWidth(), mapType.getHeight());
    }

    private static <T> T getElementOrNull(List<T> list, int index) {
        if (list != null && index >= 0 && index < list.size()) {
            return list.get(index);
        }
        return null;
    }

    public void buildHouse(House house) {
        this.house = house;
    }

    public void buildLakes(Lake lake) {
        this.lakes.add(lake);
    }

    public void buildCoop(Coop coop) {
        this.coop = coop;
    }

    public void buildShop(Shop shop) {
        this.shops.add(shop);
    }

    public void buildMines(Mines mines) {
        this.mines = mines;
    }

    public void buildGreenHouses(GreenHouse greenHouses) {
        this.greenHouses = greenHouses;
    }

    public Tile getTile(Coord coord) {
        return getElementOrNull(getElementOrNull(tiles, coord.getY()), coord.getX());
    }

    public void setTile(Coord coord, Tile tile) {
        if (getTile(coord) == null) return;
        tiles.get(coord.getY()).set(coord.getX(), tile);
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
            if (!output.isEmpty() && !output.getLast().equals("\n"))
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

    public boolean thor(Coord coord) {
        if (mapType != MapType.FARM)
            return false;
        Tile t = getTile(coord);
        if (t == null || t.getPlacable(Building.class) != null) return false;
        setTile(coord, Tile.createEmpty());
        System.err.println("Tile with coord " + coord.toString() + " is hit by thor");
        return true;
    }

    public int getMaxX() {
        return tiles.get(0).size();
    }

    public int getMaxY() {
        return tiles.size();
    }

    public Coord getRandomCoord() {
        return Coord.getRandomCoord(getMaxX(), getMaxY());
    }

    static final int NUMBER_OF_THORS_PER_DAY = 3;
    public boolean nextDay() {
        for (int i = 0; i < NUMBER_OF_THORS_PER_DAY; i++)
            thor(getRandomCoord());

        return false;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("Map Summary:\n");
        builder.append("Map Type: ").append(mapType).append("\n");

        builder.append("House: ").append(house == null ? "null" : house).append("\n");

        builder.append("Lakes: ").append(lakes == null ? "null" : lakes.isEmpty() ? "[]" : lakes).append("\n");

        builder.append("Shops: ").append(shops == null ? "null" : shops.isEmpty() ? "[]" : shops).append("\n");

        builder.append("Mines: ").append(mines == null ? "null" : mines).append("\n");

        builder.append("Greenhouses: ").append(greenHouses == null ? "null" : greenHouses).append("\n");

        builder.append("Barn: ").append(barn == null ? "null" : barn).append("\n");

        builder.append("Coop: ").append(coop == null ? "null" : coop).append("\n");

        builder.append("Tile Grid Size: ");
        if (tiles == null || tiles.isEmpty() || tiles.get(0) == null) {
            builder.append("null\n");
        } else {
            builder.append(getMaxY()).append(" rows × ").append(getMaxX()).append(" columns\n");
        }

        return builder.toString();
    }

    public Shop getShopByType(TileType type) {
        for (Shop shop : shops) {
            if (shop.getShopType() == type)
                return shop;
        }
        return null;
    }

    public void addShop(Shop shop) {
        Shop oldShop = getShopByType(shop.getShopType());
        if (oldShop != null) throw new IllegalArgumentException("Shop already exists in this Map");
        shops.add(shop);
    }
}
