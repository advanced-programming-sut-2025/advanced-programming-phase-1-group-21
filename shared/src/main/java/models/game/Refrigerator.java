package models.game;

import com.badlogic.gdx.graphics.Texture;
import models.map.Placable;
import models.map.TileType;

import java.io.Serializable;

public class Refrigerator implements Placable, Serializable {
    Inventory inventory;

    public Refrigerator() {
        this.inventory = new Inventory();
        this.inventory.inventoryType = InventoryType.BIG;
    }

    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public TileType getTileType() {
        return TileType.REFRIGERATOR;
    }

    @Override
    public boolean isWalkable() {
        return false;
    }

    @Override
    public String getSprite() {
        return "R";
    }

    @Override
    public Texture getTexture() {
        return new Texture("assets/Textures/Refrigerator/Fridge.png");
    }

    @Override
    public String toString() {
        return "Refrigerator{inventory=" + inventory + '}';
    }

}
