package controllers;

import models.game.Item;
import models.game.Player;
import models.game.Trade;
import models.result.Result;
import models.user.User;

import java.util.ArrayList;

public class TradeController{

    public Result<Trade> trade(Player player, String offerItemName, int offerAmount, int requestedPrice) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> trade(Player player, String offerItemName, int offerAmount, String requesterItemName, int requesterAmount) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<ArrayList<Trade>> tradeList() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<Void> tradeResponse(Player player, int tradeID, boolean answer) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Result<ArrayList<Trade>> tradeHistory() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
