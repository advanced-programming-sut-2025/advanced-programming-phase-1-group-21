package io.github.StardewValley.controllers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import models.sprite.GameSprite;
import models.sprite.SpriteData;
import models.game.Game;
import models.result.Result;
import models.result.errorTypes.GameError;

import java.io.*;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;

public class GameSaver {

    public static void saveGame(Game game, String filename) {
        filename = "SAVE " + filename + ".dat";
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(game);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Result<Game> loadGame(File file) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            Game game = (Game) in.readObject();
            System.out.println("Game loaded successfully.");
            return Result.success(game);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return Result.failure(GameError.GAME_NOT_FOUND);
        }
    }

    public static Map<String, SpriteData> saveAllSprites(Object root) {
        Map<String, SpriteData> spriteMap = new HashMap<>();
        Set<Object> visited = Collections.newSetFromMap(new IdentityHashMap<>());
        saveSpritesRecursive(root, spriteMap, visited, "");
        return spriteMap;
    }

    private static void saveSpritesRecursive(Object obj, Map<String, SpriteData> spriteMap, Set<Object> visited, String path) {
        if (obj == null || visited.contains(obj)) return;
        visited.add(obj);

        Class<?> clazz = obj.getClass();

        // If this object is a Sprite
        if (obj instanceof GameSprite) {
            GameSprite sprite = (GameSprite) obj;
            String texturePath = sprite.getTexturePath();
            spriteMap.put(path, new SpriteData(sprite, texturePath));
            return;
        }

        // If it's a collection/array
        if (obj instanceof Iterable) {
            int i = 0;
            for (Object item : (Iterable<?>) obj) {
                saveSpritesRecursive(item, spriteMap, visited, path + "[" + i++ + "]");
            }
            return;
        }
        if (clazz.isArray()) {
            int len = Array.getLength(obj);
            for (int i = 0; i < len; i++) {
                saveSpritesRecursive(Array.get(obj, i), spriteMap, visited, path + "[" + i + "]");
            }
            return;
        }

        // Recurse into fields
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object value = field.get(obj);
                saveSpritesRecursive(value, spriteMap, visited, path + "." + field.getName());
            } catch (IllegalAccessException ignored) {}
        }
    }

    public static void reloadAllSprites(Object root, Map<String, SpriteData> spriteMap) {
        Set<Object> visited = Collections.newSetFromMap(new IdentityHashMap<>());
        reloadSpritesRecursive(root, spriteMap, visited, "");
    }

    private static void reloadSpritesRecursive(Object obj, Map<String, SpriteData> spriteMap, Set<Object> visited, String path) {
        if (obj == null || visited.contains(obj)) return;
        visited.add(obj);

        Class<?> clazz = obj.getClass();

        if (obj instanceof GameSprite) {
            GameSprite sprite = (GameSprite) obj;
            SpriteData data = spriteMap.get(path);
            if (data != null) {
                Texture texture = Asset.SharedAssetManager.getOrLoad(data.texturePath);
                sprite.setTexture(texture);
                data.applyTo(sprite);
            }
            return;
        }

        if (obj instanceof Iterable) {
            int i = 0;
            for (Object item : (Iterable<?>) obj) {
                reloadSpritesRecursive(item, spriteMap, visited, path + "[" + i++ + "]");
            }
            return;
        }
        if (clazz.isArray()) {
            int len = Array.getLength(obj);
            for (int i = 0; i < len; i++) {
                reloadSpritesRecursive(Array.get(obj, i), spriteMap, visited, path + "[" + i + "]");
            }
            return;
        }

        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object value = field.get(obj);
                reloadSpritesRecursive(value, spriteMap, visited, path + "." + field.getName());
            } catch (IllegalAccessException ignored) {}
        }
    }


}
