package models.Item;

import models.data.items.*;

public interface Item {
    static Item build(String name, int amount) {
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
        }

        return null;
    }

    String getName();

    ItemType getItemType();

    int getAmount();

    void setAmount(int amount);

    void changeAmount(int change);

    boolean isSalable();

    int getPrice();
}