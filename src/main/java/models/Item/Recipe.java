package models.Item;

import models.data.items.ConsumableData;
import models.data.items.ItemData;
import models.data.items.RecipeData;

import java.util.ArrayList;
import java.util.Map;

public class Recipe implements Item {
    private final RecipeData data;
    public final RecipeType type;
    public final ItemType itemType;
    private int amount;

    public Recipe(RecipeData data, RecipeType recipeType, int amount) {
        this.data = data;
        this.itemType = ItemType.RECIPE;
        this.amount = amount;
        this.type = recipeType;
    }

    public RecipeType getType() {
        return type;
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

    public Item getResult() {
        return Item.build(data.getResultName(), 1);
    }

    public ArrayList<Item> getItems() {
        ArrayList<Item> items = new ArrayList<>();
        Map <String, Integer> ingredients = data.getIngredients();
        for (String itemName: ingredients.keySet())
            items.add(Item.build(itemName, ingredients.get(itemName)));
        return items;
    }

    @Override
    public String toString() {
        return data.getName() + " * " + amount;
    }
}
