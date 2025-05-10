package models.map;

import models.game.Refrigerator;

import java.util.ArrayList;

public class House extends Building {
    Refrigerator refrigerator = new Refrigerator();

    public House() {
        super();
        this.map = (new MapBuilder()).buildHouse(refrigerator);
    }

    @Override
    public boolean canEnter() {
        return true;
    }
}
