package models.Tool;

import models.App;
import models.game.Item;
import models.game.Player;
import models.map.Coord;
import models.map.Foraging;
import models.map.Tile;
import models.result.Result;

public class Scythe extends Tool {
	private ToolMaterialType toolMaterialType;
	public Scythe() {
		super(ToolType.SCYTHE);
	}

	@Override
	protected Result<Item> use(Coord coord) {
		Player player = App.game.getCurrentPlayer();
		player.decreaseEnergy(2);
		Tile tile = player.currentLocationTiles().get(coord.getY()).get(coord.getX());

		if(tile.getForaging() == null && tile.getSeed() == null)
			return Result.success("inja ke chizi nist");

		if(tile.getForaging() == Foraging.LEAF){
			tile.setForaging(null);
			return Result.success("the leaf removed from the ground");
		}

		if(tile.getSeed() != null){
			tile.setSeed(null);
			return Result.success("The crop has now been harvested");
		}

		return Result.success(null);
	}

	@Override
	public ToolMaterialType getToolMaterialType() {
		return toolMaterialType;
	}
}
