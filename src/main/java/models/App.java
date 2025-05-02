package models;

import controllers.DataBaseController;
import models.crop.SeedInfo;
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

    public final static List<SeedInfo> crops = DataBaseController.loadCropsFromCSV("crops.csv");

    public static SeedInfo getSeedInfoByName(String seedName) {
        return SeedInfo.getSeedInfo(seedName);
    }
}
