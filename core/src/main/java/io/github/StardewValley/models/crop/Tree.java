package io.github.StardewValley.models.crop;

import io.github.StardewValley.models.Item.Item;
import io.github.StardewValley.models.Item.ItemType;

public class Tree implements Harvestable {
	public Tree(String name, ItemType itemType, int cost) {
//		super(name, itemType, cost, 1);
	}

	public Tree(String name, ItemType itemType, int cost, int amount) {
//		super(name, itemType, cost, amount);
	}

	@Override
	public Item harvest() {
		throw new RuntimeException();
	}


}
