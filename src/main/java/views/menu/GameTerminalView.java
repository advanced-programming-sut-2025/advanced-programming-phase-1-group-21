package views.menu;

import controllers.GameController;
import models.App;
import models.command.GameMenuCommand;
import models.game.Player;
import models.map.GreenHouse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameTerminalView {
    Matcher matcher;
    GameController gameController = new GameController();

    public static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    public static void printWithColor(ArrayList<String> output) {
        for(String line : output) {
            if(line.equals("#"))
                System.out.print(WHITE + line + WHITE);
            else if(line.equals("H"))
                System.out.print(YELLOW + line + YELLOW);
            else if(line.equals("~"))
                System.out.print(BLUE + line + BLUE);
            else if(line.equals("M"))
                System.out.print(BLACK + line + BLACK);
            else if(line.equals("R"))
                System.out.print(BLACK + line + BLACK);
            else if(line.equals("G"))
                System.out.print(GREEN + line + GREEN);
            else if(line.equals("*"))
                System.out.print(GREEN + line + GREEN);
            else if(line.equals("T"))
                System.out.print(GREEN + line + GREEN);
            else
                System.out.print(line);
        }
    }

    public static int getMap(Player player){
        System.out.print(player.getUser().getUsername() + " ,Select your map ID: ");
        return AppView.scanner.nextInt();
    }

    public void Result(String command) throws IOException {
        if((matcher = GameMenuCommand.PRINT_MAP.getMatcher(command)) != null) {
            printWithColor(gameController.printMap(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y")), Integer.parseInt(matcher.group("size"))));
        }

        else if((matcher = GameMenuCommand.SHOW_CURRENT_MENU.getMatcher(command)) != null) {
            System.out.println(gameController.showCurrentMenu().getMessage());
        }

        else if((matcher = GameMenuCommand.NEW_GAME.getMatcher(command)) != null) {
            System.out.println(gameController.createGame(matcher.group("username1") , matcher.group("username2") , matcher.group("username3")).getMessage());
        }

        else if((matcher = GameMenuCommand.NEXT_TURN.getMatcher(command)) != null) {
            System.out.println(gameController.nextTurn().getMessage());
        }

        else if((matcher = GameMenuCommand.EXIT_GAME.getMatcher(command)) != null) {
            System.out.println(gameController.exitGame().getMessage());
        }

        else
            System.out.println("Invalid command");
    }
}
