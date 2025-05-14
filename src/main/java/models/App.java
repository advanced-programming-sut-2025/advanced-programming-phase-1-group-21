package models;

import models.data.DataLoader;
import models.game.Game;
import models.user.User;
import views.menu.GameTerminalView;

import java.util.Random;

public class App {
    public static Menu currentMenu;
    public static Game game;
    public static boolean play;
    public static User registeredUser;
    public static User logedInUser;
    public static final Random random = new Random(1);

    static {
        DataLoader.load();
    }

    public static void reset() {
        currentMenu = Menu.RegisterMenu;
        game = null;
        play = true;
        registeredUser = null;
        logedInUser = null;
    }

    public static String getUI() {
        if (game == null) return "";
        return GameTerminalView.PURPLE + game.getCurrentPlayer().getUser().getUsername() + GameTerminalView.RESET + " " + GameTerminalView.GREEN + game.getGameDate().compactString() + GameTerminalView.RESET;
    }
}
