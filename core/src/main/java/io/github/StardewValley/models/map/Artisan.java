package io.github.StardewValley.models.map;

import io.github.StardewValley.models.App;
import io.github.StardewValley.models.Item.Item;
import io.github.StardewValley.models.data.ArtisanGoodsData;
import io.github.StardewValley.models.data.ArtisanRecipeData;
import io.github.StardewValley.models.game.Inventory;
import io.github.StardewValley.models.result.Result;
import io.github.StardewValley.models.result.errorTypes.GameError;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Artisan implements Placable, Serializable {
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

				Inventory inventory = App.getInstance().game.getCurrentPlayer().getInventory();

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

	@Override
	public String toString() {
		return String.format(
				"Artisan{name='%s', resultReady=%s, result='%s'}",
				getName(),
				isResultReady(),
				result != null ? result.getName() : "none"
		);
	}
}
