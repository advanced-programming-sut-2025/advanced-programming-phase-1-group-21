package models.Item;

import models.data.items.*;

public class Consumable implements Item {
    private final ConsumableData data;
    private final ItemType itemType;
    private int amount;

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

    @Override
    public String toString() {
        return data.getName() + " * " + amount;
    }
}
