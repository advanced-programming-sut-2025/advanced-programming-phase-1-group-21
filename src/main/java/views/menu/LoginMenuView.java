package views.menu;

import controllers.LoginMenuController;
import models.command.LoginMenuCommand;

import java.util.regex.Matcher;

public class LoginMenuView {
    Matcher matcher;
    LoginMenuController loginMenuController = new LoginMenuController();

    public void Result(String command){
        if((matcher = LoginMenuCommand.FORGET_PASSWORD.getMatcher(command)) != null){
            throw new UnsupportedOperationException("Not supported yet.");
        }

        if((matcher = LoginMenuCommand.ANSWER.getMatcher(command)) != null){
            throw new UnsupportedOperationException("Not supported yet.");
        }

        if((matcher = LoginMenuCommand.LOGIN.getMatcher(command)) != null){
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

}
