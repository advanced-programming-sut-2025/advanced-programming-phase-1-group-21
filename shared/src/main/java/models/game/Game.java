package models.game;

import models.Item.Item;
import models.map.Map;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import models.map.MapBuilder;
import models.map.Weather;
import models.time.Date;
import models.time.Season;

public class Game implements Serializable {
    private ArrayList<Player> players;
    private ArrayList<NPC> npcs = new ArrayList<>();
    private ArrayList<Relation> relations = new ArrayList<>();
    public Map village;
    private int roundCount = 0;
    private Date gameDate;
    private Weather gameWeather;
    private Weather nextDayWeather;
    public Random rand = new Random(1);

    public Game() {}

    public Game(ArrayList<Player> players) {
        this.players = players;
        this.gameDate = Date.createBias();
        this.gameWeather = Weather.SUNNY;
        this.nextDayWeather = calculateRandomWeather();

        for(int i = 0 ; i < players.size() ; i++) {
            for(int j = i+1 ; j < players.size() ; j++) {
                this.relations.add(new Relation(players.get(i), players.get(j)));
            }
        }

        addNPC();
        village = (new MapBuilder()).buildVillage(npcs, new Random(42));
        for(NPC npc : npcs)
            npc.setNpcMap(village);
    }

    /**
     * copy constructor, shallow copy of all fields except weather fields are duplicated.
     */
    public Game(Game other) {
        this.players = other.players;
        this.npcs = other.npcs;
        this.relations = other.relations;
        this.village = other.village;
        this.roundCount = other.roundCount;
        this.gameDate = new Date(other.gameDate);
        this.gameWeather = other.gameWeather;
        this.nextDayWeather = other.nextDayWeather;
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
        int rnd = rand.nextInt(100);

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

    private void endOfRound() {
        //TODO IF everyone should sleep
        if (gameDate.getHour() == 22) {
            nextDay();
            roundCount++;
        }
        else {
            advance();
        }
        for (Player player : players) {
            player.resetEnergy();
        }
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }


    public void endgame() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static Item getCoinItem(int amount) {
        return Item.build("Coin", amount);
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

    public boolean nextDay() {
        gameWeather = nextDayWeather;
        nextDayWeather = calculateRandomWeather();

        Game copy = new Game(this);

        for(Relation relation : relations) {
            relation.nextDay(copy);
        }

        for(NPC npc : npcs) {
            npc.nextDay(copy);
        }

        for (Player player : players) {
            //copy.setCurrentPlayer(player);
            player.nextDay(copy);
        }
        gameDate.nextDay(copy);

        village.nextDay(copy);

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

    public double weatherCoefficient(){
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

    /**
     * changes current weather! (it's needed in the code and shouldn't be reached from user
     * @param newWeather
     */
    public void setWeather(Weather newWeather) {
        gameWeather = newWeather;
    }
}
