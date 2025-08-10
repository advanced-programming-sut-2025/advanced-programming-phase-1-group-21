package models.map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.io.Serializable;

public interface Placable extends Serializable {
    default void onPlace(Tile tile) {
        tile.setPlacable(this);
        tile.setTileType(getTileType());
    }
    TileType getTileType();
    boolean isWalkable();
    String getSprite();
    String getTexture();
    Sprite spriteGetter();
}
