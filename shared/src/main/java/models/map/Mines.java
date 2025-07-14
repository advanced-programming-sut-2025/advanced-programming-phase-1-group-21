package models.map;

import models.time.Date;

import java.util.Random;

public class Mines extends Building {

    public Mines(Random rand) {
        map = (new MapBuilder()).buildMines(rand);
    }

    @Override
    public TileType getTileType() {
        return TileType.ARTISAN;
    }

    @Override
    public boolean canEnter(Date date) {
        return true;
    }

    @Override
    public String getSprite() {
        return "M";
    }
}
