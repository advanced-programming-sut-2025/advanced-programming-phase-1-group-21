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

        if((matcher = MainMenuCommand.SHOW_CURRENT_MENU.getMatcher(command)) != null){
            throw new UnsupportedOperationException("Not supported yet.");
        }

        if((matcher = MainMenuCommand.ENTER_MENU.getMatcher(command)) != null){
            throw new UnsupportedOperationException("Not supported yet.");
        }

        if((matcher = MainMenuCommand.MENU_EXIT.getMatcher(command)) != null){
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

}
