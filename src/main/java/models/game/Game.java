package models.game;

import models.App;
import models.DailyUpdate;
import models.Item.Item;
import models.animal.Animal;
import models.data.DataLoader;
import models.map.Map;

import java.util.ArrayList;
import java.util.Random;

import models.map.MapBuilder;
import models.map.Weather;
import models.time.*;

public class Game implements DailyUpdate {
    private ArrayList<Player> players;
    private ArrayList<NPC> npcs = new ArrayList<>();
    private ArrayList<Relation> relations = new ArrayList<>();
    private Player currentPlayer;
    public final Map village;
    private int roundCount = 0;
    private Date gameDate;
    private Weather gameWeather;
    private Weather nextDayWeather;

    public Game(ArrayList<Player> players) {
        this.players = players;
        this.currentPlayer = players.getFirst();
        this.gameDate = Date.createBias();
        this.gameWeather = Weather.SUNNY;
        this.nextDayWeather = calculateRandomWeather();

        for(int i = 0 ; i < players.size() ; i++) {
            for(int j = i+1 ; j < players.size() ; j++) {
                this.relations.add(new Relation(players.get(i), players.get(j)));
            }
        }

        addNPC();
        village = (new MapBuilder()).buildVillage(npcs);
    }

    public Relation getRelationOfUs(Player player1 , Player player2) {
        for(Relation relation : relations) {
            if(player1.equals(relation.getPlayer1()) && player2.equals(relation.getPlayer2())) {
                return relation;
            }
            else if(player1.equals(relation.getPlayer2()) && player2.equals(relation.getPlayer1())) {
                return relation;
            }
        }
        return null;
    }

    public void addNPC(){
        NPC sebastian = new NPC("Sebastian" , players);
        NPC abigail = new NPC("Abigail" , players);
        NPC harvey = new NPC("Harvey" ,players);
        NPC leah = new NPC("Leah" , players);
        NPC robin = new NPC("Robin" , players);
        npcs.add(sebastian);
        npcs.add(abigail);
        npcs.add(harvey);
        npcs.add(leah);
        npcs.add(robin);
    }

    public ArrayList<Relation> getMyRelations(Player player) {
        ArrayList<Relation> output = new ArrayList<>();
        for(Relation relation : relations) {
            if(player.equals(relation.getPlayer1()) || player.equals(relation.getPlayer2())) {
                output.add(relation);
            }
        }

        return output;
    }

    public ArrayList<Relation> getRelations() {
        return relations;
    }


    private Weather calculateRandomWeather() {
        int rnd = App.random.nextInt(100);

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
        return Item.build("Coin", 0);
    }

    public Map getVillage() {
        return village;
    }

    public Player getPlayerByName(String username){
        for(Player player : players) {
            if(player.getUser().getUsername().equals(username))
                return player;
        }
        return null;
    }



    public ArrayList<NPC> getNpcs() {
        return npcs;
    }

    @Override
    public boolean nextDay() {
        gameWeather = nextDayWeather;
        nextDayWeather = calculateRandomWeather();

        for(Relation relation : relations) {
            relation.resetTodayCommunication();
        }

        for(NPC npc : npcs) {
            for(NPCFriendship friendship : npc.getFriendships()) {
                friendship.setTodayMeet(false);
                friendship.setTodayGift(false);
            }
        }

        for (Player player : players)
            player.nextDay();
        gameDate.goToNextDay();
        return false;
    }

    //this means game goes for 1 command
    public void advance() {
        gameDate.advanceHours(1);
    }

    public NPC getNPCByName(String name) {
        for(NPC npc : npcs) {
            if(npc.getName().equals(name))
                return npc;
        }
        return null;
    }

    public double weatherCofficient(){
        if(gameWeather.equals(Weather.RAINY) || gameWeather.equals(Weather.STORM))
            return 1.5;
        if(gameWeather.equals(Weather.SNOW))
            return 2;
        return 1;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Game{");
        sb.append("players=[");
        for (Player p : players) {
            sb.append(p.getUser().getUsername()).append(",");
        }
        if (!players.isEmpty()) {
            sb.setLength(sb.length() - 1);  // remove trailing comma
        }
        sb.append("]");
        sb.append(", gameDate=").append(gameDate);
        sb.append(", gameWeather=").append(gameWeather);
        sb.append(", nextDayWeather=").append(nextDayWeather);
        sb.append("}");
        return sb.toString();
    }

}