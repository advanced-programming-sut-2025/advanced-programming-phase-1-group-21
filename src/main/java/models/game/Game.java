package models.game;

import models.map.Map;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private ArrayList<Player> players;
    private Player currentPlayer;
    private ArrayList<Map> maps;

    public Game(Player currentPlayer, ArrayList<Player> players) {
        this.currentPlayer = currentPlayer;
        this.players = players;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void nextTurn() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void endGame() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void startGame() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }
}
