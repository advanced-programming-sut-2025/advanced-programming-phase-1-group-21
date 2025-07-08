package io.github.StardewValley.views.menu;

import io.github.StardewValley.controllers.ProfileMenuController;
import io.github.StardewValley.models.command.ProfileMenuCommand;

import java.io.IOException;
import java.util.regex.Matcher;

public class ProfileMenuView implements Menu {

    Matcher matcher;
    ProfileMenuController profileMenuController = new ProfileMenuController();
    private static ProfileMenuView instance;

    private ProfileMenuView() {
    }

    public static ProfileMenuView getInstance() {
        if (instance == null)
            instance = new ProfileMenuView();
        return instance;
    }

    public void Result(String command) {
        if((matcher = ProfileMenuCommand.USERINFO.getMatcher(command)) != null){
            for(String string : profileMenuController.userInfo().getData())
                System.out.println(string);
        }

        else if((matcher = ProfileMenuCommand.CHANGE_USERNAME.getMatcher(command)) != null){
            System.out.println(profileMenuController.changeUsername(matcher.group("newUsername")));
        }

        else if((matcher = ProfileMenuCommand.CHANGE_PASSWORD.getMatcher(command)) != null){
            System.out.println(profileMenuController.changePassword(matcher.group("oldPassword"),
                    matcher.group("newPassword")));
        }

        else if((matcher = ProfileMenuCommand.CHANGE_EMAIL.getMatcher(command)) != null){
            System.out.println(profileMenuController.changeEmail(matcher.group("newEmail")));
        }

        else if((matcher = ProfileMenuCommand.CHANGE_NICKNAME.getMatcher(command)) != null){
            System.out.println(profileMenuController.changeNickname(matcher.group("newNickname")));
        }

        else if((matcher = ProfileMenuCommand.ENTER_MENU.getMatcher(command)) != null){
            System.out.println(profileMenuController.menuEnter(matcher.group("menu")));
        }

        else
            System.out.println("Invalid command");
    }
}
