package models.map;

import models.game.Refrigerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MapBuilder {
    private static final int MAP_WIDTH = 50;
    private static final int MAP_HEIGHT = 30;
    private final Random random = new Random();

    public void setBuildingsRandomly(List<TileType> buildings, Map map) {
        for (TileType spec : buildings) {
            boolean placed = false;
            int attempts = 0;
            int maxAttempts = 100;

            while (!placed && attempts < maxAttempts) {
                attempts++;

                int startX = random.nextInt(MAP_WIDTH - spec.getDefaultWidth());
                int startY = random.nextInt(MAP_HEIGHT - spec.getDefaultHeight());

                if (isAreaAvailable(map, startY, startX, spec.getDefaultHeight(), spec.getDefaultWidth())) {
                    placeBuilding(map, spec, startY, startX, spec.getDefaultHeight(), spec.getDefaultWidth());
                    placed = true;
                }
            }
        }
    }

    public Map buildFarm() {
        Map map = new Map(MapType.FARM);
        initializeEmptyMap(map);

        List<TileType> buildings = Arrays.asList(
                TileType.HOUSE,
                TileType.GREEN_HOUSE,
                TileType.MINES);
        //TODO push LAKE
        setBuildingsRandomly(buildings, map);
        return map;
    }

    private boolean isAreaAvailable(Map map, int startY, int startX, int height, int width) {
        for (int y = startY; y < startY + height; y++) {
            for (int x = startX; x < startX + width; x++) {
                if (map.tiles.get(y).get(x).isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    public Map buildVillage() {
        Map map = new Map(MapType.VILLAGE);
        initializeEmptyMap(map);

        List<TileType> buildings = Arrays.asList(
                TileType.BLACKSMITH,
                TileType.JOJAMART,
                TileType.PIERR_STORE,
                TileType.CARPENTER_SHOP,
                TileType.FISH_SHOP,
                TileType.MARINE_SHOP,
                TileType.STARDROP_SALOON
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

    private void placeBuilding(Map map, TileType type, int startY, int startX, int height, int width) {
        for (int y = startY; y < startY + height; y++) {
            for (int x = startX; x < startX + width; x++) {
                Tile tile = map.tiles.get(y).get(x);
                tile.setTileType(type);
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