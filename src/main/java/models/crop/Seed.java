package models.crop;

import models.game.Item;
import models.game.ItemType;
import models.map.Tile;

public class Seed extends Item {
    SeedInfo seedInfo;


    public Seed(String name) {
        super(name, ItemType.SEED, 0, 1);
        seedInfo = SeedInfo.getSeedInfo(name);
    }
    public Seed(String name, int amount) {
        super(name, ItemType.SEED, 0, amount);
        seedInfo = SeedInfo.getSeedInfo(name);
    }

    public PlantedSeed plant(Tile tile) {
        PlantedSeed plantedSeed = new PlantedSeed(seedInfo);
        tile.setPlantedSeed(plantedSeed);
        return plantedSeed;
    }
}
