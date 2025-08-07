package io.github.StardewValley.Animations;

import Asset.SharedAssetManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import models.map.Artisan;

public class ExclamationMarkAnimation implements IndependentAnimation {
	private Sprite sprite;
	private int fixedX, fixedY;
	private float time;
	private final float amplitude = 20;
	private final int speed = 5;
	private Artisan artisan;
	private Boolean isShown = false;

	public ExclamationMarkAnimation show(Artisan artisan, int x, int y) {
		this.artisan = artisan;
		fixedX = x;
		fixedY = Gdx.graphics.getHeight() - y;
		sprite = new Sprite(SharedAssetManager.getOrLoad("Textures/Exclamation_Mark.png"));
		sprite.setPosition(fixedX, fixedY);
		return this;
	}

	public void setShown() {
		isShown = true;
	}

	public boolean check(Artisan artisan) {
		return this.artisan.equals(artisan);
	}

	public boolean draw(SpriteBatch batch) {
		if (isShown) {
			time += Gdx.graphics.getDeltaTime() * speed;
			float newY = fixedY + (MathUtils.sin(time) * amplitude);
			sprite.setY(newY);
			sprite.draw(batch);
			isShown = false;
		}
		else {
			sprite.setY(fixedY);
			time = 0;
		}
		return false;
	}
}
