package io.github.StardewValley;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.Array;
import io.github.StardewValley.controllers.DataBaseController;
import data.DataLoader;
import io.github.StardewValley.views.menu.CLI.Colors;
import io.github.StardewValley.views.menu.CLI.MainMenuView;
import io.github.StardewValley.views.menu.CLI.Menu;
import io.github.StardewValley.views.menu.CLI.RegisterMenuView;
import models.game.Game;
import models.user.User;

import java.io.*;
import java.util.List;
import java.util.Random;

public class App implements Serializable {
    private static App instance;

    public Menu currentMenu = RegisterMenuView.getInstance();
    public Screen currentScreen = null;
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

    public Array<String> getSecurityQuestions() {
        return new Array<>(new String[] {
                "1. WHAT'S YOUR FAVORITE FOOD?",
                "2. WHAT'S YOUR FAVORITE COLOR?",
                "3. WHO'S YOUR FAVORITE SINGER?",
                "4. WHO'S YOUR FAVORITE ACTOR?",
                "5. WHERE'S YOUR FAVORITE CITY?",
                "6. WHERE'S YOUR FAVORITE COUNTRY?"
        });
    }
}
