package models.Tool;

import models.App;
import models.game.Item;
import models.map.Coord;
import models.map.LocationsOnMap;
import models.result.Result;
import models.result.errorTypes.GameError;

public class Pickaxe extends Tool {
	private ToolMaterialType toolMaterialType = ToolMaterialType.PRIMITIVE;
	public Pickaxe() {
		super(ToolType.PICKAXE);
	}

	@Override
	public Result<Item> use(Coord coord) {
		if(App.game.getCurrentPlayer().getCurrentPlayerMap().getCurrentLocation() != LocationsOnMap.Farm) {
			if(App.game.getCurrentPlayer().getCurrentPlayerMap().getCurrentLocation() != LocationsOnMap.Mines)
				return Result.failure(GameError.YOU_CANT_USE_PICKAXE_HERE, GameError.YOU_CANT_USE_PICKAXE_HERE.getMessage());
		}

		if(App.game.getCurrentPlayer().getCurrentPlayerMap().getCurrentLocation() == LocationsOnMap.Farm) {
			App.game.getCurrentPlayer().currentLocationTiles().get(coord.getY()).get(coord.getX()).setForaging(null);
			App.game.getCurrentPlayer().currentLocationTiles().get(coord.getY()).get(coord.getX()).setShokhmi(false);
			return Result.success("zamin shokhmi nashod");
		}

		App.game.getCurrentPlayer().currentLocationTiles().get(coord.getY()).get(coord.getX()).setForaging(null);
		return Result.success("the Rock removed from the ground");

	}

	@Override
	public ToolMaterialType getToolMaterialType() {
		return toolMaterialType;
	}
}
