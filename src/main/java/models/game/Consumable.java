package models.game;

public class Consumable extends Item {
    String name;
    Energy energy;

    public Consumable(String name, ItemType itemType, int cost) {
        super(name, itemType, cost, 1);
    }

    public Consumable(String name, ItemType itemType, int cost, int amount) {
        super(name, itemType, cost, amount);
    }
}
