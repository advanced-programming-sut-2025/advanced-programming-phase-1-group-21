package models;

import models.data.DataLoader;
import models.game.Game;
import models.user.User;
import views.menu.Colors;

import java.io.*;
import java.util.Random;

public class App implements Serializable {
    private static App instance;

    public Menu currentMenu = Menu.RegisterMenu;
    public Game game = null;
    public User registeredUser = null;
    public User logedInUser = null;
    public final Random random = new Random(1);

    private App() {
        DataLoader.load();
    }

    public static void setInstance(App app) {
        instance = app;
    }

    public static App getInstance() {
        if (instance == null) {
            instance = new App();
        }
        return instance;
    }

    public static void reset() {
        instance = new App();
    }

    public String getUI() {
        if (game == null) return "";
        return Colors.color(Colors.PURPLE, game.getCurrentPlayer().getUser().getUsername()) + " " +
                Colors.color(Colors.GREEN, game.getGameDate().compactString()) + " " +
                Colors.color(Colors.YELLOW, "" + game.getCurrentPlayer().getEnergy());
    }

    public Random getRandom() { return random; }
}