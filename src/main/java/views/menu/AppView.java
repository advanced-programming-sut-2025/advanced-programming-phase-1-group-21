package views.menu;

import models.App;
import models.Menu;

import java.io.IOException;
import java.util.Scanner;

public class AppView {
    Scanner scanner = new Scanner(System.in);
    GameTerminalView gameTerminalView = new GameTerminalView();
    LoginMenuView loginMenuView = new LoginMenuView();
    MainMenuView mainMenuView = new MainMenuView();
    ProfileMenuView profileMenuView = new ProfileMenuView();
    RegisterMenuView registerMenuView = new RegisterMenuView();

    public void run() throws IOException {
        while (App.play){
            String command = scanner.nextLine();
            if(App.currentMenu.equals(Menu.RegisterMenu))
                registerMenuView.Result(command);
            else if(App.currentMenu.equals(Menu.LoginMenu))
                loginMenuView.Result(command);
            else if(App.currentMenu.equals(Menu.ProfileMenu))
                profileMenuView.Result(command);
            else if(App.currentMenu.equals(Menu.Game))
                gameTerminalView.Result(command);
            else if(App.currentMenu.equals(Menu.MainMenu))
                mainMenuView.Result(command);
        }

    }
}
