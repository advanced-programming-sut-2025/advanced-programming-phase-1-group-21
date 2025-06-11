package io.github.StardewValley.models.map;

public class Mines extends Building {

    public Mines() {
        map = (new MapBuilder()).buildMines();
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
        return "M";
    }
}
