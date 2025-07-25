package models.tool;

import models.crop.PlantedSeed;
import models.Item.Item;
import models.crop.PlantedTree;
import models.game.Game;
import models.game.Inventory;
import models.game.Player;
import models.map.Coord;
import models.map.ForagingCrop;
import models.map.Tile;
import models.map.TileType;
import models.result.Result;

public class Scythe extends Tool {
	public Scythe() {
		super(ToolType.SCYTHE);
	}

	@Override
	public Result<Item> use(Coord coord, Game game) {
		Player player = game.getCurrentPlayer();
		player.decreaseEnergy((int)(2 * game.weatherCoefficient()));
		Tile tile = player.getMap().getTile(coord);

		if(!tile.getTileType().isForaging() && tile.getTileType() != TileType.PLANTED_SEED && tile.getTileType() != TileType.PLANTED_TREE && tile.getTileType() != TileType.FORAGING_CROP)
			return Result.success(null, "inja ke chizi nist");

		if(tile.getTileType() == TileType.LEAF){
			tile.setTileType(TileType.UNPLOWED);
			return Result.success(null, "the leaf removed from the ground");
		}

		Inventory inventory = player.getInventory();
		switch(tile.getTileType()){
			case PLANTED_SEED -> {
				PlantedSeed plantedSeed = tile.getPlacable(PlantedSeed.class);
				if (plantedSeed.isHarvestReady()) {
					String resultName = plantedSeed.getResultName();
					if (inventory.canAdd(resultName)) {
						Item result = plantedSeed.harvest(game.getCurrentPlayer());
						if (plantedSeed.isOneTime())
							tile.setPlacable(null);
						return Result.success(result, "The crop has been harvested");
					}
				}
			}
			case PLANTED_TREE -> {
				PlantedTree plantedTree = tile.getPlacable(PlantedTree.class);
				if (plantedTree.isHarvestReady()) {
					String result = plantedTree.getResultName();
					if (inventory.canAdd(result))
						return Result.success(plantedTree.harvest(game.getCurrentPlayer()), "The fruit has been harvested");
				}
			}
			case FORAGING_CROP -> {
				ForagingCrop foragingCrop = tile.getPlacable(ForagingCrop.class);
				String result = foragingCrop.getResultName();
				if (inventory.canAdd(result)) {

					Item resultItem = foragingCrop.harvest(game.getCurrentPlayer());
					tile.setPlacable(null);
					return Result.success(resultItem, "The foraging crop has been harvested");
				}
			}
		}
		return Result.success(null, "Failed to use scythe.");
	}

	@Override
	public ToolMaterialType getToolMaterialType() {
		return toolMaterialType;
	}
}
