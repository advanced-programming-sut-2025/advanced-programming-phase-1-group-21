package models.tool;

import models.Item.Item;
import models.game.Game;
import models.game.Player;
import models.map.Coord;
import models.map.MapType;
import models.map.Tile;
import models.map.TileType;
import models.result.Result;
import models.result.errorTypes.GameError;

public class Hoe extends Tool {
	public Hoe() {
		super(ToolType.HOE);
	}

	@Override
	public Result<Item> use(Coord coord, Game game, Player player) {
		Tile tile = player.getMap().getTile(coord);
		if(tile == null)
			return Result.failure(GameError.COORDINATE_DOESNT_EXISTS);
		ToolMaterialType type = ((Tool) player.getItemInHand()).getToolMaterialType();

		double weatherCofficien  = game.weatherCoefficient();

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
		tile.loadOnTileTexture();

		return Result.success(null, "zamin shokhmi shod");
	}

	@Override
	public ToolMaterialType getToolMaterialType() {
		return toolMaterialType;
	}
}

