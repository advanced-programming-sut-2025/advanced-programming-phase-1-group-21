package models.Tool;

import models.App;
import models.game.Item;
import models.map.Coord;
import models.map.Foraging;
import models.map.LocationsOnMap;
import models.result.Result;
import models.result.errorTypes.GameError;

public class Pickaxe extends Tool {
	private ToolMaterialType toolMaterialType = ToolMaterialType.PRIMITIVE;
	public Pickaxe() {
		super(ToolType.PICKAXE);
	}

	@Override
	public Result<Item> use(Coord coord) {
		if(App.game.getCurrentPlayer().getCurrentPlayerMap().getCurrentLocation() != LocationsOnMap.Farm) {
			if(App.game.getCurrentPlayer().getCurrentPlayerMap().getCurrentLocation() != LocationsOnMap.Mines)
				return Result.failure(GameError.YOU_CANT_USE_PICKAXE_HERE, GameError.YOU_CANT_USE_PICKAXE_HERE.getMessage());
		}

		int use = 0;
		if(App.game.getCurrentPlayer().currentLocationTiles().get(coord.getY()).get(coord.getX()).isShokhmi()) {
			App.game.getCurrentPlayer().currentLocationTiles().get(coord.getY()).get(coord.getX()).setShokhmi(false);
			use = 1;
		}

		if(App.game.getCurrentPlayer().currentLocationTiles().get(coord.getY()).get(coord.getX()).getForaging() != null) {
			if (this.toolMaterialType.equals(ToolMaterialType.IRIDIUM)) {
				if (App.game.getCurrentPlayer().currentLocationTiles().get(coord.getY()).get(coord.getX()).getForaging().equals(Foraging.SIMPLE_ROCK)) {
					use = 2;
					App.game.getCurrentPlayer().currentLocationTiles().get(coord.getY()).get(coord.getX()).setForaging(null);
				}
				else if (App.game.getCurrentPlayer().currentLocationTiles().get(coord.getY()).get(coord.getX()).getForaging().equals(Foraging.COPPER_ROCK)) {
					use = 2;
					App.game.getCurrentPlayer().currentLocationTiles().get(coord.getY()).get(coord.getX()).setForaging(null);
				}
				else if (App.game.getCurrentPlayer().currentLocationTiles().get(coord.getY()).get(coord.getX()).getForaging().equals(Foraging.STEEL_ROCK)) {
					use = 2;
					App.game.getCurrentPlayer().currentLocationTiles().get(coord.getY()).get(coord.getX()).setForaging(null);
				}
				else if (App.game.getCurrentPlayer().currentLocationTiles().get(coord.getY()).get(coord.getX()).getForaging().equals(Foraging.GOLD_ROCK)) {
					use = 2;
					App.game.getCurrentPlayer().currentLocationTiles().get(coord.getY()).get(coord.getX()).setForaging(null);
				}
				else if (App.game.getCurrentPlayer().currentLocationTiles().get(coord.getY()).get(coord.getX()).getForaging().equals(Foraging.IRIDIUM_ROCK)) {
					use = 2;
					App.game.getCurrentPlayer().currentLocationTiles().get(coord.getY()).get(coord.getX()).setForaging(null);
				}
			}
			else if (this.toolMaterialType.equals(ToolMaterialType.GOLD)) {
				if (App.game.getCurrentPlayer().currentLocationTiles().get(coord.getY()).get(coord.getX()).getForaging().equals(Foraging.SIMPLE_ROCK)) {
					use = 2;
					App.game.getCurrentPlayer().currentLocationTiles().get(coord.getY()).get(coord.getX()).setForaging(null);
				}
				else if (App.game.getCurrentPlayer().currentLocationTiles().get(coord.getY()).get(coord.getX()).getForaging().equals(Foraging.COPPER_ROCK)) {
					use = 2;
					App.game.getCurrentPlayer().currentLocationTiles().get(coord.getY()).get(coord.getX()).setForaging(null);
				}
				else if (App.game.getCurrentPlayer().currentLocationTiles().get(coord.getY()).get(coord.getX()).getForaging().equals(Foraging.STEEL_ROCK)) {
					use = 2;
					App.game.getCurrentPlayer().currentLocationTiles().get(coord.getY()).get(coord.getX()).setForaging(null);
				}
				else if (App.game.getCurrentPlayer().currentLocationTiles().get(coord.getY()).get(coord.getX()).getForaging().equals(Foraging.GOLD_ROCK)) {
					use = 2;
					App.game.getCurrentPlayer().currentLocationTiles().get(coord.getY()).get(coord.getX()).setForaging(null);
				}
				else if (App.game.getCurrentPlayer().currentLocationTiles().get(coord.getY()).get(coord.getX()).getForaging().equals(Foraging.IRIDIUM_ROCK)) {
					use = 2;
					App.game.getCurrentPlayer().currentLocationTiles().get(coord.getY()).get(coord.getX()).setForaging(null);
				}
			}
			else if (this.toolMaterialType.equals(ToolMaterialType.STEEL)) {
				if (App.game.getCurrentPlayer().currentLocationTiles().get(coord.getY()).get(coord.getX()).getForaging().equals(Foraging.SIMPLE_ROCK)) {
					use = 2;
					App.game.getCurrentPlayer().currentLocationTiles().get(coord.getY()).get(coord.getX()).setForaging(null);
				}
				else if (App.game.getCurrentPlayer().currentLocationTiles().get(coord.getY()).get(coord.getX()).getForaging().equals(Foraging.COPPER_ROCK)) {
					use = 2;
					App.game.getCurrentPlayer().currentLocationTiles().get(coord.getY()).get(coord.getX()).setForaging(null);
				}
				else if (App.game.getCurrentPlayer().currentLocationTiles().get(coord.getY()).get(coord.getX()).getForaging().equals(Foraging.STEEL_ROCK)) {
					use = 2;
					App.game.getCurrentPlayer().currentLocationTiles().get(coord.getY()).get(coord.getX()).setForaging(null);
				}
				else if (App.game.getCurrentPlayer().currentLocationTiles().get(coord.getY()).get(coord.getX()).getForaging().equals(Foraging.GOLD_ROCK)) {
					use = 2;
					App.game.getCurrentPlayer().currentLocationTiles().get(coord.getY()).get(coord.getX()).setForaging(null);
				}
			}

			else if (this.toolMaterialType.equals(ToolMaterialType.COPPER)) {
				if (App.game.getCurrentPlayer().currentLocationTiles().get(coord.getY()).get(coord.getX()).getForaging().equals(Foraging.SIMPLE_ROCK)) {
					use = 2;
					App.game.getCurrentPlayer().currentLocationTiles().get(coord.getY()).get(coord.getX()).setForaging(null);
				}
				else if (App.game.getCurrentPlayer().currentLocationTiles().get(coord.getY()).get(coord.getX()).getForaging().equals(Foraging.COPPER_ROCK)) {
					use = 2;
					App.game.getCurrentPlayer().currentLocationTiles().get(coord.getY()).get(coord.getX()).setForaging(null);
				}
				else if (App.game.getCurrentPlayer().currentLocationTiles().get(coord.getY()).get(coord.getX()).getForaging().equals(Foraging.STEEL_ROCK)) {
					use = 2;
					App.game.getCurrentPlayer().currentLocationTiles().get(coord.getY()).get(coord.getX()).setForaging(null);
				}
			}

			else if (this.toolMaterialType.equals(ToolMaterialType.PRIMITIVE)) {
				if (App.game.getCurrentPlayer().currentLocationTiles().get(coord.getY()).get(coord.getX()).getForaging().equals(Foraging.SIMPLE_ROCK)) {
					use = 2;
					App.game.getCurrentPlayer().currentLocationTiles().get(coord.getY()).get(coord.getX()).setForaging(null);
				}
				else if (App.game.getCurrentPlayer().currentLocationTiles().get(coord.getY()).get(coord.getX()).getForaging().equals(Foraging.COPPER_ROCK)) {
					use = 2;
					App.game.getCurrentPlayer().currentLocationTiles().get(coord.getY()).get(coord.getX()).setForaging(null);
				}
			}
		}

		if(use != 0) {
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
		}
		else {
			App.game.getCurrentPlayer().setEnergy(App.game.getCurrentPlayer().getEnergy() - 1);
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
