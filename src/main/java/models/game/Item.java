package models.game;

public class Item {
    protected final String name;
    private final ItemType itemType;
    private final int cost;
    private int amount;

    public Item(String name, ItemType itemType, int cost, int amount) {
        this.name = name;
        this.itemType = itemType;
        this.cost = cost;
        this.amount = amount;
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void changeAmount(int change) {
        amount += change;
    }
}