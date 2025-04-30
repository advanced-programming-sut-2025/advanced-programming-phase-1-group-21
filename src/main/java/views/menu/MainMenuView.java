package views.menu;

import controllers.MainMenuController;
import models.command.MainMenuCommand;

import java.util.regex.Matcher;

public class MainMenuView {


    Matcher matcher;
    MainMenuController mainMenuController = new MainMenuController();


    public void Result(String command){
        if((matcher = MainMenuCommand.LOGOUT.getMatcher(command)) != null){
            throw new UnsupportedOperationException("Not supported yet.");
        }

        else if((matcher = MainMenuCommand.SHOW_CURRENT_MENU.getMatcher(command)) != null){
            System.out.println(mainMenuController.showCurrentMenu().getMessage());
        }

        else if((matcher = MainMenuCommand.SHOW_CURRENT_MENU.getMatcher(command)) != null){
            System.out.println(mainMenuController.showCurrentMenu().getMessage());
        }

        else if((matcher = MainMenuCommand.ENTER_MENU.getMatcher(command)) != null){
            System.out.println(mainMenuController.enterMenu(matcher.group("menu").trim()).getMessage());
        }

        else if((matcher = MainMenuCommand.EXIT_MENU.getMatcher(command)) != null){
            throw new UnsupportedOperationException("Not supported yet.");
        }

        else
            System.out.println("invalid command");
    }

}
