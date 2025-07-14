package models.crop;

import models.map.Tile;

public interface Plantable {
    Harvestable plant(Tile tile);
}
