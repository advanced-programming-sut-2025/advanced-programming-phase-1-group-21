package io.github.StardewValley.Animations;

import Asset.SharedAssetManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import data.items.AllItemsData;
import io.github.StardewValley.App;
import models.game.Player;

public class HarvestAnimation implements IndependentAnimation {
	private Sprite sprite;
	private Player player;

	private float progress = 0;
	private final float ANIMATION_DURATION = 0.8f;
	private Vector2 startPoint = new Vector2();
	private Vector2 targetPoint;

	public HarvestAnimation show(Player player, String harvestName, int startX, int startY) {
		this.player = player;
		sprite = new Sprite(SharedAssetManager.getOrLoad(AllItemsData.getData(harvestName).getTextureAddress()));
		startPoint = new Vector2(startX, Gdx.graphics.getHeight() - startY);
		sprite.setPosition(startPoint.x, startPoint.y);
		return this;
	}

	public boolean draw(SpriteBatch batch) {
		targetPoint = new Vector2(player.getSprite().getX(), player.getSprite().getY());

		progress += Gdx.graphics.getDeltaTime() / ANIMATION_DURATION;

		if (progress >= 1.f) {
			return true;
		}

		float currentX = startPoint.x + (targetPoint.x - startPoint.x) * progress;
		float currentY = startPoint.y + (targetPoint.y - startPoint.y) * progress;

		sprite.setPosition(currentX, currentY);

		sprite.draw(batch);
		return false;
	}
}
