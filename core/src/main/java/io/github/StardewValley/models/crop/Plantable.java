package io.github.StardewValley.models.crop;

import io.github.StardewValley.models.map.Tile;

public interface Plantable {
    Harvestable plant(Tile tile);
}
