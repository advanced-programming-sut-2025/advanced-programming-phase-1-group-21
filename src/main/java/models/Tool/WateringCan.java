package models.Tool;

import models.game.Item;
import models.map.Coord;
import models.result.Result;

public class WateringCan extends Tool {
    private int capacity;
    private int currentWater;

    private ToolMaterialType toolMaterialType;
    public WateringCan() {
        super(ToolType.WATERING_CAN);
    }

    @Override
    protected Result<Item> use(Coord coord) {
        return null;
    }

    @Override
    public ToolMaterialType getToolMaterialType() {
        return toolMaterialType;
    }
}
