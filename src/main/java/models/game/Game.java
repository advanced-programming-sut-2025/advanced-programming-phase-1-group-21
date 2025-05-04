package models.game;

import models.App;
import models.map.Map;

import java.util.ArrayList;
import java.util.List;

import models.map.MapBuilder;
import models.map.Weather;
import models.time.*;

public class Game {
    private ArrayList<Player> players;
    private Player currentPlayer;
    private Map village = (new MapBuilder()).buildVillage();
    private int roundCount = 0;
    private Date gameDate;
    private Season gameSeason;
    private Weather gameWeather;
    private Weather forecastCheat;


    public Game(ArrayList<Player> players) {
        this.players = players;
        this.currentPlayer = players.get(0);
        this.gameDate = Date.createBias();
        this.gameSeason = Season.SPRING;
        this.gameWeather = Weather.SUNNY;
        this.forecastCheat = null;
    }

    //Calculate randomly based on sth?
    public Weather getNextWeather() {
        if (forecastCheat != null) {
            return forecastCheat;
        }
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Map getCurrentPlayerMap() {
        return currentPlayer.getMap();
    }

    public void setForecastWeather(Weather newWeather) {
        forecastCheat = newWeather;
    }

    public Weather getWeather() {
        return gameWeather;
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

    public void advanceDay() {
        gameWeather = getNextWeather();
        forecastCheat = null;
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void endOfRound() {

    }
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

    public static Item getCoinItem(int amount) {
        return new Item("coin", ItemType.COIN, 1, amount);
    }

    public Map getVillage() {
        return village;
    }
}
