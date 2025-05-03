package models.map;

import java.util.ArrayList;

public class GreenHouse extends Building {
    private boolean isBuild = false;

    public GreenHouse() {

    }

    public void setBuild(boolean build) {
        isBuild = build;
    }

    public boolean isBuild() {
        return isBuild;
    }
}
