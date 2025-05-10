package models.tool;

import models.App;
import models.crop.PlantedSeed;
import models.Item.Item;
import models.game.Player;
import models.map.Coord;
import models.map.Tile;
import models.map.TileType;
import models.result.Result;

public class Scythe extends Tool {
	public Scythe() {
		super(ToolType.SCYTHE);
	}

	@Override
	public Result<Item> use(Coord coord) {
		Player player = App.game.getCurrentPlayer();
		player.decreaseEnergy(2);
		Tile tile = player.getMap().getTile(coord);

		if(!tile.getTileType().isForaging() && tile.getPlacable(PlantedSeed.class) == null)
			return Result.success(null, "inja ke chizi nist");

		if(tile.getTileType() == TileType.LEAF){
			tile.setTileType(TileType.UNPLOWED);
			return Result.success(null, "the leaf removed from the ground");
		}
		if(tile.getPlacable(PlantedSeed.class) != null){
			tile.setPlacable(null);
			return Result.success(null, "The crop has now been harvested");
		}
		return Result.success(null, "");
	}

	@Override
	public ToolMaterialType getToolMaterialType() {
		return toolMaterialType;
	}
}
