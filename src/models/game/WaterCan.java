package models.game;

public class WaterCan extends Tool {
    private int capacity;
    private int currentWater;

    public WaterCan(String name, ItemType itemType, int cost) {
        super(name, itemType, cost);
    }
}
