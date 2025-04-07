package models.game;

import java.util.List;

public class Recipe {
    private final List<Item> items;
    private Item result;

    public Recipe(List<Item> items, Item result) {
        this.items = items;
        this.result = result;
    }
}