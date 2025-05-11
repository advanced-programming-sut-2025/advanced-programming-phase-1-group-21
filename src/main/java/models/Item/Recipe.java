package models.Item;

import models.data.items.ConsumableData;
import models.data.items.ItemData;
import models.data.items.RecipeData;

public class Recipe implements Item {
    private final RecipeData data;
    private final ItemType itemType;
    private int amount;

    public Recipe(RecipeData data, ItemType itemType, int amount) {
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
}
