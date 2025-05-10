package models;

import models.data.DataLoader;
import models.game.Game;
import models.user.User;

public class App {
    public static Menu currentMenu = Menu.RegisterMenu;
    public static Game game = null;
    public static boolean play = true;
    public static User registeredUser;
    public static User logedInUser;

    static {
        DataLoader.load();
    }
}
