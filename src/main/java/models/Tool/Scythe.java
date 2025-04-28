package models.Tool;

import models.game.Item;
import models.map.Coord;
import models.result.Result;

public class Scythe extends Tool {
	private ToolMaterialType toolMaterialType;
	public Scythe() {
		super(ToolType.SCYTHE);
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
