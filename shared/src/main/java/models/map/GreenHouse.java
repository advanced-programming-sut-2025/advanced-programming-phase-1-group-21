package models.map;

import models.game.Game;
import models.time.Date;

public class GreenHouse extends Building {
    private boolean isBuild = false;

    public GreenHouse() {
        this.map = (new MapBuilder()).buildGreenHouse();
    }

    public void setBuild(boolean build) {
        isBuild = build;
    }

    public boolean isBuild() {
        return isBuild;
    }

    @Override
    public TileType getTileType() {
        return TileType.GREEN_HOUSE;
    }

    @Override
    public boolean canEnter(Date date) {
        return isBuild;
    }

    @Override
    public String getSprite() {
        return "G";
    }

    @Override
    public boolean nextDay(Game g) {
        g.setWeather(Weather.SUNNY); //GreenHouse Weather is always sunny
        return super.nextDay(g);
    }
}
