package views.menu;

import controllers.RegisterMenuController;
import models.command.RegisterMenuCommand;

import java.util.regex.Matcher;

public class ShopMenuView {
    Matcher matcher;
    RegisterMenuController registerMenuController = new RegisterMenuController();

    public void Result(String command){
        if((matcher = RegisterMenuCommand.register.getMatcher(command)) != null){
            throw new UnsupportedOperationException("Not supported yet.");
        }

        if((matcher = RegisterMenuCommand.pickQuestion.getMatcher(command)) != null){
            throw new UnsupportedOperationException("Not supported yet.");
        }

    }
}
