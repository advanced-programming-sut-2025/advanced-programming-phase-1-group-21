package models.map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import models.DailyUpdate;
import models.crop.Harvestable;
import models.crop.PlantedSeed;
import models.Item.Item;
import models.game.Game;
import models.game.Player;
import org.w3c.dom.Text;

import java.io.Serializable;

public class Tile implements DailyUpdate, Serializable {
    private TileType tileType;
    private Placable placable;
    transient private Texture texture = new Texture("assets/Textures/map/SpringBasicTile.png");
    transient private Sprite sprite = new Sprite(texture);

    private Tile(TileType tileType, Placable placable) {
        this.tileType = tileType;
        this.placable = placable;
        sprite.setSize(30 , 30);
    }

    public void setTexture(MapType mapType) {
        float x = sprite.getX();
        float y = sprite.getY();
        if(mapType.equals(MapType.HOUSE)) {
            texture = new Texture("assets/Textures/Flooring/HouseFloor.png");
            sprite = new Sprite(texture);
        }
        else{
            texture = new Texture("assets/Textures/map/SpringBasicTile.png");
            sprite = new Sprite(texture);
        }

        sprite.setSize(30 , 30);
        sprite.setX(x);
        sprite.setY(y);
    }

    public Texture getTexture() {
        return texture;
    }

    public Sprite spriteGetter(){
        return sprite;
    }

    public static Tile createEmpty() {
        return new Tile(TileType.UNPLOWED, null);
    }

    public boolean isEmpty() {
        return placable == null && tileType == TileType.UNPLOWED;
    }

    public boolean isWalkable() {
        return placable == null || placable.isWalkable();
    }

    public void water() {
        if (placable instanceof PlantedSeed) {
            System.err.println("WATERED!");
            ((PlantedSeed) placable).water();
        }
    }

    public Item harvest(Player player) {
        if (placable instanceof Harvestable) {
            Item item = ((Harvestable) placable).harvest(player);
            if (placable instanceof PlantedSeed && ((PlantedSeed) placable).isOneTime()) {
                placable = null;
            }
            return item;
        }
        return null;
    }

    public boolean nextDay(Game g) {
        if (g.getWeather() == Weather.RAINY) {
            water();
        }
        if (placable instanceof DailyUpdate) {
            boolean shouldRemove = ((DailyUpdate) placable).nextDay(g);
            if (shouldRemove) {
                placable = null;
                tileType = TileType.UNPLOWED;
            }
        }
        return true;
    }

    public TileType getTileType() {
        return tileType;
    }

    public void setTileType(TileType tileType) {
        this.tileType = tileType;
    }

    public <T extends Placable> T getPlacable(Class<T> type) {
        return type.isInstance(placable) ? type.cast(placable) : null;
    }

    public boolean isPlacable() {
        return placable != null;
    }

    public void setPlacable(Placable placable) {
        this.placable = placable;
        if (placable == null) {
            setTileType(TileType.UNPLOWED);
        }
    }

    public String getSprite() {
        if (placable != null) return placable.getSprite();
        return "" + tileType.getSymbol();
    }

    @Override
    public String toString() {
        return "Tile{" +
                "tileType=" + tileType +
                ", placable=" + (placable != null ? placable.getClass().getSimpleName() : "null") +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Tile other = (Tile) obj;

        if (tileType != other.tileType) return false;
        if (placable == null && other.placable == null) return true;
        if (placable != null && other.placable != null) {
            return placable.equals(other.placable);
        }
        return false;
    }


}
