package io.github.StardewValley.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.StardewValley.App;
import io.github.StardewValley.network.NetworkDataBaseController;
import io.github.StardewValley.views.menu.CLI.MainMenuView;
import models.result.Result;
import models.result.errorTypes.AuthError;
import models.result.errorTypes.MenuError;
import models.user.User;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import static io.github.StardewValley.controllers.DataBaseController.findUserByUsername;
import static io.github.StardewValley.controllers.RegisterMenuController.*;

public class ProfileMenuController{
    public Result<Void> changeUsername(String newUsername) {
        if (!usernameValidation(newUsername))
            return Result.failure(AuthError.INVALID_USERNAME);
        if (NetworkDataBaseController.doesUserExists(newUsername))
            return Result.failure(AuthError.USER_ALREADY_EXISTS);

        return NetworkDataBaseController.setUsername(newUsername);
    }

    public Result<Void> changePassword(String oldPassword, String newPassword) {
        if(checkPassword(newPassword).isError())
            return checkPassword(newPassword);

        return NetworkDataBaseController.setPassword(oldPassword, newPassword);
    }

    public Result<Void> changeEmail(String newEmail) {
        if(!emailValidation(newEmail))
            return Result.failure(AuthError.INVALID_EMAIL_FORMAT);
        return NetworkDataBaseController.setEmail(newEmail);
    }

    public Result<Void> changeNickname(String newNickname) {
        return NetworkDataBaseController.setNickname(newNickname);
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
