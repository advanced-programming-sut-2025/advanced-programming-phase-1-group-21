package models.crop;

import models.game.Item;
import models.game.ItemType;

public class Tree extends Item implements Harvestable {
	public Tree(String name, ItemType itemType, int cost) {
		super(name, itemType, cost, 1);
	}

	public Tree(String name, ItemType itemType, int cost, int amount) {
		super(name, itemType, cost, amount);
	}

	@Override
	public Item harvest() {
		throw new RuntimeException();
	}
}
