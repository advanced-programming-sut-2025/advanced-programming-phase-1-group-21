package io.github.StardewValley.controllers;

import io.github.StardewValley.App;
import models.result.Result;
import models.result.errorTypes.GameError;

import java.io.*;

public class GameSaver {

    public static void saveApp(App app, String filename) {
        filename = "user-data/" + filename + ".dat";
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(app);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Result<App> loadApp(String filename) {
        filename = "user-data/" + filename + ".dat";
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            App app = (App) in.readObject();
            System.out.println("Game loaded successfully.");
            return Result.success(app);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return Result.failure(GameError.GAME_NOT_FOUND);
        }
    }
}
