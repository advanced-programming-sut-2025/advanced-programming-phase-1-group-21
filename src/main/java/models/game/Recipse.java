package models.game;

import models.Item.Item;

import java.util.List;

public class Recipse {
    private String recipeName;
    private final List<Item> items;
    private Item result;

    public Recipse(String name, List<Item> items, Item result) {
        this.recipeName = name;
        this.items = items;
        this.result = result;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public List<Item> getItems() {
        return items;
    }

    public Item getResult() {
        return result;
    }

    public void setResult(Item result) {
        this.result = result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            builder.append(item.toString());

            if (i < items.size() - 1) {
                builder.append(" + ");
            }
        }

        builder.append(" = ").append(result.toString());

        return builder.toString();
    }
}