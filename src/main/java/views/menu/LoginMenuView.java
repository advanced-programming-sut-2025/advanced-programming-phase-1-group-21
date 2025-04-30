package views.menu;

import controllers.LoginMenuController;
import models.command.LoginMenuCommand;

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
            System.out.println(loginMenuController.forgetPassword(matcher.group("username")).getMessage());
        }

        else if((matcher = LoginMenuCommand.SHOW_CURRENT_MENU.getMatcher(command)) != null){
            System.out.println(loginMenuController.showCurrentMenu().getMessage());
        }

        else if((matcher = LoginMenuCommand.LOGIN.getMatcher(command)) != null){
            System.out.println(loginMenuController.login(matcher.group("username") ,
                    matcher.group("password")).getMessage());
        }

        else if((matcher = LoginMenuCommand.ENTER_MENU.getMatcher(command)) != null){
            System.out.println(loginMenuController.changeMenu(matcher.group("menu")).getMessage());
        }

        else
            System.out.println("invalid command");
    }

}
