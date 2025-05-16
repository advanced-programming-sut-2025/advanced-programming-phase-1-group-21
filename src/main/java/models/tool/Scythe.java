package models.tool;

import models.App;
import models.crop.Harvestable;
import models.crop.PlantedSeed;
import models.Item.Item;
import models.crop.PlantedTree;
import models.game.Inventory;
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
		Player player = App.getInstance().game.getCurrentPlayer();
		player.decreaseEnergy((int)(2 * App.getInstance().game.weatherCoefficient()));
		Tile tile = player.getMap().getTile(coord);

		if(!tile.getTileType().isForaging() && tile.getTileType() != TileType.PLANTED_SEED && tile.getTileType() != TileType.PLANTED_TREE)
			return Result.success(null, "inja ke chizi nist");

		if(tile.getTileType() == TileType.LEAF){
			tile.setTileType(TileType.UNPLOWED);
			return Result.success(null, "the leaf removed from the ground");
		}
		if (tile.getTileType() == TileType.PLANTED_SEED){
			PlantedSeed plantedSeed = tile.getPlacable(PlantedSeed.class);
			Inventory inventory = player.getInventory();
			if (plantedSeed.isHarvestReady()) {
				String resultName = plantedSeed.getResultName();
				if (inventory.canAdd(resultName)) {
					Item result = plantedSeed.harvest();
					if (plantedSeed.isOneTime())
						tile.setPlacable(null);
					return Result.success(result, "The crop has now been harvested");
				}
			}
		}
		else if (tile.getTileType() == TileType.PLANTED_TREE) {
			PlantedTree plantedTree = tile.getPlacable(PlantedTree.class);
			Inventory inventory = player.getInventory();
			if (plantedTree.isHarvestReady()) {
				String result = plantedTree.getResultName();
				if (inventory.canAdd(result))
					return Result.success(plantedTree.harvest(), "The crop has now been harvested");
			}
		}
		return Result.success(null, "Failed to use scythe.");
	}

	@Override
	public ToolMaterialType getToolMaterialType() {
		return toolMaterialType;
	}
}
