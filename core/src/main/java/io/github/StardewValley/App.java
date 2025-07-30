package io.github.StardewValley;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.Array;
import data.DataLoader;
import io.github.StardewValley.controllers.GameController;
import io.github.StardewValley.controllers.ViewController;
import io.github.StardewValley.network.MultiplayerProxy;
import io.github.StardewValley.views.menu.CLI.Colors;
import io.github.StardewValley.views.menu.CLI.MainMenuView;
import io.github.StardewValley.views.menu.CLI.Menu;
import io.github.StardewValley.views.menu.CLI.RegisterMenuView;
import io.github.StardewValley.views.menu.GUI.GameScreen;
import models.game.Game;
import models.game.Player;
import models.user.User;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class App implements Serializable {
    private static App instance;

    public Menu currentMenu = RegisterMenuView.getInstance();
    public Game game = null;
    public GameController currentPlayerController = null;
    public ViewController currentPlayerViewController = null;

    public Map<String, GameController> gameControllers;

    //IT WILL BE NO LONGER NEEDED (NETWORK WILL HANDLE IT)!!!!
    //server should already know which user is registered
    //public User registeredUser = null;

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
        //TODO :)))
        /*
        List<User> users = DataBaseController.readAllUsers();
        for (User user : users) {
            if (user.isStayLoggedIn()) return user;
        }
         */
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
        return Colors.color(Colors.PURPLE, App.getInstance().logedInUser.getUsername() + " " +
                Colors.color(Colors.GREEN, game.getGameDate().compactString()));
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

    public void initGame(Game game) {

        gameControllers = new HashMap<>();
        for (Player player : game.getPlayers()) {
            String username = player.getUser().getUsername();
            GameController gc = new GameController(game, player);

            if (username.equals(logedInUser.getUsername())) {
                currentPlayerViewController = new ViewController(gc);
                try {
                    gc = MultiplayerProxy.create(gc);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                currentPlayerController = gc;
            }

            gameControllers.put(username, gc);
        }

        Main.getInstance().setScreen(new GameScreen());
    }
}
