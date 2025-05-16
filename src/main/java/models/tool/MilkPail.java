package models.tool;

import models.App;
import models.Item.Item;
import models.animal.Animal;
import models.animal.AnimalTypes;
import models.game.Player;
import models.map.Coord;
import models.map.Tile;
import models.result.Result;

public class MilkPail extends Tool {
	public MilkPail() {
		super(ToolType.MILK_PAIL);
	}

	@Override
	public Result<Item> use(Coord coord) {
		Player player = App.getInstance().game.getCurrentPlayer();
		Tile tile = player.getMap().getTile(coord);

		player.decreaseEnergy((int) (4 * App.getInstance().game.weatherCofficient()));

		Animal animal = tile.getPlacable(Animal.class);
		if (animal == null) {
			return null;
		}

		if(animal.getTodayProduct() == null)
			return null;

		if(animal.getAnimalType().equals(AnimalTypes.COW) || (animal.getAnimalType().equals(AnimalTypes.GOAT))) {
			animal.setFriendship(animal.getFriendship() + 5);
			animal.setTodayProduct(null);
			return Result.success(Item.build(animal.getTodayProduct() , 1));
		}

		return null;

	}

	@Override
	public ToolMaterialType getToolMaterialType() {
		return toolMaterialType;
	}
}
