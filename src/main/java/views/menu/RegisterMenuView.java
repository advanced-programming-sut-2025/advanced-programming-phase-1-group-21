package views.menu;

import controllers.RegisterMenuController;
import models.command.RegisterMenuCommand;

import java.util.regex.Matcher;

public class RegisterMenuView {
    Matcher matcher;
    RegisterMenuController registerMenuController = new RegisterMenuController();

    public void Result(String command){
        if((matcher = RegisterMenuCommand.register.getMatcher(command)) != null){
            throw new UnsupportedOperationException("Not supported yet.");
        }

        if((matcher = RegisterMenuCommand.pickQuestion.getMatcher(command)) != null){
            throw new UnsupportedOperationException("Not supported yet.");
        }

        if((matcher = RegisterMenuCommand.SHOW_CURRENT_MENU.getMatcher(command)) != null){
            System.out.println(registerMenuController.showCurrentMenu().getMessage());
        }

    }
}
