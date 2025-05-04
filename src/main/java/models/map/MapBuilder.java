package models.map;

import models.game.Refrigerator;
import org.apache.commons.lang3.tuple.Pair;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MapBuilder {
    private static final int MAP_WIDTH = 30;
    private static final int MAP_HEIGHT = 50;
    private final Random random = new Random();

    public boolean setBuildingsRandomly(List<Pair<TileType, Placable>> buildings, Map map) {
        for (Pair<TileType, Placable> spec : buildings) {
            boolean placed = false;
            int attempts = 0;
            while (!placed) {
                if (attempts >= 50) {
                    return false;
                }
                ++attempts;
                int startX = random.nextInt(MAP_WIDTH - spec.getLeft().getDefaultWidth());
                int startY = random.nextInt(MAP_HEIGHT - spec.getLeft().getDefaultHeight());

                if (isAreaAvailable(map, startY, startX, spec.getLeft().getDefaultHeight(), spec.getLeft().getDefaultWidth())) {
                    placeBuilding(map, spec, startY, startX, spec.getLeft().getDefaultHeight(), spec.getLeft().getDefaultWidth());
                    placed = true;
                }
            }
        }
        return true;
    }

    public Map buildFarm() {
        while (true) {
            Map map = new Map(MapType.FARM);
            initializeEmptyMap(map);

            map.buildHouse(new House());
            map.buildGreenHouses(new GreenHouse());
            map.buildMines(new Mines());

            List<Pair<TileType, Placable>> buildings = Arrays.asList(
                    Pair.of(TileType.HOUSE, map.getHouse()),
                    Pair.of(TileType.GREEN_HOUSE, map.getGreenHouses()),
                    Pair.of(TileType.MINES, map.getMines())
            );
            //TODO push LAKE
            if (setBuildingsRandomly(buildings, map))
                return map;
        }
    }

    private boolean isAreaAvailable(Map map, int startY, int startX, int height, int width) {
        for (int y = startY; y < startY + height; y++) {
            for (int x = startX; x < startX + width; x++) {
                if (!map.getTile(new Coord(x, y)).isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    public Map buildVillage() {
        Map map = new Map(MapType.VILLAGE);
        initializeEmptyMap(map);

        List<Pair<TileType, Placable>> buildings = Arrays.asList(
                Pair.of(TileType.BLACKSMITH, null),
                Pair.of(TileType.JOJAMART, null),
                Pair.of(TileType.PIERR_STORE, null),
                Pair.of(TileType.CARPENTER_SHOP, null),
                Pair.of(TileType.FISH_SHOP, null),
                Pair.of(TileType.MARINE_SHOP, null),
                Pair.of(TileType.STARDROP_SALOON, null)
        );

        setBuildingsRandomly(buildings, map);
        return map;
    }

    private void initializeEmptyMap(Map map) {
        for (int y = 0; y < MAP_HEIGHT; y++) {
            ArrayList<Tile> row = new ArrayList<>();
            for (int x = 0; x < MAP_WIDTH; x++) {
                row.add(Tile.createEmpty());
            }
            map.tiles.add(row);
        }
    }

    private void placeBuilding(Map map, Pair<TileType, Placable> pair, int startY, int startX, int height, int width) {
        for (int y = startY; y < startY + height; y++) {
            for (int x = startX; x < startX + width; x++) {
                Tile tile = map.getTile(new Coord(x, y));
                tile.setTileType(pair.getLeft());
                tile.setPlacable(pair.getRight());
            }
        }
    }

    public Map buildHouse(Refrigerator refrigerator) {
        Map map = new Map(MapType.HOUSE);
        map.tiles = new ArrayList<>();
        for(int i = 0 ; i < 10 ; i++){
            ArrayList<Tile> thisRowTiles = new ArrayList<>();
            for(int j = 0 ; j < 20 ; j++){
                thisRowTiles.add(Tile.createEmpty());
            }
            map.tiles.add(thisRowTiles);
        }
        map.tiles.get(9).get(19).setTileType(TileType.DOOR);
        map.tiles.get(0).get(5).setTileType(TileType.REFRIGERATOR);
        map.tiles.get(9).get(19).setPlacable(refrigerator);
        return map;
    }

    public Map buildMines() {
        Map map = new Map(MapType.MINES);
        map.tiles = new ArrayList<>();
        for(int i = 0 ; i < 15; i++) {
                ArrayList<Tile> thisRowTiles = new ArrayList<>();
                for(int j = 0 ; j < 25 ; j++) {
                    thisRowTiles.add(Tile.createEmpty());
                }
                map.tiles.add(thisRowTiles);
        }
        map.tiles.get(14).get(24).setTileType(TileType.DOOR);
        return map;
    }

    public Map buildGreenHouse() {
        Map map = new Map(MapType.GREEN_HOUSE);
        map.tiles = new ArrayList<>();
        for(int i = 0 ; i < 10 ; i++){
            ArrayList<Tile> thisRowTiles = new ArrayList<>();
            for(int j = 0 ; j < 20 ; j++){
                thisRowTiles.add(Tile.createEmpty());
            }
            map.tiles.add(thisRowTiles);
        }
        map.tiles.get(9).get(19).setTileType(TileType.DOOR);
        return map;
    }
}