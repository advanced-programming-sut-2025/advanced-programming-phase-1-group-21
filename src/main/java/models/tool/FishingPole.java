package models.tool;

import models.game.Item;
import models.map.Coord;
import models.result.Result;

public class FishingPole extends Tool {
	public FishingPole() {
		super(ToolType.FISHING_POLE);
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
