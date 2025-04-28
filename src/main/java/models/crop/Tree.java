package models.crop;

import models.game.Item;
import models.game.ItemType;
import models.map.Tile;

public class Tree extends Item {
	public Tree(String name, ItemType itemType, int cost) {
		super(name, itemType, cost, 1);
	}

	public Tree(String name, ItemType itemType, int cost, int amount) {
		super(name, itemType, cost, amount);
	}

}
