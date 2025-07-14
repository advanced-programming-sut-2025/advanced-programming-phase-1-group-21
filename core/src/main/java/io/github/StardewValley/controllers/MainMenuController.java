package io.github.StardewValley.controllers;

import io.github.StardewValley.App;
import io.github.StardewValley.views.menu.CLI.GameTerminalView;
import io.github.StardewValley.views.menu.CLI.ProfileMenuView;
import io.github.StardewValley.views.menu.CLI.RegisterMenuView;
import models.result.Result;
import models.user.User;

public class MainMenuController{

    public Result<Void> enterMenu(String menu) {
        if(menu.equals("game")){
            App.getInstance().currentMenu = GameTerminalView.getInstance();
            return Result.success("Now you are in a game");
        }

        if(menu.equals("profile")){
            App.getInstance().currentMenu = ProfileMenuView.getInstance();
            return Result.success("Now you are in profile menu");
        }
        return Result.success("You are not in a game");
    }

    private Result<Void> menuExit() {throw new UnsupportedOperationException("Not supported yet.");}

    public Result<String> showCurrentMenu(){
        return Result.success("main menu", "");
    }


    public Result<Void> logout() {
        User user = App.getInstance().logedInUser;
        user.setStayLoggedIn(false);
        DataBaseController.editUser(user);
        App.getInstance().logedInUser = null;
        App.getInstance().currentMenu = RegisterMenuView.getInstance();
        return Result.success(null);
    }
}
