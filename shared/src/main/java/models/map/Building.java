package models.map;

import models.DailyUpdate;
import models.game.Game;
import models.time.Date;

import java.io.Serializable;

public abstract class Building implements Placable, DailyUpdate, Serializable {
    public Map map;

    public Map getMap() {
        return map;
    }

    public abstract boolean canEnter(Date date);

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

    @Override
    public boolean nextDay(Game g) {
        if (map == null) return false;
        return map.nextDay(g);
    }
}
