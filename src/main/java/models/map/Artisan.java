package models.map;

import models.Item.Item;
import models.data.ArtisanGoodsData;
import models.data.ArtisanRecipeData;

import java.util.ArrayList;
import java.util.Map;

public class Artisan implements Placable {
	private final ArtisanGoodsData goodsData;

	public Artisan(ArtisanGoodsData goodsData) {
		this.goodsData = goodsData;

		// The ingredients of "Bee House" is null
		if (goodsData.getName().equalsIgnoreCase("Bee House"))
			craft(new ArrayList<>());
	}

	public String getName() {
		return goodsData.getName();
	}

	public void craft(ArrayList<Item> items) {
		for (ArtisanRecipeData recipe : goodsData.getRecipes()) {
			for (Map <String, Integer> ingredient: recipe.getIngredients()) {
				int correct = 0;
				for (String key : ingredient.keySet()) {
					for (Item item : items) {
						if (item.getName().equalsIgnoreCase(key) && ingredient.get(key) < item.getAmount()) {
							correct++;
							break;
						}
					}
				}
				if (correct == ingredient.size()) {
					// We hava all ingredients we want

				}
			}
		}
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
