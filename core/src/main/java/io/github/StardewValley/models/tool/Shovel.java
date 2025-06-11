package io.github.StardewValley.models.tool;

import io.github.StardewValley.models.Item.Item;
import io.github.StardewValley.models.map.Coord;
import io.github.StardewValley.models.result.Result;

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
