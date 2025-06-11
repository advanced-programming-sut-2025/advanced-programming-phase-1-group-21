package io.github.StardewValley.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import io.github.StardewValley.models.App;
import io.github.StardewValley.models.Menu;
import io.github.StardewValley.models.result.Result;
import io.github.StardewValley.models.result.errorTypes.AuthError;
import io.github.StardewValley.models.result.errorTypes.MenuError;
import io.github.StardewValley.models.result.errorTypes.UserError;
import io.github.StardewValley.models.user.User;
import io.github.StardewValley.views.menu.LoginMenuView;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class LoginMenuController{
    public Result<String> showCurrentMenu(){
        return Result.success("login menu", "login menu");
    }

    public Result<User> login(String username, String password, boolean stayLoggedIn) throws IOException {
        if(findUserByUsername(username) == null)
            return Result.failure(UserError.USER_NOT_FOUND);
        if(!findUserByUsername(username).verifyPassword(password))
            return Result.failure(UserError.PASSWORD_DOESNT_MATCH);

        User user = findUserByUsername(username);
        user.setStayLoggedIn(stayLoggedIn);
        DataBaseController.editUser(user);

        App.getInstance().logedInUser = findUserByUsername(username);
        return Result.success(App.getInstance().logedInUser , "User logged in successfully");
    }

    public Result<Void> forgetPassword(String username) throws IOException {
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
            throw e;
        }

        return Result.success(null, "Password changed successfully");
    }

    public Result<Void> changeMenu(String menu){
        if(menu.equals("register")){
            App.getInstance().currentMenu = Menu.RegisterMenu;
            return Result.success(null);
        }

        if(menu.equals("mainmenu")){
            if(App.getInstance().logedInUser == null)
                return Result.failure(UserError.SHOULD_LOGIN_FIRST);
            App.getInstance().currentMenu = Menu.MainMenu;
            return Result.success(null);
        }
        else
            return Result.failure(MenuError.MENU_ACCESS_DENIED);
    }

    private ArrayList<User> readAllUsers(Gson gson , String filePath){
        return DataBaseController.readAllUsers(gson, filePath);
    }

    private User findUserByUsername(String username) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        ArrayList<User> users = readAllUsers(gson , "Users.json");
        for(User user : users)
            if (user.getUsername().equals(username))
                return user;
        return null;
    }

    private Result<Void> checkPassword(String password) {
        return RegisterMenuController.checkPassword(password);
    }

}
