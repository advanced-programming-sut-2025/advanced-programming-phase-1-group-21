package models.tool;

import models.App;
import models.game.Item;
import models.game.Player;
import models.map.Coord;
import models.map.MapType;
import models.map.TileType;
import models.result.Result;
import models.result.errorTypes.GameError;

public class Hoe extends Tool {
	public Hoe() {
		super(ToolType.HOE);
	}

	@Override
	public Result<Item> use(Coord coord) {
		Player player = App.game.getCurrentPlayer();
		ToolMaterialType type = ((Tool) App.game.getCurrentPlayer().getItemInHand()).getToolMaterialType();
		if(type == ToolMaterialType.PRIMITIVE)
			player.decreaseEnergy(5);
		if(type == ToolMaterialType.COPPER)
			player.decreaseEnergy(4);
		if (type == ToolMaterialType.STEEL)
			player.decreaseEnergy(3);
		if (type == ToolMaterialType.GOLD)
			player.decreaseEnergy(2);
		if (type == ToolMaterialType.IRIDIUM)
			player.decreaseEnergy(1);

		if(player.getMap().mapType != MapType.FARM)
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

