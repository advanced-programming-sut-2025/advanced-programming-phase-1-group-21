package models.tool;

import models.game.Item;
import models.map.Coord;
import models.result.Result;

public class Shear extends Tool {
	public Shear() {
		super(ToolType.SHEAR);
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
