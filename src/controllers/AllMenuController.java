package controllers;

import models.App;
import models.Menu;

public class AllMenuController {
    RegisterMenuController registerMenuController = new RegisterMenuController();
    LoginMenuController loginMenuController = new LoginMenuController();
    ProfileMenuController profileMenuController = new ProfileMenuController();
    MainMenuController mainMenuController = new MainMenuController();

    public void controlMenus(String command){
        if(App.currentMenu.equals(Menu.RegisterMenu))
            registerMenuController.start();

        else if(App.currentMenu.equals(Menu.LoginMenu))
            loginMenuController.start();

        else if(App.currentMenu.equals(Menu.ProfileMenu))
            profileMenuController.start();

        else if(App.currentMenu.equals(Menu.MainMenu))
            mainMenuController.start();
    }
}
