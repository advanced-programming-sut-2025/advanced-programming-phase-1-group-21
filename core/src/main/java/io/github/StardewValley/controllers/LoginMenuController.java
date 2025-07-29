package io.github.StardewValley.controllers;

import io.github.StardewValley.App;
import io.github.StardewValley.network.NetworkDataBaseController;
import io.github.StardewValley.views.menu.CLI.MainMenuView;
import io.github.StardewValley.views.menu.CLI.LoginMenuView;
import io.github.StardewValley.views.menu.CLI.RegisterMenuView;
import models.result.Result;
import models.result.errorTypes.AuthError;
import models.result.errorTypes.MenuError;
import models.result.errorTypes.UserError;
import models.user.User;

public class LoginMenuController{
    public Result<String> showCurrentMenu(){
        return Result.success("login menu", "login menu");
    }

    public Result<Void> login(String username, String password, boolean stayLoggedIn) {
        if (!NetworkDataBaseController.doesUserExists(username))
            return Result.failure(UserError.USER_NOT_FOUND);
        Result<User> result = NetworkDataBaseController.login(username, password, stayLoggedIn);
        if (result.isSuccess()) {
            App.getInstance().logedInUser = result.getData();
            return Result.success(null);
        }
        return Result.failure(result.getError());
    }

    public Result<String> getQuestion(String username) {
        return NetworkDataBaseController.getSecurityQuestion(username);
    }

    public Result<Void> forgetPass(String username, String answer, String password, String passwordConfirm) {
        if(NetworkDataBaseController.doesUserExists(username))
            return Result.failure(UserError.USER_NOT_FOUND);

        Result<String> question = NetworkDataBaseController.getSecurityQuestion(username);
        if (question.isError())
            return Result.failure(question.getError());

        if(!answer.equals(question.getData()))
            return Result.failure(UserError.INCORRECT_ANSWER);

        if(checkPassword(password).isError())
            return checkPassword(password);

        if(!password.equals(passwordConfirm))
            return Result.failure(AuthError.PASSWORD_CONFIRM_ERROR);

        Result r = NetworkDataBaseController.setPassword(username, password);
        if (r.isError())
            return Result.failure(r.getError());
        return r;
    }

    public Result<Void> forgetPassword(String username) {
        throw new RuntimeException("[ERROR] TODO, Actually this part of code is invalidated!");

        /*
        if(findUserByUsername(username) == null)
            return Result.failure(UserError.USER_NOT_FOUND);
        String answer = LoginMenuView.getAnswer();
        if(!answer.equals(findUserByUsername(username).getSecurityAnswer()))
            return Result.failure(UserError.INCORRECT_ANSWER);
        String password = LoginMenuView.getPassword();
        if(checkPassword(password).isError())
            return checkPassword(password);

        String passwordConfirm = LoginMenuView.getConfirmPassword();
        if(!password.equals(passwordConfirm))
            return Result.failure(AuthError.PASSWORD_CONFIRM_ERROR);


        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        ArrayList<User> users = readAllUsers(gson , "Users.json");
        for(User user : users){
            if(user.getUsername().equals(username))
                user.setPassword(password);
        }

        try (FileWriter writer = new FileWriter("Users.json")) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            System.err.println("error" + e.getMessage());
//            throw e;
        }
        */

        //return Result.success(null, "Password changed successfully");
    }

    public Result<Void> changeMenu(String menu){
        if(menu.equals("register")){
            App.getInstance().currentMenu = RegisterMenuView.getInstance();
            return Result.success(null);
        }

        if(menu.equals("mainmenu")){
            if(App.getInstance().logedInUser == null)
                return Result.failure(UserError.SHOULD_LOGIN_FIRST);
            App.getInstance().currentMenu = MainMenuView.getInstance();
            return Result.success(null);
        }
        else
            return Result.failure(MenuError.MENU_ACCESS_DENIED);
    }

    private Result<Void> checkPassword(String password) {
        return RegisterMenuController.checkPassword(password);
    }

    public static Result<Void> logout() {
        if (App.getInstance().logedInUser == null) {
            System.out.println("[WARN] You are not logged in!");
            return Result.failure(UserError.USER_NOT_FOUND);
        }
        NetworkDataBaseController.logout();
        return Result.success(null);
    }
}
