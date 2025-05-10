package controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.App;
import models.Menu;
import models.result.Result;
import models.result.errorTypes.AuthError;
import models.result.errorTypes.MenuError;
import models.user.User;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import static controllers.DataBaseController.findUserByUsername;
import static controllers.RegisterMenuController.*;

public class ProfileMenuController{
    Gson gson = new GsonBuilder().setPrettyPrinting().create();


    public Result<Void> changeUsername(String newUsername) throws IOException {
        if(!usernameValidation(newUsername))
            return Result.failure(AuthError.INVALID_USERNAME);
        if(findUserByUsername(newUsername) != null)
            return Result.failure(AuthError.USER_ALREADY_EXISTS);

        ArrayList<User> users = DataBaseController.readAllUsers(gson , "Users.json");
        for(User user : users){
            if(user.getUsername().equals(App.logedInUser.getUsername())){
                user.setUsername(newUsername);
                App.logedInUser.setUsername(newUsername);

                try (FileWriter writer = new FileWriter("Users.json")) {
                    gson.toJson(users, writer);
                } catch (IOException e) {
                    System.err.println("error" + e.getMessage());
                    throw e;
                }

                return Result.success("Username changed successfully");
            }
        }

        return Result.success(null);
    }

    public Result<Void> changePassword(String oldPassword, String newPassword) throws IOException {
        if(!oldPassword.equals(App.logedInUser.getPassword()))
            return Result.failure(AuthError.PASSWORD_CONFIRM_ERROR);
        if(checkPassword(newPassword).isError())
            return checkPassword(newPassword);

        ArrayList<User> users = DataBaseController.readAllUsers(gson , "Users.json");
        for(User user : users){
            if(user.getUsername().equals(App.logedInUser.getUsername())){
                user.setPassword(newPassword);
                App.logedInUser.setPassword(newPassword);

                try (FileWriter writer = new FileWriter("Users.json")) {
                    gson.toJson(users, writer);
                } catch (IOException e) {
                    System.err.println("error" + e.getMessage());
                    throw e;
                }

                return Result.success("Password changed successfully");
            }
        }

        try (FileWriter writer = new FileWriter("Users.json")) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            System.err.println("error" + e.getMessage());
            throw e;
        }

        return Result.success(null);
    }

    public Result<Void> changeEmail(String newEmail) throws IOException {
        if(!emailValidation(newEmail))
            return Result.failure(AuthError.INVALID_EMAIL_FORMAT);

        ArrayList<User> users = DataBaseController.readAllUsers(gson , "Users.json");
        for(User user : users){
            if(user.getUsername().equals(App.logedInUser.getUsername())){
                user.setEmail(newEmail);
                App.logedInUser.setEmail(newEmail);

                try (FileWriter writer = new FileWriter("Users.json")) {
                    gson.toJson(users, writer);
                } catch (IOException e) {
                    System.err.println("error" + e.getMessage());
                    throw e;
                }

                return Result.success("Email changed successfully");
            }
        }

        try (FileWriter writer = new FileWriter("Users.json")) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            System.err.println("error" + e.getMessage());
            throw e;
        }

        return Result.success(null);

    }

    public Result<Void> changeNickname(String newNickname) throws IOException {
        ArrayList<User> users = DataBaseController.readAllUsers(gson , "Users.json");
        for(User user : users){
            if(user.getUsername().equals(App.logedInUser.getUsername())){
                user.setNickname(newNickname);
                App.logedInUser.setNickname(newNickname);

                try (FileWriter writer = new FileWriter("Users.json")) {
                    gson.toJson(users, writer);
                } catch (IOException e) {
                    System.err.println("error" + e.getMessage());
                    throw e;
                }

                return Result.success("Nickname changed successfully");
            }
        }

        try (FileWriter writer = new FileWriter("Users.json")) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            System.err.println("error" + e.getMessage());
            throw e;
        }

        return Result.success(null);
    }

    public Result<ArrayList<String>> userInfo() {
        ArrayList<String> output = new ArrayList<>();
        output.add("your info:");
        output.add("username : " + App.logedInUser.getUsername());
        output.add("email : " + App.logedInUser.getEmail());
        output.add("nickname : " + App.logedInUser.getNickname());
        return Result.success(output);
    }

    public Result<Void> menuEnter(String menu) {
        if(menu.equals("mainmenu")){
            App.currentMenu = Menu.MainMenu;
            return Result.success(null);
        }

        return Result.failure(MenuError.MENU_ACCESS_DENIED);
    }
}
