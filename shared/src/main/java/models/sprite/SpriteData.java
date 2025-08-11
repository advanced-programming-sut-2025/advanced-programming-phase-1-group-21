package models.sprite;

import com.badlogic.gdx.graphics.g2d.Sprite;

import java.io.Serializable;

public class SpriteData implements Serializable {
    public String texturePath;
    public float x, y, width, height, rotation, scaleX, scaleY;

    public SpriteData() {
    }

    public SpriteData(Sprite sprite, String texturePath) {
        this.texturePath = texturePath;
        this.x = sprite.getX();
        this.y = sprite.getY();
        this.width = sprite.getWidth();
        this.height = sprite.getHeight();
        this.rotation = sprite.getRotation();
        this.scaleX = sprite.getScaleX();
        this.scaleY = sprite.getScaleY();
    }

    public void applyTo(GameSprite sprite) {
        sprite.setPosition(x, y);
        sprite.setSize(width, height);
        sprite.setRotation(rotation);
        sprite.setScale(scaleX, scaleY);
    }
}