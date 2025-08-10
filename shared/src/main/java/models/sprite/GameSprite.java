package models.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import Asset.SharedAssetManager;

public class GameSprite extends Sprite {
    private String texturePath;

    public GameSprite(String texturePath) {
        super(loadTexture(texturePath));
        this.texturePath = texturePath;
    }

    private static Texture loadTexture(String path) {
        return SharedAssetManager.getOrLoad(path);
    }

    public void setTexture(String path) {
        super.setTexture(loadTexture(path));
        this.texturePath = path;
    }

    public String getTexturePath() {
        return texturePath;
    }
}