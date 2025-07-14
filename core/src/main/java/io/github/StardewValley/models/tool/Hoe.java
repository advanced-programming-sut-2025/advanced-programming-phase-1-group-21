package io.github.StardewValley.models.tool;

import io.github.StardewValley.App;
import io.github.StardewValley.models.Item.Item;
import io.github.StardewValley.models.game.Player;
import io.github.StardewValley.models.map.Coord;
import io.github.StardewValley.models.map.MapType;
import io.github.StardewValley.models.map.Tile;
import io.github.StardewValley.models.map.TileType;
import io.github.StardewValley.models.result.Result;
import io.github.StardewValley.models.result.errorTypes.GameError;

public class Hoe extends Tool {
	public Hoe() {
		super(ToolType.HOE);
	}

	@Override
	public Result<Item> use(Coord coord) {
		Player player = App.getInstance().game.getCurrentPlayer();
		Tile tile = player.getMap().getTile(coord);
		if(tile == null)
			return Result.failure(GameError.COORDINATE_DOESNT_EXISTS);
		ToolMaterialType type = ((Tool) App.getInstance().game.getCurrentPlayer().getItemInHand()).getToolMaterialType();

		double weatherCofficien  = App.getInstance().game.weatherCoefficient();

		if(type == ToolMaterialType.PRIMITIVE)
			player.decreaseEnergy((int)(5 * weatherCofficien));
		if(type == ToolMaterialType.COPPER)
			player.decreaseEnergy((int)(4*weatherCofficien));
		if (type == ToolMaterialType.STEEL)
			player.decreaseEnergy((int)(3 * weatherCofficien));
		if (type == ToolMaterialType.GOLD)
			player.decreaseEnergy((int)(2 * weatherCofficien));
		if (type == ToolMaterialType.IRIDIUM)
			player.decreaseEnergy((int)(weatherCofficien));

		if(player.getMap().mapType != MapType.FARM && player.getMap().mapType != MapType.GREEN_HOUSE)
			return Result.failure(GameError.HERE_IS_NOT_FARM);
		if(!player.getMap().getTile(coord).isEmpty())
			return Result.failure(GameError.TILE_IS_NOT_EMPTY);
		player.getMap().getTile(coord).setTileType(TileType.PLOWED);

		return Result.success(null, "zamin shokhmi shod");
	}

	@Override
	public ToolMaterialType getToolMaterialType() {
		return toolMaterialType;
	}
}

