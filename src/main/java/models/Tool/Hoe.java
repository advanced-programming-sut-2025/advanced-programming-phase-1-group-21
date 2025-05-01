package models.Tool;

import models.App;
import models.game.Item;
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
		if(((Tool) App.game.getCurrentPlayer().getItemInHand()).getToolMaterialType().equals(ToolMaterialType.PRIMITIVE))
			App.game.getCurrentPlayer().setEnergy(App.game.getCurrentPlayer().getEnergy() - 5);
		if(((Tool) App.game.getCurrentPlayer().getItemInHand()).getToolMaterialType().equals(ToolMaterialType.COPPER))
			App.game.getCurrentPlayer().setEnergy(App.game.getCurrentPlayer().getEnergy() - 4);
		if(((Tool) App.game.getCurrentPlayer().getItemInHand()).getToolMaterialType().equals(ToolMaterialType.STEEL))
			App.game.getCurrentPlayer().setEnergy(App.game.getCurrentPlayer().getEnergy() - 3);
		if(((Tool) App.game.getCurrentPlayer().getItemInHand()).getToolMaterialType().equals(ToolMaterialType.GOLD))
			App.game.getCurrentPlayer().setEnergy(App.game.getCurrentPlayer().getEnergy() - 2);
		if(((Tool) App.game.getCurrentPlayer().getItemInHand()).getToolMaterialType().equals(ToolMaterialType.IRIDIUM))
			App.game.getCurrentPlayer().setEnergy(App.game.getCurrentPlayer().getEnergy() - 1);

		if(App.game.getCurrentPlayer().getCurrentPlayerMap().getCurrentLocation() != LocationsOnMap.Farm)
			return Result.failure(GameError.HERE_IS_NOT_FARM , "You are not in the farm");
		if(!App.game.getCurrentPlayer().currentLocationTiles().get(coord.getY()).get(coord.getX()).tileIsEmpty())
			return Result.failure(GameError.TILE_IS_NOT_EMPTY , GameError.TILE_IS_NOT_EMPTY.getMessage());

		App.game.getCurrentPlayer().currentLocationTiles().get(coord.getY()).get(coord.getX()).setShokhmi(true);

		return Result.success("zamin shokhmi shod");
	}

	@Override
	public ToolMaterialType getToolMaterialType() {
		return toolMaterialType;
	}
}

