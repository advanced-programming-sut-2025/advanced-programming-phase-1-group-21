package models.map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import data.ArtisanGoodsData;
import data.ArtisanRecipeData;
import models.Item.Item;
import models.game.Inventory;
import models.result.Result;
import models.result.errorTypes.GameError;
import models.sprite.GameSprite;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Artisan implements Placable, Serializable {
	private ArtisanGoodsData goodsData;
	private transient GameSprite sprite;
	private float remainTime, fullTime;
	Item result;
	private String resultName;

	public Artisan() {}

	public Artisan(ArtisanGoodsData goodsData) {
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
  
	public Item getResult() {
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
	public String getTexture() {

		if (isProcessing())
			return goodsData.getProcessingTexture();
		else if (result == null)
			return goodsData.getEmptyTexture();
		return goodsData.getDoneTexture();
	}

	@Override
	public Sprite spriteGetter() {
		if(sprite == null){
			sprite = new GameSprite(getTexture());
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
