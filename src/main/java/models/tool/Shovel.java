package models.tool;

import models.Item.Item;
import models.map.Coord;
import models.result.Result;

public class Shovel extends Tool {
	public Shovel() {
		super(ToolType.SHOVEL);
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
