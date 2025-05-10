package models;

import controllers.DataBaseController;
import models.Tool.ToolMaterialType;
import models.crop.SeedInfo;
import models.game.Game;
import models.game.Item;
import models.map.Foraging;
import models.map.TileType;
import models.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class App {
    public static Menu currentMenu = Menu.RegisterMenu;
    public static Game game = null;
    public static boolean play = true;
    public static User registeredUser;
    public static User logedInUser;
    public final static List<SeedInfo> crops = null;

    public static SeedInfo getSeedInfoByName(String seedName) {
        return SeedInfo.getSeedInfo(seedName);
    }
}
