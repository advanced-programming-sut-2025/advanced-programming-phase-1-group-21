package models.game;

import models.map.Building;
import models.map.Weather;

import java.util.ArrayList;

public class NPC {
    private String name, job;
    // Characterization
    private Building house;
    private ArrayList<Dialogue> dialogues;
    private ArrayList<NPCFriendship> friendships;

    private ArrayList<Item> favorites;
    private ArrayList<Pair<Item, Item>> quests;

}
