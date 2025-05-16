package models.Item;

import models.data.items.*;

import java.io.Serializable;

public abstract class Item implements Serializable {
    public static Item build(String name, int amount) {
        AllItemsData itemData = AllItemsData.getData(name);
        if (itemData == null) {
            return null;
        }
        switch (itemData.getType()) {
            case PLACEABLE -> {
                return new Placeable(PlaceableData.getData(name), itemData.getType(), amount);
            }
            case CONSUMABLE -> {
                return new Consumable(ConsumableData.getData(name), itemData.getType(), amount);
            }
            case SALABLE -> {
                return new Salable(SalableData.getData(name), itemData.getType(), amount);
            }
            case SEED -> {
                return new Seed(SeedData.getData(name), itemData.getType(), amount);
            }
            case SAPLING -> {
                return new Sapling(TreeData.getData(name), itemData.getType(), amount);
            }
            case RECIPE -> {
                return new Recipe(RecipeData.getRecipeData(name).getLeft(), RecipeData.getRecipeData(name).getRight(), amount);
            }
        }

        return null;
    }

    public abstract String getName();

    public abstract ItemType getItemType();

    public abstract int getAmount();

    public abstract void setAmount(int amount);

    public abstract void changeAmount(int change);

    public abstract boolean isSalable();

    public abstract int getPrice();

    @Override
    public String toString() {
        AllItemsData itemData = AllItemsData.getData(getName());
        assert itemData != null;
        return itemData + " * " + getAmount();
    }

    public Item copy() {
        return Item.build(getName(), getAmount());
    }
}