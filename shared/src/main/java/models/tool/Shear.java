package models.tool;

import models.Item.Item;
import models.animal.Animal;
import models.animal.AnimalTypes;
import models.game.Game;
import models.game.Player;
import models.map.Coord;
import models.map.Tile;
import models.result.Result;

public class Shear extends Tool {
	public Shear() {
		super(ToolType.SHEAR);
	}

	@Override
	public Result<Item> use(Coord coord, Game game) {
		Player player = game.getCurrentPlayer();
		Tile tile = player.getMap().getTile(coord);

		player.decreaseEnergy((int)(4 * game.weatherCoefficient()));

		Animal animal = tile.getPlacable(Animal.class);
		if (animal == null) {
			return null;
		}

		if(animal.getTodayProduct() == null)
			return null;

		if(animal.getAnimalType().equals(AnimalTypes.SHEEP) || (animal.getAnimalType().equals(AnimalTypes.RABBIT))) {
			animal.setFriendship(animal.getFriendship() + 5);
			return Result.success(Item.build(animal.getTodayProduct() , 1));
		}

		return null;
	}

	@Override
	public ToolMaterialType getToolMaterialType() {
		return toolMaterialType;
	}
}
