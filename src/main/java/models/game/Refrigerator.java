package models.game;

import models.map.Placable;

import java.util.ArrayList;

public class Refrigerator implements Placable {
    ArrayList<Item> items;

    @Override
    public boolean isWalkable() {
        return false;
    }

    @Override
    public String getSprite() {
        return "R";
    }
}
