package models.crop;

import models.Item.Item;
import models.game.Player;

public interface Harvestable {
    Item harvest(Player player);
}
