package models.Tool;

import models.game.Item;
import models.map.Coord;
import models.result.Result;

public class Shear extends Tool {
	private ToolMaterialType toolMaterialType;
	public Shear() {
		super(ToolType.SHEAR);
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
