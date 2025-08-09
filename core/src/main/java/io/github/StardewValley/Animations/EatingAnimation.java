package io.github.StardewValley.Animations;

import Asset.SharedAssetManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import data.items.AllItemsData;
import models.game.Player;

public class EatingAnimation implements IndependentAnimation {
	private Sprite sprite;
	private Player player;
	private float time = 1f;
	private final float amplitude = 10;
	private final int speed = 20;

	public EatingAnimation show(Player player, String foodName) {
		this.player = player;
		sprite = new Sprite(SharedAssetManager.getOrLoad(AllItemsData.getData(foodName).getTextureAddress()));
		sprite.setPosition(player.getSprite().getX() - 10, player.getSprite().getY() + 30);
		return this;
	}

	public boolean draw(SpriteBatch batch) {
		time -= Gdx.graphics.getDeltaTime();
		if (time <= 0) return true;
		float diff = (MathUtils.sin(time * speed) * amplitude);
		sprite.setPosition(player.getSprite().getX() - 20, player.getSprite().getY() + 30 + diff);
		sprite.draw(batch);
		return false;
	}
}
