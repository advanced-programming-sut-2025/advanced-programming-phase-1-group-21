package models.tool;

import models.Item.Item;
import models.crop.PlantedTree;
import models.game.Game;
import models.map.Coord;
import models.map.Tile;
import models.map.TileType;
import models.result.Result;
import models.result.errorTypes.GameError;
import models.skill.SkillType;

public class Axe extends Tool {
	public Axe() {
		super(ToolType.AXE);
	}

	@Override
    public Result<Item> use(Coord coord, Game game) {
		boolean use = false;
		Tile tile = game.getCurrentPlayerMap().getTile(coord);
		if (tile == null) {
			return Result.failure(GameError.COORDINATE_DOESNT_EXISTS);
		}
		System.out.println(tile.getTileType());
		if(tile.getPlacable(PlantedTree.class) != null) {
			use = true;
			tile.setTileType(TileType.UNPLOWED);
			tile.setPlacable(null);
			game.getCurrentPlayer().getInventory().addItem(Item.build("Wood", 10));
		}

		double weatherCofficient = game.weatherCoefficient();

		if(use){
			if(this.toolMaterialType.equals(ToolMaterialType.PRIMITIVE))
				game.getCurrentPlayer().decreaseEnergy((int) (5 * weatherCofficient));
			else if(this.toolMaterialType.equals(ToolMaterialType.COPPER))
				game.getCurrentPlayer().decreaseEnergy((int) (4 * weatherCofficient));
			else if(this.toolMaterialType.equals(ToolMaterialType.STEEL))
				game.getCurrentPlayer().decreaseEnergy((int)(3 * weatherCofficient));
			else if(this.toolMaterialType.equals(ToolMaterialType.GOLD))
				game.getCurrentPlayer().decreaseEnergy((int)(2 * weatherCofficient));
			else if(this.toolMaterialType.equals(ToolMaterialType.IRIDIUM))
				game.getCurrentPlayer().decreaseEnergy((int) weatherCofficient);
			else if(game.getCurrentPlayer().getSkillLevel(SkillType.FARMING) >= 4)
				game.getCurrentPlayer().setEnergy(game.getCurrentPlayer().getEnergy() + 1);
			return Result.success(null, "to yek derakht ro koshti");
		}

		game.getCurrentPlayer().decreaseEnergy(1);
		return Result.success(null, "tabaret khata raft");
	}


	@Override
	public ToolMaterialType getToolMaterialType() {
		return toolMaterialType;
	}
}
