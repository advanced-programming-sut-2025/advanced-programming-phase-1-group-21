package io.github.StardewValley.models.Item;

import io.github.StardewValley.models.crop.Plantable;
import io.github.StardewValley.models.crop.PlantedTree;
import io.github.StardewValley.data.items.ItemData;
import io.github.StardewValley.data.items.TreeData;
import io.github.StardewValley.models.map.Tile;

public class Sapling extends Item implements Plantable {
	private final TreeData data;
	private final ItemType itemType;
	private int amount;

	public Sapling(TreeData data, ItemType itemType, int amount) {
		this.data = data;
		this.itemType = itemType;
		this.amount = amount;
	}

	@Override
	public String getName() {
		return data.getSaplingName();
	}

	@Override
	public ItemType getItemType() {
		return itemType;
	}

	private ItemData getData() {
		return data;
	}

	@Override
	public int getAmount() {
		return amount;
	}

	@Override
	public void setAmount(int amount) {
		this.amount = amount;
	}

	@Override
	public void changeAmount(int change) {
		amount += change;
	}

	@Override
	public boolean isSalable() {
		return false;
	}

	@Override
	public int getPrice() {
		return 0;
	}

	public PlantedTree plant(Tile tile) {
		PlantedTree plantedTree = new PlantedTree(data);
		plantedTree.onPlace(tile);
		return plantedTree;

	}
}
