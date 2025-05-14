package models.Item;

import models.data.items.ItemData;
import models.data.items.PlaceableData;
import models.map.Placable;
import models.map.TileType;

public class Placeable extends Item {
	private final PlaceableData data;
	private final ItemType itemType;
	private int amount;

	public Placeable(PlaceableData data, ItemType itemType, int amount) {
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

	public String getLocation() {
		return data.getLocation();
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
}
