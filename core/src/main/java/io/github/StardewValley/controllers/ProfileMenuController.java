package io.github.StardewValley.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.StardewValley.App;
import io.github.StardewValley.views.menu.CLI.MainMenuView;
import io.github.StardewValley.models.result.Result;
import io.github.StardewValley.models.result.errorTypes.AuthError;
import io.github.StardewValley.models.result.errorTypes.MenuError;
import io.github.StardewValley.models.user.User;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import static io.github.StardewValley.controllers.DataBaseController.findUserByUsername;
import static io.github.StardewValley.controllers.RegisterMenuController.*;

public class ProfileMenuController{
    Gson gson = new GsonBuilder().setPrettyPrinting().create();


    public Result<Void> changeUsername(String newUsername) {
        if(!usernameValidation(newUsername))
            return Result.failure(AuthError.INVALID_USERNAME);
        if(findUserByUsername(newUsername) != null)
            return Result.failure(AuthError.USER_ALREADY_EXISTS);

        ArrayList<User> users = DataBaseController.readAllUsers(gson , "Users.json");
        for(User user : users){
            if(user.getUsername().equals(App.getInstance().logedInUser.getUsername())){
                user.setUsername(newUsername);
                App.getInstance().logedInUser.setUsername(newUsername);

                try (FileWriter writer = new FileWriter("Users.json")) {
                    gson.toJson(users, writer);
                } catch (IOException e) {
                    System.err.println("error" + e.getMessage());
//                    throw e;
                }

                return Result.success("Username changed successfully");
            }
        }

        return Result.success(null);
    }

    public Result<Void> changePassword(String oldPassword, String newPassword) {
        if(App.getInstance().logedInUser.verifyPassword(oldPassword))
            return Result.failure(AuthError.PASSWORD_CONFIRM_ERROR);
        if(checkPassword(newPassword).isError())
            return checkPassword(newPassword);

        ArrayList<User> users = DataBaseController.readAllUsers(gson , "Users.json");
        for(User user : users){
            if(user.getUsername().equals(App.getInstance().logedInUser.getUsername())){
                user.setPassword(newPassword);
                App.getInstance().logedInUser.setPassword(newPassword);

                try (FileWriter writer = new FileWriter("Users.json")) {
                    gson.toJson(users, writer);
                } catch (IOException e) {
                    System.err.println("error" + e.getMessage());
//                    throw e;
                }

                return Result.success("Password changed successfully");
            }
        }

        try (FileWriter writer = new FileWriter("Users.json")) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            System.err.println("error" + e.getMessage());
//            throw e;
        }

        return Result.success(null);
    }

    public Result<Void> changeEmail(String newEmail) {
        if(!emailValidation(newEmail))
            return Result.failure(AuthError.INVALID_EMAIL_FORMAT);

        ArrayList<User> users = DataBaseController.readAllUsers(gson , "Users.json");
        for(User user : users){
            if(user.getUsername().equals(App.getInstance().logedInUser.getUsername())){
                user.setEmail(newEmail);
                App.getInstance().logedInUser.setEmail(newEmail);

                try (FileWriter writer = new FileWriter("Users.json")) {
                    gson.toJson(users, writer);
                } catch (IOException e) {
                    System.err.println("error" + e.getMessage());
//                    throw e;
                }

                return Result.success("Email changed successfully");
            }
        }

        try (FileWriter writer = new FileWriter("Users.json")) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            System.err.println("error" + e.getMessage());
//            throw e;
        }

        return Result.success(null);

    }

    public Result<Void> changeNickname(String newNickname) {
        ArrayList<User> users = DataBaseController.readAllUsers(gson , "Users.json");
        for(User user : users){
            if(user.getUsername().equals(App.getInstance().logedInUser.getUsername())){
                user.setNickname(newNickname);
                App.getInstance().logedInUser.setNickname(newNickname);

                try (FileWriter writer = new FileWriter("Users.json")) {
                    gson.toJson(users, writer);
                } catch (IOException e) {
                    System.err.println("error" + e.getMessage());
//                    throw e;
                }

                return Result.success("Nickname changed successfully");
            }
        }

        try (FileWriter writer = new FileWriter("Users.json")) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            System.err.println("error" + e.getMessage());
//            throw e;
        }

        return Result.success(null);
    }

    public Result<ArrayList<String>> userInfo() {
        ArrayList<String> output = new ArrayList<>();
        output.add("your info:");
        output.add("username : " + App.getInstance().logedInUser.getUsername());
        output.add("email : " + App.getInstance().logedInUser.getEmail());
        output.add("nickname : " + App.getInstance().logedInUser.getNickname());
        return Result.success(output);
    }

    public Result<Void> menuEnter(String menu) {
        if(menu.equals("mainmenu")){
            App.getInstance().currentMenu = MainMenuView.getInstance();
            return Result.success(null);
        }

        return Result.failure(MenuError.MENU_ACCESS_DENIED);
    }
}
