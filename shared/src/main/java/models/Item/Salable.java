package models.Item;


import data.items.ItemData;
import data.items.SalableData;

public class Salable extends Item {
	private SalableData data;
	private ItemType itemType;
	private int amount;

	public Salable() {}

	public Salable(SalableData data, ItemType itemType, int amount) {
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
		return true;
	}

	@Override
	public int getPrice() {
		return data.getPrice();
	}

	@Override
	public void changeAmount(int change) {
		amount += change;
	}
}
