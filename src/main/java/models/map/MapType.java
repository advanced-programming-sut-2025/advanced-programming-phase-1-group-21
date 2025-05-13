package models.map;

public enum MapType {
    HOUSE(20, 10),
    GREEN_HOUSE(20, 10),
    LAKE(10, 10),
    MINES(25, 15),
    FARM(30, 50),
    VILLAGE(60, 60),
    SHOP(8, 8),
    NPC_HOUSE(8, 8),
    BARN(4, 7),
    COOP(3, 6),
    ;

    public final int x;
    public final int y;

    MapType(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getWidth() {
        return x;
    }

    public int getHeight() {
        return y;
    }
}
