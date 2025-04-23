package models.game;

public class WaterCan extends Tool {
    private int capacity;
    private int currentWater;

    public WaterCan(String name, ItemType itemType, int cost, ToolType toolType) {
        super(name, itemType, cost, toolType);
    }
}
