package models.Tool;

import models.App;
import models.game.Item;
import models.map.Coord;
import models.map.Foraging;
import models.result.Result;

public class Scythe extends Tool {
	private ToolMaterialType toolMaterialType;
	public Scythe() {
		super(ToolType.SCYTHE);
	}

	@Override
	protected Result<Item> use(Coord coord) {
		App.game.getCurrentPlayer().setEnergy(App.game.getCurrentPlayer().getEnergy() - 2);
		if((App.game.getCurrentPlayer().currentLocationTiles().get(coord.getY()).get(coord.getX()).getForaging() == null)
			&& (App.game.getCurrentPlayer().currentLocationTiles().get(coord.getY()).get(coord.getX()).getSeed() == null))
			return Result.success("inja ke chizi nist");

		if(App.game.getCurrentPlayer().currentLocationTiles().get(coord.getY()).get(coord.getX()).getForaging() == Foraging.LEAF){
			App.game.getCurrentPlayer().currentLocationTiles().get(coord.getY()).get(coord.getX()).setForaging(null);
			return Result.success("the leaf removed from the ground");
		}

		if(App.game.getCurrentPlayer().currentLocationTiles().get(coord.getY()).get(coord.getX()).getSeed() != null){
			App.game.getCurrentPlayer().currentLocationTiles().get(coord.getY()).get(coord.getX()).setSeed(null);
			return Result.success("The crop has now been harvested");
		}

		return Result.success("what?");

	}

	@Override
	public ToolMaterialType getToolMaterialType() {
		return toolMaterialType;
	}
}
