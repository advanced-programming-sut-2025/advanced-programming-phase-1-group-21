package models.crop;

import models.game.Item;
import models.game.ItemType;

public class Seed extends Item {
    SeedType seedType;
    FertilizerType fertilizerType;
    int stage;
    int day;

    public Seed(String name, ItemType itemType, int cost) {
        super(name, itemType, cost);
    }

    public void plant() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void water() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void harvest() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
