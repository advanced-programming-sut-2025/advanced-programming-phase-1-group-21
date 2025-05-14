package models.Item;

import models.data.items.ItemData;
import models.data.items.TreeData;

public class Sapling extends Item {
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
}
