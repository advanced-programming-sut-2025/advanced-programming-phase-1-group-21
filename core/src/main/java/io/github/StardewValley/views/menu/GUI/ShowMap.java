package io.github.StardewValley.views.menu.GUI;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import io.github.StardewValley.App;
import io.github.StardewValley.Main;
import models.game.Player;
import models.game.Refrigerator;
import models.map.*;
import models.tool.Tool;

public class ShowMap {
    public static Player player;

    public static void show(Main game , float delta){
        showTiles(game);
        showHouse(game);
        onTilesShow(game);
        showPlayer(game);
        showTool(game , delta);
    }

    public static void showTiles(Main game){
        Map map = player.getMap();
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

    public static void onTilesShow(Main game){
        Map map = player.getMap();
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

    public static void showPlayer(Main game){
        player.getSprite().draw(game.getBatch());
    }

    public static void showHouse(Main game){//TODO: this function should be changed to showBuilding
        Map map = player.getMap();
        for(Building building : map.getBuildings()){
            if(building.sprite != null)
                building.sprite.draw(game.getBatch());
        }
    }

    public static void showTool(Main game , float delta){
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
