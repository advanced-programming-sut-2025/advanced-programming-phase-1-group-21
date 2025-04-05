package models.game;

import java.util.List;

public class Game {
    private List<Player> players;
    private Player currentPlayer;

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
