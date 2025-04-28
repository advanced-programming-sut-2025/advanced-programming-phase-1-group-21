package views.menu;

import controllers.GameController;
import controllers.RegisterMenuController;
import models.command.RegisterMenuCommand;

import java.io.IOException;
import java.util.regex.Matcher;

public class RegisterMenuView {
    Matcher matcher;
    RegisterMenuController registerMenuController = new RegisterMenuController();
    GameController gameController = new GameController();

    public void Result(String command) throws IOException {
        if((matcher = RegisterMenuCommand.register.getMatcher(command)) != null){
            System.out.println(registerMenuController.register(matcher.group("username") , matcher.group("password")
                    , matcher.group("passwordConfirm") , matcher.group("nickname") , matcher.group("email")
                    , matcher.group("gender")).getMessage());
        }

        else if((matcher = RegisterMenuCommand.pickQuestion.getMatcher(command)) != null){
            throw new UnsupportedOperationException("Not supported yet.");
        }

        else if((matcher = RegisterMenuCommand.SHOW_CURRENT_MENU.getMatcher(command)) != null){
            System.out.println(registerMenuController.showCurrentMenu().getMessage());
        }

        else if((matcher = RegisterMenuCommand.pickQuestion.getMatcher(command)) != null){
            System.out.println(registerMenuController.pickQuestion(matcher.group("answer") ,
                    matcher.group("answerConfirm") , matcher.group("questionNumber")).getMessage());
        }

        else if((matcher = RegisterMenuCommand.PRINT_MAP.getMatcher(command)) != null){
            gameController.printMap();
        }

        else
            System.out.println("invalid command");

    }
}
