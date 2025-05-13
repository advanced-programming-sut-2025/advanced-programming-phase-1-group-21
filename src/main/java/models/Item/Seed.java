package models.Item;

import models.crop.PlantedSeed;
import models.data.items.ItemData;
import models.data.items.SeedData;
import models.map.Tile;
import models.map.TileType;

public class Seed implements Item {
	private final SeedData data;
	private final ItemType itemType;
	private int amount;

	public Seed(SeedData data, ItemType itemType, int amount) {
		this.data = data;
		this.itemType = itemType;
		this.amount = amount;
	}

	@Override
	public String getName() {
		return data.getName();
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
	public boolean isSalable() {
		return false;
	}

	@Override
	public int getPrice() {
		return 0;
	}

	@Override
	public void changeAmount(int change) {
		amount += change;
	}

	@Override
	public String toString() {
		return data.getName() + " * " + amount;
	}

	public PlantedSeed plant(Tile tile) {
		PlantedSeed plantedSeed = new PlantedSeed(data);
		plantedSeed.onPlace(tile);
		return plantedSeed;
	}
}
