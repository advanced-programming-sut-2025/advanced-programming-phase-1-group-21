package io.github.StardewValley.asset;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextTooltip;
import com.kotcrab.vis.ui.VisUI;
import org.w3c.dom.Text;

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
        manager.load("Textures/Tools/Primitive_Axe.png" , Texture.class);
        manager.load("Textures/Tools/Primitive_Hoe.png" , Texture.class);
        manager.load("Textures/Tools/Primitive_Milk_pail.png" , Texture.class);
        manager.load("Textures/Tools/Primitive_Pickaxe.png" , Texture.class);
        manager.load("Textures/Tools/Primitive_Shears.png" , Texture.class);
        manager.load("Textures/Tools/Primitive_Trashcan.png" , Texture.class);
        manager.load("Textures/Tools/Primitive_Watering_can.png" , Texture.class);
        manager.load("Textures/Tools/Steel_Scythe.png" , Texture.class);
    }

    public static void finishLoading() {
        manager.finishLoading();
        //skin = manager.get("skin/uiskin.json", Skin.class);
        skin = VisUI.getSkin();

        for (GameSound gameSound: GameSound.values()) {
            gameSound.setSound(manager.get(gameSound.getPath(), Sound.class));
        }
    }

    public static Texture getToolTexture(String name){
        return manager.get("Textures/Tools/" + name + ".png");
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
