package models.game;

import java.util.ArrayList;

public class Relation {
    private Player player1, player2;
    private FriendshipLevel Level = FriendshipLevel.LEVEL0;
    private ArrayList<String> talks = new ArrayList<>();
    private int friendshipXP = 0;
    private ArrayList<Trade> trades = new ArrayList<>();
    private boolean todayCommunication = false;
    private ArrayList<Gift> gifts = new ArrayList<>();

    public Relation(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    public String printRelation(Player player) {
        if(!player1.equals(player))
            return player1.getUser().getUsername() + " : " + "level-" + getLevel().toString() + "   " + "xp-" + getFriendshipXP();
        return player2.getUser().getUsername() + " : " + "level-" + getLevel().toString() + "   " + "xp-" + getFriendshipXP();
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public FriendshipLevel getLevel() {
        return Level;
    }

    public ArrayList<String> getTalks() {
        return talks;
    }

    public int getFriendshipXP() {
        return friendshipXP;
    }

    public ArrayList<Trade> getTrades() {
        return trades;
    }

    public ArrayList<Gift> getGifts() {
        return gifts;
    }

    public void addGift(Gift gift) {

    }

    public void addFlower() {

    }

    public boolean isTodayCommunication() {
        return todayCommunication;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public void setLevel(FriendshipLevel level) {
        Level = level;
    }

    public void addTalk(String talk) {
        this.talks.add(talk);
    }

    public void setFriendshipXP(int friendshipXP) {
        this.friendshipXP = friendshipXP;
    }

    public void setTrades(ArrayList<Trade> trades) {
        this.trades = trades;
    }

    public void setGifts(ArrayList<Gift> gifts) {
        this.gifts = gifts;
    }

    public void setTodayCommunication(boolean todayCommunication) {
        this.todayCommunication = todayCommunication;
    }

    public void checkOverFlow(){
        if(Level.getLevel() > 2)
            return;
        if(friendshipXP > (Level.getLevel() + 1)*100) {
            friendshipXP -= (Level.getLevel() + 1)*100;
            levelUp();
        }
    }

    public void levelUp(){
        if(Level.equals(FriendshipLevel.LEVEL0))
            Level = FriendshipLevel.LEVEL1;
        else if(Level.equals(FriendshipLevel.LEVEL1))
            Level = FriendshipLevel.LEVEL2;
        else if(Level.equals(FriendshipLevel.LEVEL2))
            Level = FriendshipLevel.LEVEL3;
        else if(Level.equals(FriendshipLevel.LEVEL3))
            Level = FriendshipLevel.LEVEL4;
    }
}
