package models.tool;

import models.App;
import models.Item.Item;
import models.map.*;
import models.result.Result;
import models.result.errorTypes.GameError;
import models.skill.SkillType;

public class Axe extends Tool {
	public Axe() {
		super(ToolType.AXE);
	}

	@Override
    public Result<Item> use(Coord coord) {
		boolean use = false;
		Tile tile = App.getInstance().game.getCurrentPlayerMap().getTile(coord);
		if (tile == null) {
			return Result.failure(GameError.COORDINATE_DOESNT_EXISTS);
		}
		if(!App.getInstance().game.getCurrentPlayerMap().getTile(coord).getTileType().isForaging()) {
			App.getInstance().game.getCurrentPlayer().decreaseEnergy(1);
			return Result.success(null, "tabaret khata raft");
		}

		if(tile.getTileType() == TileType.TREE) {
			use = true;
			tile.setTileType(TileType.UNPLOWED);
			App.getInstance().game.getCurrentPlayer().getInventory().addItem(Item.build("Wood", 10));
		}

		double weatherCofficient = App.getInstance().game.weatherCoefficient();

		if(use){
			if(this.toolMaterialType.equals(ToolMaterialType.PRIMITIVE))
				App.getInstance().game.getCurrentPlayer().decreaseEnergy((int) (5 * weatherCofficient));
			else if(this.toolMaterialType.equals(ToolMaterialType.COPPER))
				App.getInstance().game.getCurrentPlayer().decreaseEnergy((int) (4 * weatherCofficient));
			else if(this.toolMaterialType.equals(ToolMaterialType.STEEL))
				App.getInstance().game.getCurrentPlayer().decreaseEnergy((int)(3 * weatherCofficient));
			else if(this.toolMaterialType.equals(ToolMaterialType.GOLD))
				App.getInstance().game.getCurrentPlayer().decreaseEnergy((int)(2 * weatherCofficient));
			else if(this.toolMaterialType.equals(ToolMaterialType.IRIDIUM))
				App.getInstance().game.getCurrentPlayer().decreaseEnergy((int) weatherCofficient);
			else if(App.getInstance().game.getCurrentPlayer().getSkillLevel(SkillType.FARMING) >= 4)
				App.getInstance().game.getCurrentPlayer().setEnergy(App.getInstance().game.getCurrentPlayer().getEnergy() + 1);
			return Result.success(null, "to yek derakht ro koshti");
		}

		App.getInstance().game.getCurrentPlayer().decreaseEnergy(1);
		return Result.success(null, "tabaret khata raft");
	}


	@Override
	public ToolMaterialType getToolMaterialType() {
		return toolMaterialType;
	}
}
