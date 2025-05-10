package models.map;

import java.util.ArrayList;

public abstract class Building implements Placable {
    public Map map;

    public Map getMap() {
        return map;
    }

    public abstract boolean canEnter();

    @Override
    public boolean isWalkable() {
        return false;
    }

    @Override
    public String getSprite() {
        return "H";
    }
}
