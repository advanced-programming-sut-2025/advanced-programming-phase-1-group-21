package models.map;

import java.util.ArrayList;

public class Building implements Placable{
    Map map;

    public Map getMap() {
        return map;
    }

    @Override
    public boolean isWalkable() {
        return false;
    }

    @Override
    public String getSprite() {
        return "H";
    }
}
