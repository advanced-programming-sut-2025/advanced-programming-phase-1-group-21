package models.Tool;

import models.game.Item;
import models.game.ItemType;
import models.map.Coord;
import models.result.Result;

public abstract class Tool extends Item {
    private final ToolType toolType;

    public Tool(ToolType toolType) {
        super(toolType.getName().replace("-", " ").toLowerCase(), ItemType.TOOL, -1, 1);
		this.toolType = toolType;
	}

	public ToolType getToolType() {
		return toolType;
	}

	abstract public ToolMaterialType getToolMaterialType();

	abstract protected Result<?> use(Coord coord);


}
