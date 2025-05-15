package models.game;

import models.DailyUpdate;

import java.util.ArrayList;

public class Relation implements DailyUpdate {
    private Player player1, player2;
    private FriendshipLevel Level = FriendshipLevel.LEVEL0;
    private ArrayList<String> talks = new ArrayList<>();
    private int friendshipXP = 0;
    private ArrayList<Trade> trades = new ArrayList<>();
    private boolean todayTalk = false;
    private boolean todayGift = false;
    private boolean todayHug = false;
    private boolean todayTrade = false;
    private ArrayList<Gift> gifts = new ArrayList<>();
    private boolean isFlower = false;

    public Relation(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    public String printRelation(Player player) {
        if(!player1.equals(player))
            return player1.getUser().getUsername() + " : " + "level=" + getLevel().toString() + "   " + "xp=" + getFriendshipXP();
        return player2.getUser().getUsername() + " : " + "level=" + getLevel().toString() + "   " + "xp=" + getFriendshipXP();
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
        this.gifts.add(gift);
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
        checkOverFlow();
    }

    public void addTrade(Trade trade) {
        trades.add(trade);
    }

    public boolean isFlower() {
        return isFlower;
    }

    public void setFlower(boolean flower) {
        isFlower = flower;
    }

    public void checkOverFlow(){
        if(friendshipXP < 0){
            if(Level.equals(FriendshipLevel.LEVEL0))
                friendshipXP = 0;
            levelDown();
            friendshipXP = (Level.getLevel() + 1)*100 + friendshipXP;
        }

        if(Level.getLevel() == 2){
            if(friendshipXP >= (Level.getLevel() + 1)*100){
                if(isFlower) {
                    friendshipXP = 0;
                    levelUp();
                }
                else
                    friendshipXP = (Level.getLevel() + 1)*100;
            }
        }
        else if(Level.getLevel() == 3){
            if(friendshipXP >= (Level.getLevel() + 1)*100)
                friendshipXP = (Level.getLevel() + 1)*100;
        }
        else if(friendshipXP >= (Level.getLevel() + 1)*100) {
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

    public void levelDown(){
        if(Level.equals(FriendshipLevel.LEVEL1))
            Level = FriendshipLevel.LEVEL0;
        else if(Level.equals(FriendshipLevel.LEVEL2))
            Level = FriendshipLevel.LEVEL1;
        else if(Level.equals(FriendshipLevel.LEVEL3))
            Level = FriendshipLevel.LEVEL2;
        else if(Level.equals(FriendshipLevel.LEVEL4))
            Level = FriendshipLevel.LEVEL3;
    }

    public boolean isTodayTalk() {
        return todayTalk;
    }

    public boolean isTodayGift() {
        return todayGift;
    }

    public boolean isTodayHug() {
        return todayHug;
    }

    public boolean isTodayTrade() {
        return todayTrade;
    }

    public void setTodayTalk(boolean todayTalk) {
        this.todayTalk = todayTalk;
    }

    public void setTodayGift(boolean todayGift) {
        this.todayGift = todayGift;
    }

    public void setTodayHug(boolean todayHug) {
        this.todayHug = todayHug;
    }

    public void setTodayTrade(boolean todayTrade) {
        this.todayTrade = todayTrade;
    }

    public void resetTodayCommunication(){
        todayGift = false;
        todayHug = false;
        todayTrade = false;
        todayTalk = false;
    }

    @Override
    public String toString() {
        return String.format(
                "Relation{player1=%s, player2=%s, level=%s, xp=%d, talks=%d, trades=%d, gifts=%d, todayTalk=%b, todayGift=%b, todayHug=%b, todayTrade=%b, isFlower=%b}",
                player1.getUser().getUsername(),
                player2.getUser().getUsername(),
                Level,
                friendshipXP,
                talks.size(),
                trades.size(),
                gifts.size(),
                todayTalk,
                todayGift,
                todayHug,
                todayTrade,
                isFlower
        );
    }

    @Override
    public boolean nextDay(Game game) {
        resetTodayCommunication();
        return true;
    }
}
