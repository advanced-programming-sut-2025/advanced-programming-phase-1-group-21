package models.Tool;

import models.App;
import models.game.Item;
import models.map.Coord;
import models.map.Direction;
import models.map.Foraging;
import models.result.Result;

public class Axe extends Tool {
	private ToolMaterialType toolMaterialType = ToolMaterialType.PRIMITIVE;
	public Axe() {
		super(ToolType.AXE);
	}

	@Override
    public Result<Item> use(Coord coord) {
		boolean use = false;
		if(App.game.getCurrentPlayer().currentLocationTiles().get(coord.getY()).get(coord.getX()).getForaging() == null){
			App.game.getCurrentPlayer().setEnergy(App.game.getCurrentPlayer().getEnergy() - 1);
			return Result.success("tabaret khata raft");
		}

		if(App.game.getCurrentPlayer().currentLocationTiles().get(coord.getY()).get(coord.getX()).getForaging().equals(Foraging.TREE)) {
			use = true;
			App.game.getCurrentPlayer().currentLocationTiles().get(coord.getY()).get(coord.getX()).setForaging(null);
		}

		if(use){
			if(this.toolMaterialType.equals(ToolMaterialType.PRIMITIVE))
				App.game.getCurrentPlayer().setEnergy(App.game.getCurrentPlayer().getEnergy() - 5);
			if(this.toolMaterialType.equals(ToolMaterialType.COPPER))
				App.game.getCurrentPlayer().setEnergy(App.game.getCurrentPlayer().getEnergy() - 4);
			if(this.toolMaterialType.equals(ToolMaterialType.STEEL))
				App.game.getCurrentPlayer().setEnergy(App.game.getCurrentPlayer().getEnergy() - 3);
			if(this.toolMaterialType.equals(ToolMaterialType.GOLD))
				App.game.getCurrentPlayer().setEnergy(App.game.getCurrentPlayer().getEnergy() - 2);
			if(this.toolMaterialType.equals(ToolMaterialType.IRIDIUM))
				App.game.getCurrentPlayer().setEnergy(App.game.getCurrentPlayer().getEnergy() - 1);

			return Result.success("to yek derakht ro koshti");
		}
		App.game.getCurrentPlayer().setEnergy(App.game.getCurrentPlayer().getEnergy() - 1);
		return Result.success("tabaret khata raft");
	}


	@Override
	public ToolMaterialType getToolMaterialType() {
		return toolMaterialType;
	}
}
