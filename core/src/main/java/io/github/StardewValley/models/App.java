package io.github.StardewValley.models;

import io.github.StardewValley.controllers.DataBaseController;
import io.github.StardewValley.models.data.DataLoader;
import io.github.StardewValley.models.game.Game;
import io.github.StardewValley.models.user.User;
import io.github.StardewValley.views.menu.*;

import java.io.*;
import java.util.List;
import java.util.Random;

public class App implements Serializable {
    private static App instance;

    public Menu currentMenu = RegisterMenuView.getInstance();
    public Game game = null;
    public User registeredUser = null;
    public User logedInUser = null;
    public final Random random = new Random(1);

    private App() {
        DataLoader.load();
        User user = getStayLoggedIn();
        if (user != null) {
            System.err.println("we found " + user.getUsername() + " was logged in.... logging in...");
            currentMenu = MainMenuView.getInstance();
            logedInUser = user;
        }
    }

    public User getStayLoggedIn() {
        List<User> users = DataBaseController.readAllUsers();
        for (User user : users) {
            if (user.isStayLoggedIn()) return user;
        }
        return null;
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
