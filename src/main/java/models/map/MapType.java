package models.map;

import org.apache.commons.lang3.StringUtils;

public enum MapType {
    HOUSE(20, 10),
    GREEN_HOUSE(20, 10),
    LAKE(10, 10),
    MINES(50, 50),
    FARM(30, 40),
    VILLAGE(40, 40),
    SHOP(8, 8),
    NPC_HOUSE(8, 8),
    BARN(8, 8),
    COOP(8, 8),
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

    public static MapType getMapType(String name) {
        for (MapType mapType : MapType.values()) {
            if (StringUtils.containsIgnoreCase(name, mapType.name())) {
                return mapType;
            }
        }
        return null;
    }

    public int getArea() {
        return getHeight() * getWidth();
    }

    public boolean isAnimalHouse() {
        return MapType.BARN == this || MapType.COOP == this;
    }
}
