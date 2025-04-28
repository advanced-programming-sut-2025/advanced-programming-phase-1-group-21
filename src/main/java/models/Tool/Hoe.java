package models.Tool;

import models.game.Item;
import models.map.Coord;
import models.result.Result;

public class Hoe extends Tool {
	private ToolMaterialType toolMaterialType;
	public Hoe() {
		super(ToolType.HOE);
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
