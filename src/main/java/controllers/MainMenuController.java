package controllers;

import models.App;
import models.Menu;
import models.result.Result;

public class MainMenuController{

    public Result<Void> enterMenu(String menu) {
        if(menu.equals("game")){
            App.currentMenu = Menu.Game;
            return Result.success("Now you are in a game");
        }

        if(menu.equals(Menu.ProfileMenu.toString())){
            App.currentMenu = Menu.ProfileMenu;
            return Result.success("Now you are in profile menu");
        }
        return Result.success("You are not in a game");
    }

    private Result<Void> menuExit() {throw new UnsupportedOperationException("Not supported yet.");}

    public Result<String> showCurrentMenu(){
        return Result.success("main menu", "");
    }


}
