package models.crop;

import models.game.Item;
import models.game.ItemType;
import models.map.Coord;
import models.map.Tile;

public class Tree extends Item {
	Tile tile;
	public Tree(String name, ItemType itemType, int cost) {
		super(name, itemType, cost);
	}
}
