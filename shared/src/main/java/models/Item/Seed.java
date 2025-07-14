package models.Item;

import data.items.ItemData;
import data.items.SeedData;
import models.crop.Plantable;
import models.crop.PlantedSeed;
import models.map.Tile;

public class Seed extends Item implements Plantable {
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

	public PlantedSeed plant(Tile tile) {
		System.err.println("HERE");
		PlantedSeed plantedSeed = new PlantedSeed(data);
		tile.setPlacable(plantedSeed);
		tile.setTileType(plantedSeed.getTileType());
		return plantedSeed;
	}
}
