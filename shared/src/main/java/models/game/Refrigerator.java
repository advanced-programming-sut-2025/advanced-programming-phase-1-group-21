package models.game;

import Asset.SharedAssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import models.map.Placable;
import models.map.TileType;

import java.io.Serializable;

public class Refrigerator implements Placable, Serializable {
    Inventory inventory;
    Texture texture = SharedAssetManager.getRefrigerator();
    Sprite sprite = new Sprite(texture);

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
        return texture;
    }

    @Override
    public Sprite spriteGetter() {
        return sprite;
    }

    @Override
    public String toString() {
        return "Refrigerator{inventory=" + inventory + '}';
    }

}
