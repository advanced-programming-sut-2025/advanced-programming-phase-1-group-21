package models.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import Asset.SharedAssetManager;

public class GameSprite extends Sprite {
    private String texturePath;

    public GameSprite(String texturePath) {
        // 1. Call the empty super() constructor first.
        super(loadTexture(texturePath));
        this.texturePath = texturePath;
    }

    private static Texture loadTexture(String path) {
        if (path == null) {
            return null;
        }
        return SharedAssetManager.getOrLoad(path);
    }

    public String getTexturePath() {
        return texturePath;
    }


    public void setTexture(String path) {
        if (Gdx.app != null) {
            Texture texture = loadTexture(path);
            if (texture != null) {
                super.setTexture(texture);
                this.texturePath = path;
            }
        }
    }
}