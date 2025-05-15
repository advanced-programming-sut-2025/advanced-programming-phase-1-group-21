package models.crop;

import models.Item.Item;
import models.map.Tile;

public interface Plantable {
    Harvestable plant(Tile tile);
}
