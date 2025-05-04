package models.game;

import models.App;
import models.DailyUpdate;
import models.map.Map;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import models.map.MapBuilder;
import models.map.Weather;
import models.time.*;

public class Game implements DailyUpdate {
    private ArrayList<Player> players;
    private Player currentPlayer;
    private Map village = (new MapBuilder()).buildVillage();
    private int roundCount = 0;
    private final Date gameDate;
    private Weather gameWeather;
    private Weather nextDayWeather;
    private final Random random = new Random();

    public Game(ArrayList<Player> players) {
        this.players = players;
        this.currentPlayer = players.getFirst();
        this.gameDate = Date.createBias();
        this.gameWeather = Weather.SUNNY;
        this.nextDayWeather = calculateRandomWeather();
    }

    private Weather calculateRandomWeather() {
        int rnd = random.nextInt(100);

        switch (getSeason()) {
            case SPRING:
                if (rnd < 60) return Weather.SUNNY;
                if (rnd < 90) return Weather.RAINY;
                return Weather.STORM;

            case SUMMER:
                if (rnd < 70) return Weather.SUNNY;
                if (rnd < 95) return Weather.RAINY;
                return Weather.STORM;

            case AUTUMN:
                if (rnd < 40) return Weather.SUNNY;
                if (rnd < 90) return Weather.RAINY;
                return Weather.STORM;

            case WINTER:
                if (rnd < 30) return Weather.SUNNY;
                if (rnd < 70) return Weather.RAINY;
                if (rnd < 95) return Weather.SNOW;
                return Weather.STORM;
            default:
                return Weather.SUNNY;
        }
    }

    public Weather getNextWeather() {
        return nextDayWeather;
    }

    public Weather getNextDayWeather() {
        return nextDayWeather;
    }

    public Map getCurrentPlayerMap() {
        return currentPlayer.getMap();
    }

    public void setNextDayWeather(Weather newWeather) {
        nextDayWeather = newWeather;
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
        for (int i = 0; i < hour; i++)
            advance();
        for (int i = 0; i < day; ++i) {
            nextDay();
        }
    }

    public Player getNextPlayer() {
        int currentIndex = players.indexOf(currentPlayer);
        int nextIndex = (currentIndex + 1) % players.size();
        return players.get(nextIndex);
    }

    private void endOfRound() {
        //TODO IF everyone should sleep
        if (gameDate.getHour() == 22) {
            nextDay();
            roundCount++;
        }
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
        gameWeather = nextDayWeather;
        nextDayWeather = calculateRandomWeather();

        for (Player player : players)
            player.nextDay();
        gameDate.goToNextDay();
        return false;
    }

    //this means game goes for 1 command
    public void advance() {
        gameDate.advanceHours(1);
    }
}