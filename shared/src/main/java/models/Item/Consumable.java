package models.Item;


import data.items.ConsumableData;
import data.items.ItemData;

public class Consumable extends Item {
    private ConsumableData data;
    private ItemType itemType;
    private int amount;

    public Consumable() {}

    public Consumable(ConsumableData data, ItemType itemType, int amount) {
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

    public int getEnergy() {
        return data.getEnergy();
    }

    @Override
    public void changeAmount(int change) {
        amount += change;
    }
}
