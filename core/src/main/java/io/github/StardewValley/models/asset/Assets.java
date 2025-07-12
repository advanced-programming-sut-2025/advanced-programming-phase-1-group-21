package io.github.StardewValley.models.asset;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.kotcrab.vis.ui.VisUI;

import java.util.*;

public class Assets {
    private static final AssetManager manager = new AssetManager();
    private static Skin skin;

    private Assets() {
    }

    public static void load() {
        //manager.load("skin/uiskin.json", Skin.class);
        VisUI.load(VisUI.SkinScale.X2);

        for (GameSound gameSound: GameSound.values())
            manager.load(gameSound.getPath(), Sound.class);

        manager.load("menu.png", Texture.class);
    }

    public static void finishLoading() {
        manager.finishLoading();
        //skin = manager.get("skin/uiskin.json", Skin.class);
        skin = VisUI.getSkin();

        for (GameSound gameSound: GameSound.values()) {
            gameSound.setSound(manager.get(gameSound.getPath(), Sound.class));
        }
    }

    public static Texture getBackground() {
        return manager.get("background.png", Texture.class);
    }

    public static Texture getMenuBackground() {
        return manager.get("menu.png", Texture.class);
    }

    public static <T> T get(String path, Class<T> type) {
        return manager.get(path, type);
    }

    public static Skin getSkin() {
        return skin;
    }

    public static boolean update() {
        return manager.update();
    }

    public static float getProgress() {
        return manager.getProgress();
    }

    public static void dispose() {
        manager.dispose();
    }

    public static float getVolume() {
        return 1.0f;
    }
}
