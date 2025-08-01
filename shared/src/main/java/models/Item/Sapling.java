package models.Item;

import data.items.ItemData;
import data.items.TreeData;
import models.crop.Plantable;
import models.crop.PlantedTree;
import models.map.Tile;

public class Sapling extends Item implements Plantable {
	private TreeData data;
	private ItemType itemType;
	private int amount;

	public Sapling() {}

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
