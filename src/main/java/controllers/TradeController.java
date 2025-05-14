package controllers;

import models.App;
import models.Item.Item;
import models.Menu;
import models.game.Player;
import models.game.Relation;
import models.game.Trade;
import models.game.TradeType;
import models.result.Result;
import models.result.errorTypes.GameError;

import java.util.ArrayList;

public class TradeController{

    public Result<Void> backToGame(){
        App.currentMenu = Menu.Game;
        return Result.success(null);
    }

    public Result<Void> tradeWithMoney(String username , String type , String item , int amount , int price) {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        if(App.game.getCurrentPlayer().getUser().getUsername().equals(username))
            return Result.failure(GameError.NO_PLAYER_FOUND);

        Player player = App.game.getPlayerByName(username);
        if(player == null) return Result.failure(GameError.NO_PLAYER_FOUND);

        Item thisItem = Item.build(item , amount);
        if(type.equals("offer") && !App.game.getCurrentPlayer().getInventory().canRemoveItem(thisItem))
            return Result.failure(GameError.NOT_ENOUGH_ITEMS);
        Relation relation = App.game.getRelationOfUs(App.game.getCurrentPlayer(), player);
        if(type.equals("offer")) {
            relation.addTrade(new Trade(relation.getTrades().size(), App.game.getCurrentPlayer(), player, 0 ,
                    thisItem , price , null , TradeType.OFFER_MONEY));
        }
        else{
            relation.addTrade(new Trade(relation.getTrades().size(), App.game.getCurrentPlayer(), player, price ,
                    null , 0 , thisItem , TradeType.REQUEST_MONEY));
        }

        return Result.success(null);
    }

    public Result<Void> tradeWithItem(String username , String type , String item1 , int amount1 , String item2 , int amount2) {
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        if(App.game.getCurrentPlayer().getUser().getUsername().equals(username))
            return Result.failure(GameError.NO_PLAYER_FOUND);

        Player player = App.game.getPlayerByName(username);
        if(player == null) return Result.failure(GameError.NO_PLAYER_FOUND);

        Item firstItem = Item.build(item1 , amount1);
        Item secondItem = Item.build(item2 , amount2);
        if(type.equals("offer") && !App.game.getCurrentPlayer().getInventory().canRemoveItem(firstItem))
            return Result.failure(GameError.NOT_ENOUGH_ITEMS);
        if(type.equals("request") && !App.game.getCurrentPlayer().getInventory().canRemoveItem(secondItem))
            return Result.failure(GameError.NOT_ENOUGH_ITEMS);

        Relation relation = App.game.getRelationOfUs(App.game.getCurrentPlayer(), player);
        if(type.equals("offer")) {
            relation.addTrade(new Trade(relation.getTrades().size(), App.game.getCurrentPlayer(), player, 0 ,
                    firstItem , 0 , secondItem , TradeType.OFFER_ITEM));
        }
        else{
            relation.addTrade(new Trade(relation.getTrades().size(), App.game.getCurrentPlayer(), player, 0 ,
                    secondItem , 0 , firstItem , TradeType.REQUEST_ITEM));
        }

        return Result.success(null);
    }

