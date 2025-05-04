package models.Tool;

import models.App;
import models.game.Item;
import models.game.ItemType;
import models.map.*;
import models.result.Result;
import models.result.errorTypes.GameError;

public class Axe extends Tool {
	private ToolMaterialType toolMaterialType = ToolMaterialType.PRIMITIVE;
	public Axe() {
		super(ToolType.AXE);
	}

	@Override
    public Result<Item> use(Coord coord) {
		boolean use = false;
		Tile tile = App.game.getCurrentPlayerMap().getTile(coord);
		if (tile == null) {
			return Result.failure(GameError.COORDINATE_DOESNT_EXISTS);
		}
		if(!App.game.getCurrentPlayerMap().getTile(coord).getTileType().isForaging()) {
			App.game.getCurrentPlayer().decreaseEnergy(1);
			return Result.success("tabaret khata raft");
		}

		if(tile.getTileType() == TileType.TREE) {
			use = true;
			tile.setTileType(TileType.UNPLOWED);
			App.game.getCurrentPlayer().getInventory().addItem(new Item("wood" , ItemType.WOOD , 5 , 10));
		}

		if(use){
			if(this.toolMaterialType.equals(ToolMaterialType.PRIMITIVE))
				App.game.getCurrentPlayer().decreaseEnergy(5);
			if(this.toolMaterialType.equals(ToolMaterialType.COPPER))
				App.game.getCurrentPlayer().decreaseEnergy(4);
			if(this.toolMaterialType.equals(ToolMaterialType.STEEL))
				App.game.getCurrentPlayer().decreaseEnergy(3);
			if(this.toolMaterialType.equals(ToolMaterialType.GOLD))
				App.game.getCurrentPlayer().decreaseEnergy(2);
			if(this.toolMaterialType.equals(ToolMaterialType.IRIDIUM))
				App.game.getCurrentPlayer().decreaseEnergy(1);
			return Result.success("to yek derakht ro koshti");
		}

		App.game.getCurrentPlayer().decreaseEnergy(1);
		return Result.success("tabaret khata raft");
	}


	@Override
	public ToolMaterialType getToolMaterialType() {
		return toolMaterialType;
	}
}
