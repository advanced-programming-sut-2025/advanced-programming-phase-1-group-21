package models;

import models.game.Game;

public interface DailyUpdate {
    /**
     *
     * @param g
     * @return if the object should be removed, returns true else false
     */
    public boolean nextDay(Game g);
}
