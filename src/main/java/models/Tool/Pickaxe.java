package models.Tool;

import models.App;
import models.game.Item;
import models.game.Player;
import models.map.Coord;
import models.map.Foraging;
import models.map.LocationsOnMap;
import models.map.Tile;
import models.result.Result;
import models.result.errorTypes.GameError;

public class Pickaxe extends Tool {
	private ToolMaterialType toolMaterialType = ToolMaterialType.PRIMITIVE;
	public Pickaxe() {
		super(ToolType.PICKAXE);
	}

	@Override
	public Result<Item> use(Coord coord) {
		Player player = App.game.getCurrentPlayer();
		LocationsOnMap loc = player.getCurrentPlayerMap().getCurrentLocation();
		if(loc != LocationsOnMap.Farm && loc != LocationsOnMap.Mines)
				return Result.failure(GameError.YOU_CANT_USE_PICKAXE_HERE);

		int use = 0;
		Tile tile = player.currentLocationTiles().get(coord.getY()).get(coord.getX());
		if(tile.isShokhmi()) {
			tile.setShokhmi(false);
			use = 1;
		}
		if(tile.getForaging() != null) {
			if (toolMaterialType == ToolMaterialType.IRIDIUM || toolMaterialType == ToolMaterialType.GOLD) {
				if (tile.getForaging().equals(Foraging.SIMPLE_ROCK)) {
					use = 2;
					tile.setForaging(null);
				}
				else if (tile.getForaging().equals(Foraging.COPPER_ROCK)) {
					use = 2;
					tile.setForaging(null);
				}
				else if (tile.getForaging().equals(Foraging.STEEL_ROCK)) {
					use = 2;
					tile.setForaging(null);
				}
				else if (tile.getForaging().equals(Foraging.GOLD_ROCK)) {
					use = 2;
					tile.setForaging(null);
				}
				else if (tile.getForaging().equals(Foraging.IRIDIUM_ROCK)) {
					use = 2;
					tile.setForaging(null);
				}
			}
			else if (this.toolMaterialType.equals(ToolMaterialType.STEEL)) {
				if (tile.getForaging().equals(Foraging.SIMPLE_ROCK)) {
					use = 2;
					tile.setForaging(null);
				}
				else if (tile.getForaging().equals(Foraging.COPPER_ROCK)) {
					use = 2;
					tile.setForaging(null);
				}
				else if (tile.getForaging().equals(Foraging.STEEL_ROCK)) {
					use = 2;
					tile.setForaging(null);
				}
				else if (tile.getForaging().equals(Foraging.GOLD_ROCK)) {
					use = 2;
					tile.setForaging(null);
				}
			}
			else if (this.toolMaterialType.equals(ToolMaterialType.COPPER)) {
				if (tile.getForaging().equals(Foraging.SIMPLE_ROCK)) {
					use = 2;
					tile.setForaging(null);
				}
				else if (tile.getForaging().equals(Foraging.COPPER_ROCK)) {
					use = 2;
					tile.setForaging(null);
				}
				else if (tile.getForaging().equals(Foraging.STEEL_ROCK)) {
					use = 2;
					tile.setForaging(null);
				}
			}

			else if (this.toolMaterialType.equals(ToolMaterialType.PRIMITIVE)) {
				if (tile.getForaging().equals(Foraging.SIMPLE_ROCK)) {
					use = 2;
					tile.setForaging(null);
				}
				else if (tile.getForaging().equals(Foraging.COPPER_ROCK)) {
					use = 2;
					tile.setForaging(null);
				}
			}
		}

		if(use != 0) {
			if(this.toolMaterialType.equals(ToolMaterialType.PRIMITIVE))
				player.decreaseEnergy(5);
			else if(this.toolMaterialType.equals(ToolMaterialType.COPPER))
				player.decreaseEnergy(4);
			else if(this.toolMaterialType.equals(ToolMaterialType.STEEL))
				player.decreaseEnergy(3);
			else if(this.toolMaterialType.equals(ToolMaterialType.GOLD))
				player.decreaseEnergy(2);
			else if(this.toolMaterialType.equals(ToolMaterialType.IRIDIUM))
				player.decreaseEnergy(1);
		}
		else {
			player.decreaseEnergy(1);
			return Result.success("kolang hich kari nakard");
		}
		if(use == 1)
			return Result.success("zamin gheir shokhmi shod");

		return Result.success("the Rock removed from the ground");

	}

	@Override
	public ToolMaterialType getToolMaterialType() {
		return toolMaterialType;
	}
}
