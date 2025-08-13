package controllers;

import com.badlogic.gdx.graphics.Texture;
import models.game.Player;
import models.network.GamePacket;
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

            Map<String, SpriteData> spriteMap = saveAllSprites(game);
            out.writeObject(spriteMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Result<Game> loadGame(File file) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            Game game = (Game) in.readObject();

            Map<String, SpriteData> spriteMap = (Map<String, SpriteData>) in.readObject();

            System.out.println("### DEBUG: Loading spriteMap with " + spriteMap.size() + " entries. Keys: " + spriteMap.keySet());

            reloadAllSprites(game, spriteMap);
            game.rand = new Random(1);

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

        if (obj instanceof GameSprite) {
            GameSprite sprite = (GameSprite) obj;
            String texturePath = sprite.getTexturePath();
            spriteMap.put(path, new SpriteData(sprite, texturePath));
            return; // Stop recursion for this branch
        }

        // 2. BEFORE checking isJavaCoreClass, check if it's a collection we should enter
        if (obj instanceof Iterable) {
            int i = 0;
            for (Object item : (Iterable<?>) obj) {
                saveSpritesRecursive(item, spriteMap, visited, path + "[" + i++ + "]");
            }
            return; // We have handled the list's contents, so we are done with the list itself
        }
        if (clazz.isArray()) {
            int len = Array.getLength(obj);
            for (int i = 0; i < len; i++) {
                saveSpritesRecursive(Array.get(obj, i), spriteMap, visited, path + "[" + i + "]");
            }
            return;
        }

        // 3. Now, if it's not a sprite or collection, check if we should ignore it
        if (isSimple(clazz) || isJavaCoreClass(clazz)) {
            return;
        }

        // 4. If we are here, it's a custom object whose fields we need to inspect
        for (Field field : getAllFields(clazz)) {
            field.setAccessible(true);
            try {
                Object value = field.get(obj);
                saveSpritesRecursive(value, spriteMap, visited, path + "." + field.getName());
            } catch (IllegalAccessException ignored) {}
        }
    }

    public static void reloadAllSprites(Object root, Map<String, SpriteData> spriteMap) {
        System.out.println("Reloading all sprites...");
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
                sprite.setTexture(data.texturePath);
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

        if (isSimple(clazz) || isJavaCoreClass(clazz)) {
            return;
        }

        for (Field field : getAllFields(clazz)) {
            field.setAccessible(true);
            try {
                Object value = field.get(obj);
                String currentPath = path + "." + field.getName();

                if (value == null && field.getType() == GameSprite.class) {
                    SpriteData data = spriteMap.get(currentPath);

                    if (data != null) {
                        GameSprite newSprite = new GameSprite(data.texturePath); // Requires a public GameSprite() constructor

                        data.applyTo(newSprite);

                        field.set(obj, newSprite);
                    }
                } else {
                    reloadSpritesRecursive(value, spriteMap, visited, currentPath);
                }
            } catch (IllegalAccessException ignored) {}
        }
    }

    private static boolean isSimple(Class<?> clazz) {
        return clazz.isPrimitive() ||
                clazz == Integer.class || clazz == Long.class ||
                clazz == Boolean.class || clazz == Double.class ||
                clazz == Float.class || clazz == Byte.class ||
                clazz == Short.class || clazz == Character.class ||
                clazz == String.class || clazz.isEnum();
    }

    private static boolean isJavaCoreClass(Class<?> clazz) {
        return clazz.getPackageName().startsWith("java.") ||
                clazz.getPackageName().startsWith("javax.") ||
                clazz.getPackageName().startsWith("sun.") ||
                clazz.getPackageName().startsWith("com.google");
    }

    public static GamePacket serializeForNetwork(Game game) throws IOException {
        GamePacket packet = new GamePacket();

        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream out = new ObjectOutputStream(bos)) {
            out.writeObject(game);
            packet.gameBytes = bos.toByteArray();
        }

        Map<String, SpriteData> spriteMap = GameSaver.saveAllSprites(game);

        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream out = new ObjectOutputStream(bos)) {
            out.writeObject(spriteMap);
            packet.spriteMapBytes = bos.toByteArray();
        }

        ArrayList<String> users = new ArrayList<>();
        for (Player p: game.getPlayers())
            users.add(p.getUser().getUsername());

        packet.users = users;

        System.out.println("### DEBUG: Sending spriteMap with " + spriteMap.size() + " entries. Keys: " + spriteMap.keySet());

        return packet;
    }

    public static Game deserializeFromNetwork(GamePacket packet) throws IOException, ClassNotFoundException {
        Game game;
        Map<String, SpriteData> spriteMap;

        try (ByteArrayInputStream bis = new ByteArrayInputStream(packet.gameBytes);
             ObjectInputStream in = new ObjectInputStream(bis)) {
            game = (Game) in.readObject();
        }

        try (ByteArrayInputStream bis = new ByteArrayInputStream(packet.spriteMapBytes);
             ObjectInputStream in = new ObjectInputStream(bis)) {
            spriteMap = (Map<String, SpriteData>) in.readObject();
        }

        System.out.println("### DEBUG: Received spriteMap with " + spriteMap.size() + " entries. Keys: " + spriteMap.keySet());

        GameSaver.reloadAllSprites(game, spriteMap);
        game.rand = new Random(1);

        return game;
    }

    public static List<Field> getAllFields(Class<?> type) {
        List<Field> fields = new ArrayList<>();
        for (Class<?> c = type; c != null; c = c.getSuperclass()) {
            fields.addAll(Arrays.asList(c.getDeclaredFields()));
        }
        return fields;
    }
}
