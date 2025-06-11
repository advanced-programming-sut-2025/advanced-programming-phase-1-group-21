package io.github.StardewValley.views.menu;

import io.github.StardewValley.models.App;
import io.github.StardewValley.models.Menu;

import java.io.IOException;
import java.util.Scanner;

public class AppView {
    static Scanner scanner;
    GameTerminalView gameTerminalView = new GameTerminalView();
    LoginMenuView loginMenuView = new LoginMenuView();
    MainMenuView mainMenuView = new MainMenuView();
    ProfileMenuView profileMenuView = new ProfileMenuView();
    RegisterMenuView registerMenuView = new RegisterMenuView();
    TradeMenuView tradeMenuView = new TradeMenuView();

    public void resetStatic() {
        scanner = new Scanner(System.in);
    }

    public void run() throws IOException {
        while (true){
            System.out.print(App.getInstance().getUI() + " > ");
            String command = scanner.nextLine().trim();
            if (command.equals("EXIT")) {
                return;
            }
            if(App.getInstance().currentMenu.equals(Menu.RegisterMenu))
                registerMenuView.Result(command);
            else if(App.getInstance().currentMenu.equals(Menu.LoginMenu))
                loginMenuView.Result(command);
            else if(App.getInstance().currentMenu.equals(Menu.ProfileMenu))
                profileMenuView.Result(command);
            else if(App.getInstance().currentMenu.equals(Menu.Game))
                gameTerminalView.Result(command);
            else if(App.getInstance().currentMenu.equals(Menu.MainMenu))
                mainMenuView.Result(command);
            else if(App.getInstance().currentMenu.equals(Menu.TradeMenu))
                tradeMenuView.Result(command);

        }

    }
}
