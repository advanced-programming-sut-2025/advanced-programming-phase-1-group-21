package io.github.StardewValley.views.menu.CLI;

import io.github.StardewValley.App;

import java.io.IOException;
import java.util.Scanner;

public class AppView {
    static Scanner scanner;

    public void resetStatic() {
        scanner = new Scanner(System.in);
    }

    public static String getUI() {
        return App.getInstance().getUI() + " > ";
    }

    public static String getResult(String prompt) {
        return App.getInstance().currentMenu.ResultText(prompt);

    }

    public void run() throws IOException {
        while (true){
            System.out.print(getUI());
            String command = scanner.nextLine().trim();
            if (command.equals("EXIT")) {
                return;
            }
            App.getInstance().currentMenu.Result(command);
        }

    }
}
