package models.Item;


import data.items.ItemData;
import data.items.RecipeData;

import java.util.ArrayList;
import java.util.Map;

public class Recipe extends Item {
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
        for (String itemName: ingredients.keySet()) {
            Item item = Item.build(itemName, ingredients.get(itemName));
            if (item == null) {
                throw new NullPointerException("Item " + itemName + " is null");
            }
            items.add(item);
        }
        return items;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Recipe{name='").append(data.getName()).append('\'');
        sb.append(", type=").append(type);
        sb.append(", amount=").append(amount);
        sb.append(", result=").append(data.getResultName());
        sb.append(", ingredients=[");

        Map<String, Integer> ingredients = data.getIngredients();
        boolean first = true;
        for (Map.Entry<String, Integer> entry : ingredients.entrySet()) {
            if (!first) sb.append(", ");
            sb.append(entry.getKey()).append(" x").append(entry.getValue());
            first = false;
        }

        sb.append("]}");
        return sb.toString();
    }
}
