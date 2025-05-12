package models.tool;

import models.App;
import models.Item.Item;
import models.animal.Animal;
import models.animal.AnimalTypes;
import models.game.Player;
import models.map.Coord;
import models.map.Tile;
import models.result.Result;

public class Shear extends Tool {
	public Shear() {
		super(ToolType.SHEAR);
	}

	@Override
	public Result<Item> use(Coord coord) {
		Player player = App.game.getCurrentPlayer();
		Tile tile = player.getMap().getTile(coord);

		player.decreaseEnergy(4);

		Animal animal = tile.getPlacable(Animal.class);
		if (animal == null) {
			return null;
		}

		if(animal.getTodayProduct() == null)
			return null;

		if(animal.getAnimalType().equals(AnimalTypes.SHEEP) || (animal.getAnimalType().equals(AnimalTypes.RABBIT)))
			return Result.success(Item.build(animal.getTodayProduct() , 1));

		return null;
	}

	@Override
	public ToolMaterialType getToolMaterialType() {
		return toolMaterialType;
	}
}
