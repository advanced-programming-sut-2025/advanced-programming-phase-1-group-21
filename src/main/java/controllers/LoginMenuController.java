package controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import models.App;
import models.Menu;
import models.result.Result;
import models.result.errorTypes.AuthError;
import models.result.errorTypes.MenuError;
import models.result.errorTypes.UserError;
import models.user.User;
import views.menu.LoginMenuView;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class LoginMenuController{
    public Result<String> showCurrentMenu(){
        return Result.success("login menu", "");
    }

    public Result<User> login(String username, String password) {
        if(findUserByUsername(username) == null)
            return Result.failure(UserError.USER_NOT_FOUND);
        if(!findUserByUsername(username).verifyPassword(password))
            return Result.failure(UserError.PASSWORD_DOESNT_MATCH);

        //TODO
        //Stay Logged In

        App.logedInUser = findUserByUsername(username);
        return Result.success(App.logedInUser , "User logged in successfully");
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
        if(menu.equals(Menu.RegisterMenu.toString())){
            App.currentMenu = Menu.RegisterMenu;
            return Result.success(null);
        }

        if(menu.equals("mainmenu")){
            if(App.logedInUser == null)
                return Result.failure(UserError.SHOULD_LOGIN_FIRST);
            App.currentMenu = Menu.MainMenu;
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
