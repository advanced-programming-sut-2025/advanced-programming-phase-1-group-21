package io.github.StardewValley.views.menu.CLI;

import io.github.StardewValley.models.App;

import java.io.IOException;
import java.util.Scanner;

public class AppView {
    static Scanner scanner;
    public GameTerminalView gameTerminalView = GameTerminalView.getInstance();
    public LoginMenuView loginMenuView = LoginMenuView.getInstance();
    public MainMenuView mainMenuView = MainMenuView.getInstance();
    public ProfileMenuView profileMenuView = ProfileMenuView.getInstance();
    public RegisterMenuView registerMenuView = RegisterMenuView.getInstance();
    public TradeMenuView tradeMenuView = TradeMenuView.getInstance();

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
            App.getInstance().currentMenu.Result(command);
//            if(App.getInstance().currentMenu.equals(Menu.RegisterMenu))
//                registerMenuView.Result(command);
//            else if(App.getInstance().currentMenu.equals(Menu.LoginMenu))
//                loginMenuView.Result(command);
//            else if(App.getInstance().currentMenu.equals(Menu.ProfileMenu))
//                profileMenuView.Result(command);
//            else if(App.getInstance().currentMenu.equals(Menu.Game))
//                gameTerminalView.Result(command);
//            else if(App.getInstance().currentMenu.equals(Menu.MainMenu))
//                mainMenuView.Result(command);
//            else if(App.getInstance().currentMenu.equals(Menu.TradeMenu))
//                tradeMenuView.Result(command);

        }

    }
}
