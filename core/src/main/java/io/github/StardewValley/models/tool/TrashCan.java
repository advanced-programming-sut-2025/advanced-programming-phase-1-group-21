package io.github.StardewValley.models.tool;

import io.github.StardewValley.models.Item.Item;
import io.github.StardewValley.models.map.Coord;
import io.github.StardewValley.models.result.Result;

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
