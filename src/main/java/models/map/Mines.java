package models.map;

import java.util.ArrayList;

public class Mines extends Building {

    public Mines() {
        map = (new MapBuilder()).buildMines();
    }

    @Override
    public boolean canEnter() {
        return true;
    }
}
