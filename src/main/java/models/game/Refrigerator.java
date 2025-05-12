package models.game;

import models.Item.Item;
import models.map.Placable;

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
    public boolean isWalkable() {
        return false;
    }

    @Override
    public String getSprite() {
        return "R";
    }
}
