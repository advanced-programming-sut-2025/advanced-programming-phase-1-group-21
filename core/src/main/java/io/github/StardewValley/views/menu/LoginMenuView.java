package io.github.StardewValley.views.menu;

import io.github.StardewValley.controllers.LoginMenuController;
import io.github.StardewValley.models.command.LoginMenuCommand;

import java.io.IOException;
import java.util.regex.Matcher;

public class LoginMenuView {
    Matcher matcher;
    LoginMenuController loginMenuController = new LoginMenuController();

    public static String getAnswer(){
        System.out.print("Answer the question: ");
        return AppView.scanner.nextLine();
    }

    public static String getPassword(){
        System.out.print("Enter your new password: ");
        return AppView.scanner.nextLine();
    }

    public static String getConfirmPassword(){
        System.out.println("Enter your new password again: ");
        return AppView.scanner.nextLine();
    }

    public void Result(String command) throws IOException {
        if((matcher = LoginMenuCommand.FORGET_PASSWORD.getMatcher(command)) != null){
            System.out.println(loginMenuController.forgetPassword(matcher.group("username")));
        }

        else if((matcher = LoginMenuCommand.SHOW_CURRENT_MENU.getMatcher(command)) != null){
            System.out.println(loginMenuController.showCurrentMenu());
        }

        else if((matcher = LoginMenuCommand.LOGIN.getMatcher(command)) != null){
            boolean stayLoggedIn = matcher.group("stay") != null;
            System.out.println(loginMenuController.login(matcher.group("username") ,
                    matcher.group("password"), stayLoggedIn));
        }

        else if((matcher = LoginMenuCommand.ENTER_MENU.getMatcher(command)) != null){
            System.out.println(loginMenuController.changeMenu(matcher.group("menu")));
        }

        else
            System.out.println("invalid command");
    }

}
