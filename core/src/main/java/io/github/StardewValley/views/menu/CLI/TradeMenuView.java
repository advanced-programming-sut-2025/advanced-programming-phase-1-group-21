package io.github.StardewValley.views.menu.CLI;

import io.github.StardewValley.controllers.TradeController;
import io.github.StardewValley.models.command.GameMenuCommand;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class TradeMenuView implements Menu {
    Matcher matcher;
    TradeController tradeController = new TradeController();
    private static TradeMenuView instance;

    private TradeMenuView() {
    }

    public static TradeMenuView getInstance() {
        if (instance == null)
            instance = new TradeMenuView();
        return instance;
    }

    public static void printArrayList(ArrayList<String> output) {
        for(String line : output) {
            System.out.println(line);
        }
    }

    public void Result(String command){
        if((matcher = GameMenuCommand.TRADE_WITH_MONEY.getMatcher(command)) != null) {
            System.out.println(tradeController.tradeWithMoney(matcher.group("username") , matcher.group("type")
                    , matcher.group("item") , Integer.parseInt(matcher.group("amount")) , Integer.parseInt
                            (matcher.group("price"))));
        }

        else if((matcher = GameMenuCommand.TRADE_WITH_ITEM.getMatcher(command)) != null) {
            System.out.println(tradeController.tradeWithItem(matcher.group("username") , matcher.group("type")
                    , matcher.group("item1") , Integer.parseInt(matcher.group("amount1")) ,
                    matcher.group("item2") , Integer.parseInt(matcher.group("amount2"))));
        }

        else if((matcher = GameMenuCommand.TRADE_RESPONSE.getMatcher(command)) != null) {
            System.out.println(tradeController.tradeResponse(matcher.group("username") , matcher.group("response") , Integer.parseInt(matcher.group("ID"))));
        }

        else if((matcher = GameMenuCommand.TRADE_HISTORY.getMatcher(command)) != null) {
            printArrayList(tradeController.tradeHistory().getData());
        }

        else if((matcher = GameMenuCommand.BACK_TO_THE_GAME.getMatcher(command)) != null) {
            System.out.println(tradeController.backToGame());
        }

        else{
            System.out.println("Invalid command");
        }

    }
}
