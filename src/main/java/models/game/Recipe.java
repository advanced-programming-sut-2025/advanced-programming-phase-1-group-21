package models.game;

import java.util.List;

public class Recipe {
    private final List<Item> items;
    private Item result;

    public Recipe(List<Item> items, Item result) {
        this.items = items;
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