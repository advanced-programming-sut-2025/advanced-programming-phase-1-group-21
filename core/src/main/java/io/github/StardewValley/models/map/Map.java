package io.github.StardewValley.models.map;

import io.github.StardewValley.models.DailyUpdate;
import io.github.StardewValley.models.crop.PlantedSeed;
import io.github.StardewValley.models.game.Game;
import io.github.StardewValley.models.game.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Map implements DailyUpdate, Serializable {
    public ArrayList<ArrayList<Tile>> tiles;
    private ArrayList<Building> buildings = new ArrayList<>();

    public final MapType mapType;

    public <T extends Building> List<T> getBuildings(Class<T> type) {
        List<T> result = new ArrayList<>();
        for (Building b : buildings) {
            if (type.isInstance(b)) {
                result.add(type.cast(b));
            }
        }
        return result;
    }

    public <T extends Building> T getBuilding(Class<T> type) {
        List<T> buildings = getBuildings(type);
        if (buildings.isEmpty()) return null;
        if (buildings.size() > 1) throw new RuntimeException("You have multiple buildings of type " + type + " USE LIST FUNCTION");
        return buildings.get(0);
    }

    public List<Building> getBuildings() {
        return buildings;
    }

    public Coord getCoord(Placable placable) {
        for (int i = 0; i < getMaxX(); ++i) {
            for (int j = 0; j < getMaxY(); ++j) {
                Coord coord = new Coord(i, j);
                Tile tile = getTile(coord);
                if (tile != null && tile.getPlacable(Placable.class) == placable) {
                    return coord;
                }
            }
        }
        return null;
    }

    public Building getBuildingByFullName(String name) {
        for (Building building : buildings) {
            if (building.getFullName().equalsIgnoreCase(name)) return building;
        }
        return null;
    }

    public Building getBuildingBySimpleName(String name) {
        for (Building building : buildings) {
            if (building.getSimpleName().equalsIgnoreCase(name)) return building;
        }
        return null;
    }

    public void build(Building b) {
        buildings.add(b);
    }

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

    public Tile getTile(Coord coord) {
        return getElementOrNull(getElementOrNull(tiles, coord.getY()), coord.getX());
    }

    public void setTile(Coord coord, Tile tile) {
        if (getTile(coord) == null) return;
        tiles.get(coord.getY()).set(coord.getX(), tile);
    }

    public ArrayList<String> printMap(Coord center, int radius, List<Player> players) {
        ArrayList<String> output = new ArrayList<>();

        for (int j = center.getX(); j < center.getX() + radius; j++) {
            for (int i = center.getY(); i < center.getY() + radius; i++) {
                Coord coord = new Coord(i, j);
                Tile tile = getTile(coord);
                if (tile == null) continue;

                boolean playerFound = false;
                for (Player player : players) {
                    if (player.getMap() == this && player.getCoord().equals(coord)) {
                        output.add("@");
                        playerFound = true;
                        break;
                    }
                }

                if (!playerFound) {
                    output.add(tile.getSprite());
                }
            }
            if (!output.isEmpty() && !output.get(output.size() - 1).equals("\n")) {
                output.add("\n");
            }
        }

        return output;
    }

    public ArrayList<ArrayList<Tile>> getTiles() {
        return tiles;
    }

    public boolean thor(Coord coord) {
        if (mapType != MapType.FARM)
            return false;
        Tile t = getTile(coord);
        if (t == null) return false;
        if (t.getTileType().canThorAttack()) {
            setTile(coord, Tile.createEmpty());
            System.err.println("Tile with coord " + coord + " is hit by thor");
            return true;
        }
        return false;
    }

    public boolean attackCrow(Coord coord) {
        if (mapType != MapType.FARM)
            return false;
        Tile t = getTile(coord);
        if (t == null) return false;
        if (t.getTileType().canCrowAttack()) {
            setTile(coord, Tile.createEmpty());
            return true;
        }
        return false;
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
    @Override
    public boolean nextDay(Game g) {
        if (mapType == MapType.MINES) {
            MapBuilder mb = new MapBuilder();
            List<TileType> foragingTypes = Arrays.asList(
                    TileType.COPPER_ROCK,
                    TileType.STEEL_ROCK,
                    TileType.GOLD_ROCK,
                    TileType.IRIDIUM_ROCK
            );
            mb.buildRandomTile(this, foragingTypes, 1, 3);
        }

        if (g.getWeather() == Weather.STORM) {
            for (int i = 0; i < NUMBER_OF_THORS_PER_DAY; i++)
                thor(getRandomCoord());
        }


        if (mapType == MapType.FARM || mapType == MapType.VILLAGE) {
            List<Tile> plantedSeedTiles = new ArrayList<>();
            for (int i = 0; i < mapType.x; i++) {
                for (int j = 0; j < mapType.y; j++) {
                    Coord coord = new Coord(i, j);
                    Tile tile = getTile(coord);
                    if (tile != null && tile.getPlacable(PlantedSeed.class) != null) {
                        plantedSeedTiles.add(tile);
                    }
                }
            }

            Random rand = new Random();

            int crows = plantedSeedTiles.size() / 10;
            for (int i = 0; i < crows; i++) if (rand.nextInt(0, 5) == 0) {
                Tile t = plantedSeedTiles.get(rand.nextInt(plantedSeedTiles.size()));
                System.err.println("CROW ATTACK");
                t.setPlacable(null);
                t.setTileType(TileType.UNPLOWED);
            }
        }

        for (int i = 0; i < mapType.x; i++) {
            for (int j = 0; j < mapType.y; j++) {
                Coord coord = new Coord(i, j);
                Tile tile = getTile(coord);
                tile.nextDay(g);
            }
        }

//
//        for (ArrayList<Tile> row : tiles) {
//            for (Tile tile : row) {
//                tile.nextDay(g);
//            }
//        }

        for (Building building : buildings) {
            building.nextDay(g);
        }

        return false;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("Map Summary:\n");
        builder.append("Map Type: ").append(mapType).append("\n");

        builder.append("Buildings: ").append(mapType).append("\n");
        for (Building b : buildings) {
            builder.append(b.toString()).append("\n");
        }
        builder.append("\n");

        builder.append("Tile Grid Size: ");
        if (tiles == null || tiles.isEmpty() || tiles.get(0) == null) {
            builder.append("null\n");
        } else {
            builder.append(getMaxY()).append(" rows × ").append(getMaxX()).append(" columns\n");
        }

        return builder.toString();
    }

    public Shop getShopByType(TileType type) {
        List<Shop> shops = getBuildings(Shop.class);
        for (Shop shop : shops) {
            if (shop.getShopType() == type)
                return shop;
        }
        return null;
    }

    public void addShop(Shop shop) {
        Shop oldShop = getShopByType(shop.getShopType());
        if (oldShop != null) throw new IllegalArgumentException("Shop already exists in this Map");
        build(shop);
    }
}
