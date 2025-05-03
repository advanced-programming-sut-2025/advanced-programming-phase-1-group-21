package models.Tool;

import models.App;
import models.game.Item;
import models.game.Player;
import models.map.Coord;
import models.map.LocationsOnMap;
import models.result.Result;
import models.result.errorTypes.GameError;

public class Hoe extends Tool {
	private ToolMaterialType toolMaterialType = ToolMaterialType.PRIMITIVE;
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

		if(player.getCurrentPlayerMap().getCurrentLocation() != LocationsOnMap.Farm)
			return Result.failure(GameError.HERE_IS_NOT_FARM);
		if(!player.currentLocationTiles().get(coord.getY()).get(coord.getX()).tileIsEmpty())
			return Result.failure(GameError.TILE_IS_NOT_EMPTY);
		player.currentLocationTiles().get(coord.getY()).get(coord.getX()).setShokhmi(true);

		return Result.success("zamin shokhmi shod");
	}

	@Override
	public ToolMaterialType getToolMaterialType() {
		return toolMaterialType;
	}
}

