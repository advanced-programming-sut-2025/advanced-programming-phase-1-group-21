package models.game;

import Asset.SharedAssetManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Json;
import models.map.Coord;
import models.sprite.GameSprite;

import java.io.Serializable;

public class Reaction implements Serializable {
    public enum EmojiType {
        HAPPY("Textures/emoji/happy.png"),
        SAD("Textures/emoji/sad.png"),
        NONE(null);

        public final String path;
        EmojiType(String path) {
            this.path = path;
        }
    }

    private EmojiType currentEmoji = EmojiType.NONE;
    private String currentText = null;
    private transient GameSprite emojiSprite;
    private transient BitmapFont font;
    private float duration;
    private float timer;

    public Sprite getEmojiSprite() {
        if(emojiSprite == null || emojiSprite.getTexture() == null)
            emojiSprite = new GameSprite(SharedAssetManager.getHeartPath());
        return emojiSprite;
    }

    private void generateFont(int size) {
        if (font != null) return;
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/Blomberg-8MKKZ.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        parameter.color = Color.WHITE;
        BitmapFont font = generator.generateFont(parameter);
        generator.dispose();
        this.font = font;
    }

    public Reaction() {
        emojiSprite = null;
    }

    public void showEmoji(EmojiType type, float duration) {
        this.currentEmoji = type;
        this.currentText = null;
        if (type != EmojiType.NONE) {
            boolean exists = Gdx.files.internal(type.path).exists();
            assert exists;
            Texture texture = new Texture(Gdx.files.internal(type.path));
            this.emojiSprite.setTexture(texture);
            this.emojiSprite.setSize(30, 30); // More reasonable size
        } else {
            this.emojiSprite = null;
        }
        this.duration = duration;
        this.timer = duration;
    }

    public void showText(String text, float duration) {
        this.currentText = text;
        this.currentEmoji = EmojiType.NONE;
        this.emojiSprite = null;
        this.duration = duration;
        this.timer = duration;
    }

    public void update(float delta) {
        if (isShowing()) {
            timer -= delta;
            if (timer <= 0) {
                currentEmoji = EmojiType.NONE;
                currentText = null;
                emojiSprite = null;
            }
        }
    }

    public void render(SpriteBatch batch, float x, float y) {
        if (currentEmoji != EmojiType.NONE) {
            // DEBUG: Print position and texture info
            System.out.println("Drawing emoji at: " + x + "," + (y+100));
            System.out.println("Texture: " + emojiSprite.getTexture());
            System.out.println("Texture size: " +
                    (emojiSprite.getTexture() != null ?
                            emojiSprite.getTexture().getWidth() + "x" + emojiSprite.getTexture().getHeight() : "null"));

            // Make sure we have a valid texture
            if (emojiSprite.getTexture() == null) {
                emojiSprite.setTexture(currentEmoji.path);
            }

            // Set position and draw
            emojiSprite.setPosition(x, y + 100); // Centered
            emojiSprite.draw(batch);

            generateFont(20);
            font.draw(batch, currentEmoji.name() + " HERE", x, y + 80);
        } else if (currentText != null) {
            generateFont(20);
            font.draw(batch, currentText, x, y + 80);
        }
    }

    public void render(SpriteBatch batch, Coord coord) {
        render(batch, coord.getX(), coord.getY());
    }

    public boolean isShowing() {
        return currentEmoji != EmojiType.NONE || currentText != null;
    }

    public EmojiType getCurrentEmoji() {
        return currentEmoji;
    }

    public String getCurrentText() {
        return currentText;
    }

    public void fromString(String str) {
        for (EmojiType type : EmojiType.values()) {
            if (str.contains(type.name())) {
                showEmoji(type, 10);
                return;
            }
        }
        showText(str, 10);
    }
}