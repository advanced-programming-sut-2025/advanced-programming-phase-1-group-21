package models.map;

import models.game.Refrigerator;

import java.util.ArrayList;

public class House extends Building {
    Refrigerator refrigerator = new Refrigerator();

    public Refrigerator getRefrigerator() {
        return refrigerator;
    }

    public House() {
        super();
        this.map = (new MapBuilder()).buildHouse(refrigerator);
    }

    @Override
    public void onPlace(Tile tile) {
        tile.setPlacable(this);
        tile.setTileType(TileType.HOUSE);
    }

    @Override
    public TileType getTileType() {
        return TileType.ARTISAN;
    }

    @Override
    public boolean canEnter() {
        return true;
    }

    @Override
    public String getSprite() {
        return "H";
    }
}
