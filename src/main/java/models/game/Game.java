package models.game;

import models.App;
import models.DailyUpdate;
import models.map.Map;

import java.util.ArrayList;
import java.util.List;

import models.map.MapBuilder;
import models.map.Weather;
import models.time.*;

public class Game implements DailyUpdate {
    private ArrayList<Player> players;
    private Player currentPlayer;
    private Map village = (new MapBuilder()).buildVillage();
    private int roundCount = 0;
    private Date gameDate;
    private Weather gameWeather;
    private Weather forecastCheat;


    public Game(ArrayList<Player> players) {
        this.players = players;
        this.currentPlayer = players.get(0);
        this.gameDate = Date.createBias();
        this.gameWeather = Weather.SUNNY;
        this.forecastCheat = null;
    }

    //Calculate randomly based on sth?
    public Weather getNextWeather() {
        if (forecastCheat != null) {
            return forecastCheat;
        }
        int random = (int) (Math.random() * 100);

        switch (getSeason()) {
            case SPRING:
                if (random < 60) return Weather.SUNNY;
                if (random < 90) return Weather.RAINY;
                return Weather.STORM;

            case SUMMER:
                if (random < 70) return Weather.SUNNY;
                if (random < 95) return Weather.RAINY;
                return Weather.STORM;

            case AUTUMN:
                if (random < 40) return Weather.SUNNY;
                if (random < 90) return Weather.RAINY;
                return Weather.STORM;

            case WINTER:
                if (random < 30) return Weather.SUNNY;
                if (random < 70) return Weather.RAINY;
                if (random < 95) return Weather.SNOW;
                return Weather.STORM;
            default:
                return Weather.SUNNY;
        }
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
        return gameDate.getCurrentSeason();
    }

    public Date getGameDate() {
        return gameDate;
    }

    public void advanceTime(int day, int hour) {
        gameDate.advanceHours(hour);
        for (int i = 0; i < day; ++i) {
            advanceDay();
        }
    }

    public Player getNextPlayer() {
        int currentIndex = players.indexOf(currentPlayer);
        int nextIndex = (currentIndex + 1) % players.size();
        return players.get(nextIndex);
    }

    public void advanceDay() {
        gameWeather = getNextWeather();
        forecastCheat = null;
    }

    private void endOfRound() {
        //TODO IF everyone should sleep
        if (gameDate.getHour() == 22) {
            nextDay();
            roundCount++;
        }
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

        if (players.indexOf(nextPlayer) == 0)
            endOfRound();

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

    @Override
    public boolean nextDay() {
        for (Player player : players)
            player.nextDay();
        gameDate.goToNextDay();
        return false;
    }
}
