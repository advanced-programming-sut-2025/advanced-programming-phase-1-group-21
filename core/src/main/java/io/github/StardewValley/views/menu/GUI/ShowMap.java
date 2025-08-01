package io.github.StardewValley.views.menu.GUI;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import io.github.StardewValley.App;
import io.github.StardewValley.Main;
import models.game.Player;
import models.game.Refrigerator;
import models.map.*;
import models.tool.Tool;

import java.util.List;

public class ShowMap {
    public static Player currentPlayer;
    public static List<Player> listOfPlayers;
    public static Map map;
    private static Main game = Main.getInstance();

    public static void show(float delta){
        map = currentPlayer.getMap();
        showTiles();
        showHouse();
        onTilesShow();
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
                if(tile.getOnTileSprite() != null) {
                    tile.getOnTileSprite().draw(game.getBatch());
                }
            }
        }
    }

    public static void showPlayer(Player player){
        if (player.getMap() == map)
            player.getSprite().draw(game.getBatch());
    }

    public static void showHouse(){//TODO: this function should be changed to showBuilding
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
}
