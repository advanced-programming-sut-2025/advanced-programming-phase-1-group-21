package io.github.StardewValley.views.menu.CLI;

import io.github.StardewValley.controllers.MainMenuController;
import io.github.StardewValley.App;
import models.command.MainMenuCommand;

import java.util.regex.Matcher;

public class MainMenuView implements Menu {
    Matcher matcher;
    MainMenuController mainMenuController = new MainMenuController();
    private static MainMenuView instance;

    private MainMenuView() {
    }

    public static MainMenuView getInstance() {
        if (instance == null)
            instance = new MainMenuView();
        return instance;
    }


    public void Result(String command) {
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
            App.getInstance().currentMenu = LoginMenuView.getInstance();
        }

        else
            System.out.println("invalid command");
    }

    public String ResultText(String command) {
        if((matcher = MainMenuCommand.LOGOUT.getMatcher(command)) != null) {
            return mainMenuController.logout().toString();
        }

        else if((matcher = MainMenuCommand.SHOW_CURRENT_MENU.getMatcher(command)) != null){
            return mainMenuController.showCurrentMenu().toString();
        }

        else if((matcher = MainMenuCommand.ENTER_MENU.getMatcher(command)) != null){
            return mainMenuController.enterMenu(matcher.group("menu").trim()).toString();
        }

        else if((matcher = MainMenuCommand.EXIT_MENU.getMatcher(command)) != null){
            App.getInstance().currentMenu = LoginMenuView.getInstance();
        }
            return "invalid command".toString();
    }

}
