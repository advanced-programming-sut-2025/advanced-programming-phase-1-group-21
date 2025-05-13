package models.map;

public interface Placable {
    default void onPlace(Tile tile) {
        tile.setPlacable(this);
        tile.setTileType(getTileType());
    }
    TileType getTileType();
    boolean isWalkable();
    String getSprite();
}
