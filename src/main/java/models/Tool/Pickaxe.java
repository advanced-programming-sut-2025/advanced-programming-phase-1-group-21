package models.Tool;

import models.game.Item;
import models.map.Coord;
import models.result.Result;

public class Pickaxe extends Tool {
	private ToolMaterialType toolMaterialType;
	public Pickaxe() {
		super(ToolType.PICKAXE);
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
