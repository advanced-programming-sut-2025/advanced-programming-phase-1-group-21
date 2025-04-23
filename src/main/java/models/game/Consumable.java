package models.game;

public class Consumable extends Item {
    String name;
    Energy energy;

    public Consumable(String name, ItemType itemType, int cost) {
        super(name, itemType, cost);
    }
}
