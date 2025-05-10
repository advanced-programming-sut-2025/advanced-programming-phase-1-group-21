package models.Tool;

import models.game.Item;
import models.map.Coord;
import models.result.Result;

public class MilkPail extends Tool {
	protected ToolMaterialType toolMaterialType;
	public MilkPail() {
		super(ToolType.MILK_PAIL);
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
