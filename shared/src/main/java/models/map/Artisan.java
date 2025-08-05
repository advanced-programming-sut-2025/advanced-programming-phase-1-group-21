package models.map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import data.ArtisanGoodsData;
import data.ArtisanRecipeData;
import models.Item.Item;
import models.game.Inventory;
import models.result.Result;
import models.result.errorTypes.GameError;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Artisan implements Placable, Serializable {
	private ArtisanGoodsData goodsData;
	private transient Sprite sprite;
	Item result;

	public Artisan() {}

	public Artisan(ArtisanGoodsData goodsData, Inventory inventory) {
//		System.out.println("---- Artisan Created " + goodsData.getName() + " ----");
		this.goodsData = goodsData;

		// The ingredients of "Bee House" is null
		if (goodsData.getName().equalsIgnoreCase("Bee House"))
			craft(new ArrayList<>(), inventory);
	}

	@Override
	public TileType getTileType() {
		return TileType.ARTISAN;
	}

	public String getName() {
		return goodsData.getName();
	}

	public ArtisanRecipeData getRecipeData(String mainIngredient) {
		ArtisanRecipeData resultRecipe = null;
		int numberOfRecipe = 0;
		for (ArtisanRecipeData recipe : goodsData.getRecipes())
			for (Map <String, Integer> baseIngredients: recipe.getIngredients())
				for (Map.Entry<String, Integer> entry : baseIngredients.entrySet())
					if (mainIngredient.equalsIgnoreCase(entry.getKey())) {
						numberOfRecipe++;
						resultRecipe = recipe;
					}
		if (numberOfRecipe != 1)
			return null;
		return resultRecipe;
	}

	public void startProcess(String result) {
		for (ArtisanRecipeData recipeData: goodsData.getRecipes()) {
			if (recipeData.getName().equalsIgnoreCase(result)) {
				startProcess(result, recipeData.getProcessingTime());
			}
		}
	}

	public Result<Void> craft(ArrayList <String> itemNames, Inventory inventory) {
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
					continue;

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

				startProcess(recipe.getName(), 0);

				return Result.success(null);
			}
		}
		return Result.failure(GameError.WRONG_ITEM_TYPES);
	}

	private void startProcess(String recipeName, int time) {
//		System.out.println("---- Artisan Start Process: " + recipeName);
		result = Item.build(recipeName, 1);
	}

	private boolean isProcessing() {
		return false;
	}

	public boolean isEmpty() {
		return !isProcessing() && result == null;
	}
  
	public Item getResult(Inventory inventory) {
		Item r = result;
		result = null;
		if (goodsData.getName().equalsIgnoreCase("Bee House"))
			craft(new ArrayList<>(), inventory);
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
	public Texture getTexture() {

		if (isProcessing())
			return new Texture(goodsData.getProcessingTexture());
		else if (result == null)
			return new Texture(goodsData.getEmptyTexture());
		return new Texture(goodsData.getDoneTexture());
	}

	@Override
	public Sprite spriteGetter() {
		if(sprite == null){
			sprite = new Sprite(getTexture());
		}
		sprite.setTexture(getTexture());
		return sprite;
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
