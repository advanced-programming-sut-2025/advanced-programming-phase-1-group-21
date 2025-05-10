package models.tool;

import models.game.Item;
import models.game.ItemType;
import models.map.Coord;
import models.result.Result;

public abstract class Tool extends Item {
    private final ToolType toolType;
	public ToolMaterialType toolMaterialType = ToolMaterialType.PRIMITIVE;


	public Tool(ToolType toolType) {
        super(toolType.getName().replace("-", " ").toLowerCase(), ItemType.TOOL, -1, 1);
		this.toolType = toolType;
	}

	public ToolType getToolType() {
		return toolType;
	}

	public void setToolMaterialType(ToolMaterialType toolMaterialType) {
		this.toolMaterialType = toolMaterialType;
	}

	abstract public ToolMaterialType getToolMaterialType();

	abstract public Result<Item> use(Coord coord);
}
