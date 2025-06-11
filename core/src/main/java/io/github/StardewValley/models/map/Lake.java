package io.github.StardewValley.models.map;

public class Lake extends Building {

    @Override
    public void onPlace(Tile tile) {
        tile.setPlacable(this);
        tile.setTileType(TileType.BARN);
    }

    @Override
    public TileType getTileType() {
        return TileType.ARTISAN;
    }

    @Override
    public boolean canEnter() {
        return false;
    }

    @Override
    public String getSprite() {
        return "~";
    }
}
