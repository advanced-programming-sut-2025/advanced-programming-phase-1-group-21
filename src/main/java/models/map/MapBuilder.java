package models.map;

import models.App;
import models.game.Refrigerator;
import org.apache.commons.lang3.tuple.Pair;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MapBuilder {
    private static final int MAP_WIDTH = 30;
    private static final int MAP_HEIGHT = 50;

    public boolean setBuildingsRandomly(List<Pair<TileType, Placable>> buildings, Map map) {
        for (Pair<TileType, Placable> spec : buildings) {
            boolean placed = false;
            int attempts = 0;
            while (!placed) {
                if (attempts >= 50) {
                    return false;
                }
                ++attempts;
                int startX = App.random.nextInt(MAP_WIDTH - spec.getLeft().getDefaultWidth());
                int startY = App.random.nextInt(MAP_HEIGHT - spec.getLeft().getDefaultHeight());

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

            map.buildHouse(new House());
            map.buildGreenHouses(new GreenHouse());
            map.buildMines(new Mines());

            int lakesCount = App.random.nextInt(1,3);
            for (int i = 0; i < lakesCount; i++)
                map.buildLakes(new Lake());

            List<Pair<TileType, Placable>> buildings = new ArrayList<>(Arrays.asList(
                    Pair.of(TileType.HOUSE, map.getHouse()),
                    Pair.of(TileType.GREEN_HOUSE, map.getGreenHouses()),
                    Pair.of(TileType.MINES, map.getMines())
            ));

            for (Lake lake: map.getLakes())
                buildings.add(Pair.of(TileType.LAKE, lake));

            if (setBuildingsRandomly(buildings, map))
                return map;
        }
    }

    private boolean isAreaAvailable(Map map, int startY, int startX, int height, int width) {
        for (int y = startY - 1; y <= startY + height; y++) {
            for (int x = startX - 1; x <= startX + width; x++) {
                Tile tile = map.getTile(new Coord(x, y));
                if (tile != null && !tile.isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    public Map buildVillage() {
        while (true) {
            Map map = new Map(MapType.VILLAGE);

            //TODO add shops like this into map:
            // map.addShop();

            List<Pair<TileType, Placable>> buildings = Arrays.asList(
                    Pair.of(TileType.BLACKSMITH, map.getShopByType(TileType.BLACKSMITH)),
                    Pair.of(TileType.JOJAMART, map.getShopByType(TileType.JOJAMART)),
                    Pair.of(TileType.PIERR_STORE, map.getShopByType(TileType.PIERR_STORE)),
                    Pair.of(TileType.CARPENTER_SHOP, map.getShopByType(TileType.CARPENTER_SHOP)),
                    Pair.of(TileType.FISH_SHOP, map.getShopByType(TileType.FISH_SHOP)),
                    Pair.of(TileType.MARINE_SHOP, map.getShopByType(TileType.MARINE_SHOP)),
                    Pair.of(TileType.STARDROP_SALOON, map.getShopByType(TileType.STARDROP_SALOON))
            );

            if (setBuildingsRandomly(buildings, map))
                return map;
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
        map.tiles.get(9).get(19).setTileType(TileType.DOOR);
        map.tiles.get(0).get(5).setTileType(TileType.REFRIGERATOR);
        map.tiles.get(0).get(5).setPlacable(refrigerator);
        return map;
    }

    public Map buildMines() {
        Map map = new Map(MapType.MINES);
        map.tiles.get(14).get(24).setTileType(TileType.DOOR);
        return map;
    }

    public Map buildGreenHouse() {
        Map map = new Map(MapType.GREEN_HOUSE);
        map.tiles.get(9).get(19).setTileType(TileType.DOOR);
        return map;
    }

    public Map buildShop() {
        Map map = new Map(MapType.SHOP);
        map.tiles.get(7).get(7).setTileType(TileType.DOOR);
        return map;
    }
}