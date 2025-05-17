package models.map;

import models.DailyUpdate;
import models.crop.Harvestable;
import models.crop.PlantedSeed;
import models.Item.Item;
import models.game.Game;

import java.io.Serializable;

public class Tile implements DailyUpdate, Serializable {
    private TileType tileType;
    private Placable placable;

    private Tile(TileType tileType, Placable placable) {
        this.tileType = tileType;
        this.placable = placable;
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

    public Item harvest() {
        if (placable instanceof Harvestable) {
            Item item = ((Harvestable) placable).harvest();
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