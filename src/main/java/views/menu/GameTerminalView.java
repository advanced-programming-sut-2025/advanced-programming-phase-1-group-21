package views.menu;

import controllers.GameController;
import models.command.GameMenuCommand;
import models.map.GreenHouse;

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

    public void printWithColor(ArrayList<String> output) {
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

    public void Result(String command) {
        if((matcher = GameMenuCommand.PRINT_MAP.getMatcher(command)) != null) {
            printWithColor(gameController.printMap(Integer.parseInt(matcher.group("x")), Integer.parseInt(matcher.group("y")), Integer.parseInt(matcher.group("size"))));
        }

        else
            System.out.println("Invalid command");
    }
}
