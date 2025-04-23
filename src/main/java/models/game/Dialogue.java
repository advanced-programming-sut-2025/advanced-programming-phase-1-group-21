package models.game;

import models.map.Weather;
import models.time.Date;
import models.time.Season;

public class Dialogue {
    private final String message;
    private final Date start, finish;
    private final Season season;
    private final Weather weather;
    private final FriendshipLevel friendshipLevel;

    public Dialogue(String message, Date start, Date finish, Season season, Weather weather, FriendshipLevel friendshipLevel) {
        this.message = message;
        this.start = start;
        this.finish = finish;
        this.season = season;
        this.weather = weather;
        this.friendshipLevel = friendshipLevel;
    }

    public boolean canDisplay(Date date, Season season, Weather weather) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
