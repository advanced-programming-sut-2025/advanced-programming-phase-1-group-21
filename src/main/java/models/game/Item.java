package models.game;

public class Item {
    protected String name;
    private ItemType itemType;
    private int cost, amount;

    public Item(String name, ItemType itemType, int cost) {
        this.name = name;
        this.itemType = itemType;
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }

    public String getName() {
        return name;
    }

    public ItemType getItemType() {
        return itemType;
    }
}