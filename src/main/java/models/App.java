package models;

import controllers.DataBaseController;
import models.game.Game;
import models.game.Item;
import models.user.User;

import java.util.ArrayList;
import java.util.List;

public class App {
    public static Menu currentMenu = Menu.RegisterMenu;
    public static Game game = null;
    public static boolean play = true;
    public static User registeredUser;
    public static User logedInUser;

    public final static List<models.crops.SeedSource> crops = DataBaseController.loadCropsFromCSV("crops.csv");

    public static models.crops.SeedSource getSeedSourceByName(String seedName) {
        for (models.crops.SeedSource crop : crops) {
            if (crop.getName().equals(seedName)) {
                return crop;
            }
        }
        return null;
    }
}
