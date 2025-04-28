package models.Tool;

import models.game.Item;
import models.map.Coord;
import models.result.Result;

public class Axe extends Tool {
	private ToolMaterialType toolMaterialType;
	public Axe() {
		super(ToolType.AXE);
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
