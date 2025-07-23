package models.map;

import org.apache.commons.lang3.StringUtils;

public enum MapType {
    HOUSE(20, 10 , 100 , 100),
    GREEN_HOUSE(20, 10 , 0 , 0),
    LAKE(10, 10 , 0 , 0),
    MINES(50, 30 , 0 , 0),
    FARM(60, 25 , 0 , 0),
    VILLAGE(80, 30 , 0 , 0),
    SHOP(8, 8 , 0 , 0),
    NPC_HOUSE(8, 8 , 0 , 0),
    BARN(8, 8 , 0 , 0),
    COOP(8, 8 , 0 , 0),
    ;

    public final int x;
    public final int y;
    public final int distanceX;
    public final int distanceY;

    MapType(int x, int y , int distanceX , int distanceY) {
        this.x = x;
        this.y = y;
        this.distanceX = distanceX;
        this.distanceY = distanceY;
    }

    public int getWidth() {
        return x;
    }

    public int getHeight() {
        return y;
    }

    public int getDistanceX() {
        return distanceX;
    }

    public int getDistanceY() {
        return distanceY;
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