    public Result<ArrayList<String>> tradeList(){
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> tradeResponse(String username , String response , int ID){
        if (App.game == null) return Result.failure(GameError.NO_GAME_RUNNING);

        if(App.game.getCurrentPlayer().getUser().getUsername().equals(username))
            return Result.failure(GameError.NO_PLAYER_FOUND);

        Player player = App.game.getPlayerByName(username);
        if(player == null) return Result.failure(GameError.NO_PLAYER_FOUND);

        if((!response.equals("accept") && !response.equals("reject")))
            return Result.failure(GameError.RESPONSE_IS_NOT_SUPPORTED);

        Relation relation = App.game.getRelationOfUs(App.game.getCurrentPlayer(), player);
        if(ID >= relation.getTrades().size())
            return Result.failure(GameError.TRADE_ID_DOES_NOT_EXIST);

        Trade trade = relation.getTrades().get(ID);

        if(response.equals("reject")){
            trade.setResponse(false);
            trade.setResponsed(true);
            return Result.success(null);
        }
        if(trade.getTradeType().equals(TradeType.OFFER_ITEM)){
            if(!player.getInventory().canRemoveItem(trade.getOfferItem()) || !App.game.getCurrentPlayer().getInventory()
                    .canRemoveItem(trade.getRequestItem())){
                trade.setResponse(false);
                trade.setResponsed(true);
                return Result.failure(GameError.NOT_ENOUGH_ITEMS);
            }
            player.getInventory().removeItem(trade.getOfferItem());
            App.game.getCurrentPlayer().getInventory().addItem(trade.getOfferItem());
            App.game.getCurrentPlayer().getInventory().removeItem(trade.getRequestItem());
            player.getInventory().addItem(trade.getRequestItem());
            trade.setResponse(true);
            trade.setResponsed(true);
        }

        if(trade.getTradeType().equals(TradeType.REQUEST_ITEM)){
            if(!player.getInventory().canRemoveItem(trade.getRequestItem()) || !App.game.getCurrentPlayer().getInventory()
                    .canRemoveItem(trade.getOfferItem())){
                trade.setResponse(false);
                trade.setResponsed(true);
                return Result.failure(GameError.NOT_ENOUGH_ITEMS);
            }
            player.getInventory().removeItem(trade.getRequestItem());
            App.game.getCurrentPlayer().getInventory().addItem(trade.getRequestItem());
            App.game.getCurrentPlayer().getInventory().removeItem(trade.getOfferItem());
            player.getInventory().addItem(trade.getOfferItem());
            trade.setResponse(true);
            trade.setResponsed(true);
        }

        if(trade.getTradeType().equals(TradeType.OFFER_MONEY)){
            if(!player.getInventory().canRemoveItem(trade.getOfferItem()) || (App.game.getCurrentPlayer().getInventory()
                    .getCoin().getAmount() < trade.getRequestPrice())){
                trade.setResponse(true);
                trade.setResponsed(true);
                return Result.failure(GameError.NOT_ENOUGH_ITEMS);
            }
            System.out.println(trade.getOfferItem().getAmount());
            player.getInventory().removeItem(trade.getOfferItem());
            System.out.println(trade.getOfferItem().getAmount());
            App.game.getCurrentPlayer().getInventory().addItem(trade.getOfferItem());
            App.game.getCurrentPlayer().getInventory().changeCoin(-trade.getRequestPrice());
            player.getInventory().changeCoin(trade.getRequestPrice());
            trade.setResponse(true);
            trade.setResponsed(true);
        }

        if(trade.getTradeType().equals(TradeType.REQUEST_MONEY)){
            if(!App.game.getCurrentPlayer().getInventory().canRemoveItem(trade.getOfferItem()) || (player.getInventory()
                    .getCoin().getAmount() < trade.getRequestPrice())){
                trade.setResponse(true);
                trade.setResponsed(true);
                return Result.failure(GameError.NOT_ENOUGH_ITEMS);
            }
            player.getInventory().changeCoin(-trade.getOfferPrice());
            App.game.getCurrentPlayer().getInventory().changeCoin(trade.getOfferPrice());
            App.game.getCurrentPlayer().getInventory().removeItem(trade.getRequestItem());
            player.getInventory().addItem(trade.getRequestItem());
            trade.setResponse(true);
            trade.setResponsed(true);
        }
        relation.setFriendshipXP(relation.getFriendshipXP() + 50);
        return Result.success(null);
    }

    public Result<ArrayList<String>> tradeHistory(){
        ArrayList<String> output = new ArrayList<>();
        for(Player player : App.game.getPlayers()){
            if(player.equals(App.game.getCurrentPlayer()))
                continue;
            Relation relation = App.game.getRelationOfUs(App.game.getCurrentPlayer(), player);
            for(Trade trade : relation.getTrades()){
                if(trade.isResponsed())
                    continue;
                output.add("Sender : " + trade.getSender().getUser().getUsername());
                output.add("Receiver : " + trade.getReceiver().getUser().getUsername());
                output.add("ID : " + trade.getID());
                if(TradeType.OFFER_ITEM.equals(trade.getTradeType())) {
                    output.add("Offered Item : " + trade.getOfferItem().getName());
                    output.add("Requested Item : " + trade.getRequestItem().getName());
                }
                if(TradeType.REQUEST_ITEM.equals(trade.getTradeType())) {
                    output.add("Requested Item : " + trade.getRequestItem().getName());
                    output.add("Offered Item : " + trade.getOfferItem().getName());
                }
                if(TradeType.OFFER_MONEY.equals(trade.getTradeType())) {
                    output.add("Offered Item : " + trade.getOfferItem().getName());
                    output.add("Requested Price : " + trade.getRequestPrice());
                }
                if(TradeType.REQUEST_MONEY.equals(trade.getTradeType())) {
                    output.add("Requested Item : " + trade.getRequestItem().getName());
                    output.add("Offered Price : " + trade.getOfferPrice());
                }
                output.add("----------");
            }
        }
        return Result.success(output);
    }

}
