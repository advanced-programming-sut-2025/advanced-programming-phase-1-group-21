package controllers;

import models.App;
import models.game.Game;
import models.result.Error;
import models.result.Result;
import models.result.errorTypes.GameError;
import models.result.errorTypes.MenuError;

import java.io.*;

public class GameSaver {

    public static void saveApp(App app, String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(app);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Result<App> loadApp(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            App app = (App) in.readObject();
            System.out.println("Game loaded successfully.");
            return Result.success(app);
        } catch (IOException | ClassNotFoundException e) {
            return Result.failure(GameError.GAME_NOT_FOUND);
        }
    }
}
