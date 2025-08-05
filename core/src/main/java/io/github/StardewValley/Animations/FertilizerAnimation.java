package io.github.StardewValley.Animations;

import Asset.SharedAssetManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import data.items.AllItemsData;

public class FertilizerAnimation implements IndependentAnimation {
	private Sprite sprite;
	private int fixedX, fixedY;
	private float rotation = 0, rotationSpeed = 90;

	public FertilizerAnimation show(String FertilizerName, int x, int y) {
		fixedX = x;
		fixedY = Gdx.graphics.getHeight() - y;
		sprite = new Sprite(SharedAssetManager.getOrLoad(AllItemsData.getData(FertilizerName).getTextureAddress()));
		sprite.setPosition(fixedX, fixedY);
		sprite.setOrigin(0, sprite.getHeight());
		return this;
	}

	public boolean draw(SpriteBatch batch) {
		rotation += rotationSpeed * Gdx.graphics.getDeltaTime();
		if (rotation > 90f)
			return true;
		sprite.setRotation(rotation);
		sprite.draw(batch);
		return false;
	}
}
