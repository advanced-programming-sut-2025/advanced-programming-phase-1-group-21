package controllers;

import models.App;
import models.Menu;
import models.result.Result;
import models.user.User;

import java.io.IOException;

public class MainMenuController{

    public Result<Void> enterMenu(String menu) {
        if(menu.equals("game")){
            App.getInstance().currentMenu = Menu.Game;
            return Result.success("Now you are in a game");
        }

        if(menu.equals("profile")){
            App.getInstance().currentMenu = Menu.ProfileMenu;
            return Result.success("Now you are in profile menu");
        }
        return Result.success("You are not in a game");
    }

    private Result<Void> menuExit() {throw new UnsupportedOperationException("Not supported yet.");}

    public Result<String> showCurrentMenu(){
        return Result.success("main menu", "");
    }


    public Result<Void> logout() throws IOException {
        User user = App.getInstance().logedInUser;
        user.setStayLoggedIn(false);
        DataBaseController.editUser(user);
        App.getInstance().logedInUser = null;
        App.getInstance().currentMenu = Menu.RegisterMenu;
        return Result.success(null);
    }
}
