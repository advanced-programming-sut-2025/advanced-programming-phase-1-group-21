package models.tool;

import models.Item.Item;
import models.map.Coord;
import models.result.Result;

public class TrashCan extends Tool {
    public TrashCan() {
        super(ToolType.TRASHCAN);
        this.setToolMaterialType(ToolMaterialType.IRIDIUM);
    }

    @Override
    public Result<Item> use(Coord coord) {
        return null;
    }

    @Override
    public ToolMaterialType getToolMaterialType() {
        return toolMaterialType;
    }

}
