package io.github.StardewValley.models.map;

import java.io.Serializable;

public interface Placable extends Serializable {
    default void onPlace(Tile tile) {
        tile.setPlacable(this);
        tile.setTileType(getTileType());
    }
    TileType getTileType();
    boolean isWalkable();
    String getSprite();
}
