package io.github.StardewValley.views.menu.GUI;

import io.github.StardewValley.Animations.IndependentAnimation;
import io.github.StardewValley.Main;
import models.animal.Animal;
import models.game.NPC;
import models.game.Player;
import models.map.*;
import models.tool.Tool;

import java.util.ArrayList;
import java.util.List;

public class ShowMap {
    public static Player currentPlayer;
    public static List<Player> listOfPlayers;
    public static List<NPC> listOfNPCs;
    public static Map map;
    private static Main game = Main.getInstance();
    private static final ArrayList<IndependentAnimation> animations = new ArrayList<>();

    public static void show(float delta){
        map = currentPlayer.getMap();
        showTiles();
        showHouse();
        onTilesShow();
        animalAnimations();
        showAnimations();
        showNPCReactions();
        for (Player player : listOfPlayers) {
            if (player.getMap() == map) {
                showPlayer(player);
                showTool(delta, player);
            }
        }
    }

    public static void showTiles(){
        //Map map = player.getMap();
        float mapX = map.getMaxX();
        float mapY = map.getMaxY();
        for(int i = 0 ; i < mapY ; i++){
            for(int j = 0 ; j < mapX ; j++){
                Tile tile = map.getTile(j , (int)mapY - i -1);
                if(tile.spriteGetter() != null) {
                    tile.spriteGetter().draw(game.getBatch());
                }
            }
        }
    }

    public static void onTilesShow(){
        //Map map = player.getMap();
        float mapX = map.getMaxX();
        float mapY = map.getMaxY();

        for(int i = (int)mapY - 1 ; i >= 0 ; i--){
            for(int j = 0 ; j < mapX ; j++){
                Tile tile = map.getTile(j , (int)mapY - i -1);
                Placable placable = tile.getPlacable(Placable.class);
                if(placable != null && placable.spriteGetter() != null)
                    placable.spriteGetter().draw(game.getBatch());
                if(tile.getOnTileSprite() != null) {
                    tile.getOnTileSprite().draw(game.getBatch());
                }
                if(tile.getLightningSprite() != null){
                    tile.getLightningSprite().draw(game.getBatch());
                    tile.runLightning();
                }
            }
        }
    }

    public static void animalAnimations(){
        float mapX = map.getMaxX();
        float mapY = map.getMaxY();

        for(int i = (int)mapY - 1 ; i >= 0 ; i--){
            for(int j = 0 ; j < mapX ; j++){
                Tile tile = map.getTile(j , (int)mapY - i -1);
                if(tile.getPlacable(Animal.class) != null){
                    Animal animal = tile.getPlacable(Animal.class);
                    if(animal.getAnimalReactSprite() != null) {
                        animal.getAnimalReactSprite().draw(game.getBatch());
                        animal.runPetAnimation();
                    }
                }
            }
        }
    }

    public static void showPlayer(Player player){
        if (player.getMap() == map) {
            player.getSprite().draw(game.getBatch());
            if(player.getShepherdingAnimal() != null)
                player.getShepherdingAnimal().spriteGetter().draw(game.getBatch());
        }
    }

    public static void showHouse(){
        //Map map = player.getMap();
        for(Building building : map.getBuildings()){
            if(building.sprite != null)
                building.sprite.draw(game.getBatch());
        }
    }

    public static void showTool(float delta, Player player) {
        if(player.getItemInHand() instanceof Tool){
            Tool tool = (Tool) player.getItemInHand();
            tool.spriteX = player.getSprite().getX();
            tool.spriteY = player.getSprite().getY();
            tool.loadTexture();
            tool.getSprite().draw(game.getBatch());
            if(tool.getAnimationTime() > 0)
                tool.animation(delta);
        }
    }

    public static void showNPCReactions(){
        for(NPC npc : listOfNPCs){
            if(npc.getMap().equals(map) && npc.getCloudSprite() != null) {
                npc.getCloudSprite().draw(game.getBatch());
            }
            if(npc.getReactSprite() != null){
                npc.getReactSprite().draw(game.getBatch());
                npc.runAnimation();
            }
        }
    }

    private static void showAnimations() {
        for (var itr = animations.iterator(); itr.hasNext();) {
            IndependentAnimation animation = itr.next();
            if (animation.draw(game.getBatch()))
                itr.remove();
        }
    }

    public static void addAnimation(IndependentAnimation animation) {
        animations.add(animation);
    }
}
