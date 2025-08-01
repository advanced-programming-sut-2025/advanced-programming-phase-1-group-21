package models.crop;

import models.Item.Item;
import models.Item.ItemType;
import models.game.Player;

public class Tree implements Harvestable {
	public Tree() {}
	
	public Tree(String name, ItemType itemType, int cost) {
//		super(name, itemType, cost, 1);
	}

	public Tree(String name, ItemType itemType, int cost, int amount) {
//		super(name, itemType, cost, amount);
	}

	@Override
	public Item harvest(Player player) {
		throw new RuntimeException();
	}


}
