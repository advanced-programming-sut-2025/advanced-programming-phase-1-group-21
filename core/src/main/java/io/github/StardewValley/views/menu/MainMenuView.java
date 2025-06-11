package io.github.StardewValley.views.menu;

import io.github.StardewValley.controllers.MainMenuController;
import io.github.StardewValley.models.App;
import io.github.StardewValley.models.Menu;
import io.github.StardewValley.models.command.MainMenuCommand;

import java.io.IOException;
import java.util.regex.Matcher;

public class MainMenuView {


    Matcher matcher;
    MainMenuController mainMenuController = new MainMenuController();


    public void Result(String command) throws IOException {
        if((matcher = MainMenuCommand.LOGOUT.getMatcher(command)) != null) {
            System.out.println(mainMenuController.logout());
        }

        else if((matcher = MainMenuCommand.SHOW_CURRENT_MENU.getMatcher(command)) != null){
            System.out.println(mainMenuController.showCurrentMenu());
        }

        else if((matcher = MainMenuCommand.ENTER_MENU.getMatcher(command)) != null){
            System.out.println(mainMenuController.enterMenu(matcher.group("menu").trim()));
        }

        else if((matcher = MainMenuCommand.EXIT_MENU.getMatcher(command)) != null){
            App.getInstance().currentMenu = Menu.LoginMenu;
        }

        else
            System.out.println("invalid command");
    }

}
