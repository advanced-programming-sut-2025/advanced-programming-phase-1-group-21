package Asset;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class SharedAssetManager {
    private static final AssetManager manager = new AssetManager();
    private static Skin skin;

    public SharedAssetManager() {
    }

    public static void load() {
        //manager.load("skin/uiskin.json", Skin.class);


        manager.load("menu.png", Texture.class);
        manager.load("Textures/Tools/Primitive_Axe.png" , Texture.class);
        manager.load("Textures/Tools/Primitive_Hoe.png" , Texture.class);
        manager.load("Textures/Tools/Primitive_Milk_pail.png" , Texture.class);
        manager.load("Textures/Tools/Primitive_Pickaxe.png" , Texture.class);
        manager.load("Textures/Tools/Primitive_Shears.png" , Texture.class);
        manager.load("Textures/Tools/Primitive_Trashcan.png" , Texture.class);
        manager.load("Textures/Tools/Primitive_Watering_can.png" , Texture.class);
        manager.load("Textures/Tools/Steel_Scythe.png" , Texture.class);
        manager.load("Textures/Buildings/Normal_Barn.png" , Texture.class);
        manager.load("Textures/Buildings/Big_Barn.png" , Texture.class);
        manager.load("Textures/Buildings/Deluxe_Barn.png" , Texture.class);
        manager.load("Textures/Buildings/Normal_Coop.png" , Texture.class);
        manager.load("Textures/Buildings/Big_Coop.png" , Texture.class);
        manager.load("Textures/Buildings/Deluxe_Coop.png" , Texture.class);
        manager.load("Textures/Buildings/NormalGreenHouse.png" , Texture.class);
        manager.load("Textures/Buildings/DestroyedGreenHouse.png" , Texture.class);
        manager.load("Textures/map/FallBasicTile.png" , Texture.class);
        manager.load("Textures/map/WinterBasicTile.png" , Texture.class);
        manager.load("Textures/map/SpringBasicTile.png" , Texture.class);
        manager.load("Textures/map/SummerBasicTile.png" , Texture.class);
        manager.load("Textures/Decor/FoodStable.png" , Texture.class);
        manager.load("Textures/Decor/SimpleStable.png" , Texture.class);
        manager.load("Textures/Decor/heart.png" , Texture.class);
        manager.load("Textures/Emoji (probably unusable)/food.png" , Texture.class);
    }

    public static Texture getFoodEmoji(){
        return manager.get("Textures/Emoji (probably unusable)/food.png");
    }

    public static Texture getHeart(){
        return manager.get("Textures/Decor/heart.png");
    }

    public static Texture getGreenHouse(String type){
        return manager.get("Textures/Buildings/" + type + "GreenHouse.png");
    }

    public static Texture getStable(String type){
        return manager.get("Textures/Decor/" + type + "Stable.png");
    }

    public static Texture getTile(String season){
        return manager.get("Textures/map/" + season + "BasicTile.png");
    }

    public static void finishLoading() {
        manager.finishLoading();
        //skin = manager.get("skin/uiskin.json", Skin.class);
    }

    public static Texture getAnimalHouse(String name){
        return manager.get("Textures/Buildings/" + name + ".png");
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
