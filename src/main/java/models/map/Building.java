package models.map;

import models.animal.Animal;

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

    public String getSimpleName() {
        return this.getClass().getSimpleName().toLowerCase();
    }

    public String getFullName() {
        return this.getSimpleName();
    }

    @Override
    public String toString() {
        return String.format(
                "%s{name='%s', map=%s}",
                getClass().getSimpleName(),
                getFullName(),
                map != null ? map.mapType : "null"
        );
    }
}
