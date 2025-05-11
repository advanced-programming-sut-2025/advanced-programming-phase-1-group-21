package models;

import models.data.DataLoader;
import models.game.Game;
import models.user.User;

import java.util.Random;

public class App {
    public static Menu currentMenu = Menu.RegisterMenu;
    public static Game game = null;
    public static boolean play = true;
    public static User registeredUser;
    public static User logedInUser;
    public static final Random random = new Random(1);

    static {
        DataLoader.load();
    }
}
