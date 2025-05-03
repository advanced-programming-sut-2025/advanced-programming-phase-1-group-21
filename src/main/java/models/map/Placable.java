package models.map;

public interface Placable {
    default void onPlace(Tile tile) {
        tile.setPlacable(this);
    }
    boolean isWalkable();
    String getSprite();
}
