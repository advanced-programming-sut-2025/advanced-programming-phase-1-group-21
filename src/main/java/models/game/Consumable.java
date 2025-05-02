package models.game;

public class Consumable extends Item {
    Energy energy;

    public Consumable(String name, int cost) {
        super(name, ItemType.CONSUMABLE, cost, 1);
    }

    public Consumable(String name, int cost, int amount) {
        super(name, ItemType.CONSUMABLE, cost, amount);
    }
}
