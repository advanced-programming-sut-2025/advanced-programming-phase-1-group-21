package models.game;

import models.Item.Item;
import models.map.Placable;
import models.map.TileType;

import java.util.ArrayList;

public class Refrigerator implements Placable {
    ArrayList<Item> items;
    Inventory inventory;

    public Refrigerator() {
        this.inventory = new Inventory();
    }

    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public TileType getTileType() {
        return TileType.ARTISAN;
    }

    @Override
    public boolean isWalkable() {
        return false;
    }

    @Override
    public String getSprite() {
        return "R";
    }
}
