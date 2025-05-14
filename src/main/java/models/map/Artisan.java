package models.map;

import models.App;
import models.Item.Item;
import models.data.ArtisanGoodsData;
import models.data.ArtisanRecipeData;
import models.game.Inventory;
import models.result.Result;
import models.result.errorTypes.GameError;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Artisan implements Placable {
	private final ArtisanGoodsData goodsData;
	Item result;

	public Artisan(ArtisanGoodsData goodsData) {
		this.goodsData = goodsData;

		// The ingredients of "Bee House" is null
		if (goodsData.getName().equalsIgnoreCase("Bee House"))
			craft(new ArrayList<>());
	}

	@Override
	public TileType getTileType() {
		return TileType.ARTISAN;
	}

	public String getName() {
		return goodsData.getName();
	}

	public Result<Void> craft(ArrayList <String> itemNames) {
		for (ArtisanRecipeData recipe : goodsData.getRecipes()) {
			mainLoop:
			for (Map <String, Integer> baseIngredients: recipe.getIngredients()) {
				int correct = 0;
				for (Map.Entry<String, Integer> entry : baseIngredients.entrySet()) {
					if (itemNames.contains(entry.getKey()))
						correct++;
					else
						continue mainLoop;
				}
				if (correct != itemNames.size())
					continue mainLoop;

				Inventory inventory = App.game.getCurrentPlayer().getInventory();

				List<Item> requiredItems = new ArrayList<>();
				for (Map.Entry<String, Integer> entry : baseIngredients.entrySet()) {
					String ingredientName = entry.getKey();
					int totalRequired = entry.getValue();
					Item inventoryItem = inventory.getItem(ingredientName);

					if (inventoryItem == null || inventoryItem.getAmount() < totalRequired) {
						return Result.failure(GameError.NOT_ENOUGH_ITEMS);
					}
					Item required = Item.build(inventoryItem.getName(), totalRequired); // assuming Item has a copy constructor
					requiredItems.add(required);
				}

				inventory.removeItems(requiredItems);

				result = Item.build(recipe.getName(), 1);

				return Result.success(null);
			}
		}
		return Result.failure(GameError.WRONG_ITEM_TYPES);
	}

	public Item getResult() {
		Item r = result;
		result = null;
		if (goodsData.getName().equalsIgnoreCase("Bee House"))
			craft(new ArrayList<>());
		return r;
	}

	public Item getResultWithoutReset() {
		return result;
	}

	public boolean isResultReady() {
		return result != null;
	}

	@Override
	public boolean isWalkable() {
		return false;
	}

	@Override
	public String getSprite() {
		return "8";
	}
}
