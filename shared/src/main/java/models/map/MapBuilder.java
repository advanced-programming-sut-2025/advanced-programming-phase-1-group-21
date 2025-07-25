package models.map;

import data.ForagingCropData;
import data.items.TreeData;
import models.Item.ItemType;
import models.Item.Sapling;
import models.crop.PlantedTree;
import models.game.NPC;
import models.game.Refrigerator;
import org.apache.commons.lang3.tuple.Pair;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MapBuilder {

    public void buildRandomTile(Map map, List<TileType> types, int L, int R, Random rand) {
        int count = rand.nextInt(L, R);
        int placed = 0;
        int attempts = 0;

        while (placed < count && attempts < count * 10) {
            ++attempts;

            Tile tile = map.getTile(Coord.getRandomCoord(map.getMaxX(), map.getMaxY(), rand));

            if (tile != null && tile.isEmpty()) {
                TileType randomForaging = types.get(rand.nextInt(types.size()));
                tile.setTileType(randomForaging);
                placed++;
            }
        }
    }

    public boolean setBuildingsRandomly(Random random, List<Pair<TileType, Placable>> buildings, Map map) {
        for (Pair<TileType, Placable> spec : buildings) {
            boolean placed = false;
            int attempts = 0;
            while (!placed) {
                if (attempts >= 50) {
                    return false;
                }
                ++attempts;
                int startX = random.nextInt(map.mapType.getWidth() - spec.getLeft().getDefaultWidth());
                int startY = random.nextInt(map.mapType.getHeight() - spec.getLeft().getDefaultHeight());

                if (isAreaAvailable(map, startY, startX, spec.getLeft().getDefaultHeight(), spec.getLeft().getDefaultWidth())) {
                    placeBuilding(map, spec, startY, startX, spec.getLeft().getDefaultHeight(), spec.getLeft().getDefaultWidth());
                    placed = true;
                }
            }
        }
        return true;
    }

    public Map buildFarm(Random random) {
        while (true) {
            Map map = new Map(MapType.FARM);
            House house = new House();
            map.build(house);
            GreenHouse greenHouse = new GreenHouse();
            map.build(greenHouse);
            map.build(new Mines(random));

            int lakesCount = random.nextInt(1,3);
            for (int i = 0; i < lakesCount; i++)
                map.build(new Lake());

            List<Pair<TileType, Placable>> buildings = new ArrayList<>(Arrays.asList(
                    Pair.of(TileType.HOUSE, map.getBuilding(House.class)),
                    Pair.of(TileType.GREEN_HOUSE, map.getBuilding(GreenHouse.class)),
                    Pair.of(TileType.MINES, map.getBuilding(Mines.class))
            ));

            for (Lake lake: map.getBuildings(Lake.class))
                buildings.add(Pair.of(TileType.LAKE, lake));

            if (!setBuildingsRandomly(random, buildings, map)) continue;
            List<TileType> foragingTypes = Arrays.asList(
                    TileType.SIMPLE_ROCK,
                    TileType.LEAF
            );
            int numberOfRemainingForagingTrees = 30;
            final int numberOfForagingTreeTypes = TreeData.getSize();
            while (numberOfRemainingForagingTrees > 0) {
                Coord coord = Coord.getRandomCoord(map.getMaxX(), map.getMaxY(), random);
                if (map.getTile(coord).isEmpty()) {
                    int id = random.nextInt(0, numberOfForagingTreeTypes); // What is this foraging tree's type?
                    int day = random.nextInt(0, 60); // How old is this foraging tree?
                    PlantedTree plantedTree = (new Sapling(TreeData.getData(id), ItemType.SAPLING, 1)).plant(map.getTile(coord));
                    map.getTile(coord).setTileType(TileType.PLANTED_TREE);
                    plantedTree.setDay(day);
                    numberOfRemainingForagingTrees--;
                }
            }

            int numberOfRemainingForagingCrops = 30;
            final int numberOfForagingCropTypes = ForagingCropData.getSize();
            while (numberOfRemainingForagingCrops > 0) {
                Coord coord = Coord.getRandomCoord(map.getMaxX(), map.getMaxY(), random);
                Tile tile = map.getTile(coord);
                if (tile.isEmpty()) {
                    int id = random.nextInt(0, numberOfForagingCropTypes); // What is this foraging crop's type?
                    ForagingCrop foragingCrop = new ForagingCrop(ForagingCropData.getData(id));

                    tile.setPlacable(foragingCrop);
                    tile.setTileType(TileType.FORAGING_CROP);
                    numberOfRemainingForagingCrops--;
                }
            }

            map.getTile(map.getMaxX() -1 , map.getMaxY() - 1).setTileType(TileType.BRIDGE);

            buildRandomTile(map, foragingTypes, 40, 70, random);

            map.setTextures();
            return map;
        }
    }

    private boolean checkNull(Map map, int startY, int startX, int height, int width) {
        for (int y = startY; y < startY + height; y++) {
            for (int x = startX; x < startX + width; x++) {
                Tile tile = map.getTile(new Coord(x, y));
                if (tile == null) return true;
            }
        }
        return false;
    }

    public boolean isAreaAvailable(Map map, int startY, int startX, int height, int width) {
        if (checkNull(map, startY, startX, height, width)) return false;
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

    public Map buildVillage(List<NPC> npcs, Random random) {
        while (true) {
            Map map = new Map(MapType.VILLAGE);

            List<Pair<TileType, Placable>> buildings = new ArrayList<>();
            for (TileType type : TileType.values()) if (type.isShop()) {
                Shop shop = new Shop(type);
                map.addShop(shop);
                buildings.add(Pair.of(type, shop));
            }

            for (NPC npc : npcs) {
                buildings.add(Pair.of(TileType.NPC_HOUSE, npc.getHouse()));
                map.build(npc.getHouse());
            }

            if (setBuildingsRandomly(random, buildings, map)) {
                map.setTextures();
                return map;
            }
        }
    }

    public void placeBuilding(Map map, Pair<TileType, Placable> pair, int startY, int startX, int height, int width) {
        for (int y = startY; y < startY + height; y++) {
            for (int x = startX; x < startX + width; x++) {
                Tile tile = map.getTile(new Coord(x, y));
                tile.setTileType(pair.getLeft());
                tile.setPlacable(pair.getRight());
            }
        }

        Tile tile = map.getTile(startX , startY + height -1);
        if(pair.getRight() instanceof Building building){
            if (building.sprite != null) {
                building.sprite.setX(tile.spriteGetter().getX());
                building.sprite.setY(tile.spriteGetter().getY());
            }
        }
    }

    public Map buildHouse(Refrigerator refrigerator) {
        Map map = new Map(MapType.HOUSE);
        map.tiles.get(9).get(19).setTileType(TileType.DOOR);
        map.tiles.get(0).get(5).setTileType(TileType.REFRIGERATOR);
        map.tiles.get(0).get(5).setPlacable(refrigerator);

        map.tiles.get(0).get(19).setTileType(TileType.SHIPPING_BIN);
        map.tiles.get(0).get(19).setPlacable(new ShippingBin());
        map.setTextures();
        return map;
    }

    public Map buildMines(Random random) {
        Map map = new Map(MapType.MINES);
        map.tiles.get(29).get(49).setTileType(TileType.DOOR);
        List<TileType> foragingTypes = Arrays.asList(
                TileType.COPPER_ROCK,
                TileType.STEEL_ROCK,
                TileType.GOLD_ROCK,
                TileType.IRIDIUM_ROCK
        );
        buildRandomTile(map, foragingTypes, map.mapType.getArea() / 10, map.mapType.getArea() / 4, random);
        map.setTextures();
        return map;
    }

    public Map buildGreenHouse() {
        Map map = new Map(MapType.GREEN_HOUSE);
        map.tiles.get(9).get(19).setTileType(TileType.DOOR);
        map.setTextures();
        return map;
    }

    public Map buildShop() {
        Map map = new Map(MapType.SHOP);
        map.tiles.get(7).get(7).setTileType(TileType.DOOR);
        map.setTextures();
        return map;
    }

    public Map buildNPCHouse(NPC npc) {
        Map map = new Map(MapType.NPC_HOUSE);
        map.tiles.get(7).get(7).setTileType(TileType.DOOR);
        map.tiles.get(7).get(0).setPlacable(npc);
        map.tiles.get(7).get(0).setTileType(TileType.NPC);
        map.setTextures();
        return map;
    }

    public Map buildAnimalHouse(MapType mapType) {
        Map map = new Map(mapType);
        map.tiles.get(7).get(7).setTileType(TileType.DOOR);
        map.setTextures();
        return map;
    }
}
