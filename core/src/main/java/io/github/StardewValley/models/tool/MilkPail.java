package io.github.StardewValley.models.tool;

import io.github.StardewValley.models.App;
import io.github.StardewValley.models.Item.Item;
import io.github.StardewValley.models.animal.Animal;
import io.github.StardewValley.models.animal.AnimalTypes;
import io.github.StardewValley.models.game.Player;
import io.github.StardewValley.models.map.Coord;
import io.github.StardewValley.models.map.Tile;
import io.github.StardewValley.models.result.Result;

public class MilkPail extends Tool {
	public MilkPail() {
		super(ToolType.MILK_PAIL);
	}

	@Override
	public Result<Item> use(Coord coord) {
		Player player = App.getInstance().game.getCurrentPlayer();
		Tile tile = player.getMap().getTile(coord);

		player.decreaseEnergy((int) (4 * App.getInstance().game.weatherCoefficient()));

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
