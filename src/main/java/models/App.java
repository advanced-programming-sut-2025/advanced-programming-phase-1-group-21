package models;

import controllers.DataBaseController;
import models.game.Game;
import models.user.User;

import java.util.List;

public class App {
    public static Menu currentMenu = Menu.RegisterMenu;
    public static Game game = null;
    public static boolean play = true;
    public static User registeredUser;
    public static User logedInUser;

    public final static List<models.crops.SeedSource> crops = DataBaseController.loadCropsFromCSV("crops.csv");
}
