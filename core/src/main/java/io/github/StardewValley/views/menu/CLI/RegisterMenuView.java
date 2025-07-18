package io.github.StardewValley.views.menu.CLI;

import io.github.StardewValley.controllers.RegisterMenuController;
import models.command.RegisterMenuCommand;
import models.result.Result;
import models.user.Gender;

import java.util.regex.Matcher;

public class RegisterMenuView implements Menu {
    Matcher matcher;
    RegisterMenuController registerMenuController = new RegisterMenuController();
    private static RegisterMenuView instance;

    private RegisterMenuView() {
    }

    public static RegisterMenuView getInstance() {
        if (instance == null)
            instance = new RegisterMenuView();
        return instance;
    }

    public void printQuestions(){
        System.out.println();
        System.out.println("Questions:");
        System.out.println("1. WHAT'S YOUR FAVORITE FOOD?");
        System.out.println("2. WHAT'S YOUR FAVORITE COLOR?");
        System.out.println("3. WHO'S YOUR FAVORITE SINGER?");
        System.out.println("4. WHO'S YOUR FAVORITE ACTOR?");
        System.out.println("5. WHERE'S YOUR FAVORITE CITY?");
        System.out.println("6. WHERE'S YOUR FAVORITE COUNTRY?");
        System.out.println();
    }

    public void Result(String command) {
        if((matcher = RegisterMenuCommand.REGISTER.getMatcher(command)) != null){
            Result<Void> result = registerMenuController.register(matcher.group("username") , matcher.group("password")
                    , matcher.group("passwordConfirm") , matcher.group("nickname") , matcher.group("email")
                    , Gender.getGenderByName(matcher.group("gender")));
            System.out.println(result);
            if(!result.isError())
                printQuestions();
        }

        else if((matcher = RegisterMenuCommand.PICK_QUESTION.getMatcher(command)) != null){
            System.out.println(registerMenuController.pickQuestion(matcher.group("answer") ,
                    matcher.group("answerConfirm") , matcher.group("questionNumber")));
        }

        else if((matcher = RegisterMenuCommand.SHOW_CURRENT_MENU.getMatcher(command)) != null){
            System.out.println(registerMenuController.showCurrentMenu());
        }

        else if((matcher = RegisterMenuCommand.GO_TO_LOGIN.getMatcher(command)) != null){
            System.out.println(registerMenuController.goToLogin());
        }

        else
            System.out.println("invalid command");

    }

    public String ResultText(String command) {
        if((matcher = RegisterMenuCommand.REGISTER.getMatcher(command)) != null){
            Result <Void> result = registerMenuController.register(matcher.group("username") , matcher.group("password")
                    , matcher.group("passwordConfirm") , matcher.group("nickname") , matcher.group("email")
                    , Gender.getGenderByName(matcher.group("gender")));
            if (!result.isError())
                printQuestions();
            else
                return result.toString();
        }

        else if((matcher = RegisterMenuCommand.PICK_QUESTION.getMatcher(command)) != null){
            System.out.println(registerMenuController.pickQuestion(matcher.group("answer") ,
                    matcher.group("answerConfirm") , matcher.group("questionNumber")));
        }

        else if((matcher = RegisterMenuCommand.SHOW_CURRENT_MENU.getMatcher(command)) != null){
            return registerMenuController.showCurrentMenu().toString();
        }

        else if((matcher = RegisterMenuCommand.GO_TO_LOGIN.getMatcher(command)) != null){
            return registerMenuController.goToLogin().toString();
        }
            return "invalid command".toString();

    }
}
