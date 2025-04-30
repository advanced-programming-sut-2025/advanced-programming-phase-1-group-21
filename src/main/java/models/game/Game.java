package models.game;

import java.util.List;

import models.time.*;

public class Game {
    private List<Player> players;
    private Player currentPlayer;
    private int roundCount = 0;
    private Date gameDate;
    private Season gameSeason;
    
    
    public Game(List<Player> players) {
        this.players = players;
        this.currentPlayer = players.get(0);
        this.gameDate = Date.createBias();
        this.gameSeason = Season.SPRING;
    }

    public Season getSeason() {
        return gameSeason;
    }

    public void advanceSeason() {
        gameSeason = gameSeason.nextSeason();
    }

    public Date getGameDate() {
        return gameDate;
    }

    public void advanceTime(int day, int hour) {
        gameDate.advance(day, hour);
    }

    public Player getNextPlayer() {
        int currentIndex = players.indexOf(currentPlayer);
        int nextIndex = (currentIndex + 1) % players.size();
        return players.get(nextIndex);
    }

    private void endOfRound() {

    }

    public void nextTurn() {
        Player nextPlayer = getNextPlayer();
        
        if (players.indexOf(nextPlayer) == 0) {
            roundCount++;
            endOfRound();
        }
        
        currentPlayer = nextPlayer;
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
