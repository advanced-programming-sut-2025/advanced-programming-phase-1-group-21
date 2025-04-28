package models.Tool;

import models.game.Item;
import models.map.Coord;
import models.result.Result;

public class Shovel extends Tool {
	private ToolMaterialType toolMaterialType;
	public Shovel() {
		super(ToolType.SHOVEL);
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
