package views.menu;

import controllers.ProfileMenuController;
import models.command.ProfileMenuCommand;

import java.util.regex.Matcher;

public class ProfileMenuView {

    Matcher matcher;
    ProfileMenuController profileMenuController = new ProfileMenuController();

    public void Result(String command){
        if((matcher = ProfileMenuCommand.USERINFO.getMatcher(command)) != null){
            throw new UnsupportedOperationException("Not supported yet.");
        }

        if((matcher = ProfileMenuCommand.CHANGE_USERNAME.getMatcher(command)) != null){
            throw new UnsupportedOperationException("Not supported yet.");
        }

        if((matcher = ProfileMenuCommand.CHANGE_PASSWORD.getMatcher(command)) != null){
            throw new UnsupportedOperationException("Not supported yet.");
        }

        if((matcher = ProfileMenuCommand.CHANGE_EMAIL.getMatcher(command)) != null){
            throw new UnsupportedOperationException("Not supported yet.");
        }

        if((matcher = ProfileMenuCommand.CHANGE_NICKNAME.getMatcher(command)) != null){
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}
