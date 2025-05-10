package models.map;

public enum MapType {
    HOUSE(20, 10),
    GREEN_HOUSE(20, 10),
    LAKE(10, 10),
    MINES(25, 15),
    FARM(30, 50),
    VILLAGE(50, 50),
    SHOP(8, 8)
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
