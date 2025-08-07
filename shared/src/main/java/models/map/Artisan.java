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
	private float remainTime, fullTime;
	Item result;
	private String resultName;

	public Artisan() {}

	public Artisan(ArtisanGoodsData goodsData, Inventory inventory) {
		this.goodsData = goodsData;
	}

	@Override
	public TileType getTileType() {
		return TileType.ARTISAN;
	}

	public String getName() {
		return goodsData.getName();
	}

	public String getResultName() {
		return resultName;
	}

	public float getPercentage() {
		return (float) (1.0 - (remainTime / fullTime));
	}

	public void updateTime(float deltaTime) {
		if (isProcessing()) {
			remainTime -= deltaTime;
			if (remainTime <= 0) {
				result = Item.build(resultName, 1);
				remainTime = 0;
			}
		}
	}

	public void cancel() {
		resultName = null;
		result = null;
		remainTime = 0;
		fullTime = 0;
	}

	public void finish() {
		remainTime = 0.0001f;
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

				startProcess(recipe.getName(), recipe.getProcessingTime());

				return Result.success(null);
			}
		}
		return Result.failure(GameError.WRONG_ITEM_TYPES);
	}

	private void startProcess(String recipeName, int time) {
		resultName = recipeName;
		remainTime = time;
		fullTime = time;
		result = null;
	}

	public boolean isProcessing() {
		return remainTime != 0;
	}

	public boolean isEmpty() {
		return !isProcessing() && result == null;
	}
  
	public Item getResult(Inventory inventory) {
		Item r = result;
		result = null;
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

	public ArtisanGoodsData getRecipeData() {
		return goodsData;
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
