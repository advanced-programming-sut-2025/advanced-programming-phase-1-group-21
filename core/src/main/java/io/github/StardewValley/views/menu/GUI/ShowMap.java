package io.github.StardewValley.views.menu.GUI;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import io.github.StardewValley.App;
import io.github.StardewValley.Main;
import models.map.*;

public class ShowMap {
    public static void show(Main game){
        showTiles(game);
        showHouse(game);
        showPlayer(game);
    }

    public static void showTiles(Main game){
        Map map = App.getInstance().game.getCurrentPlayer().getMap();
        float mapX = map.getMaxX();
        float mapY = map.getMaxY();
        for(int i = 0 ; i < mapY ; i++){
            for(int j = 0 ; j < mapX ; j++){
                Tile tile = map.getTile(j , (int)mapY - i -1);
                tile.spriteGetter().draw(game.getBatch());
            }
        }
    }

    public static void showPlayer(Main game){
        App.getInstance().game.getCurrentPlayer().getSprite().draw(game.getBatch());
    }

    public static void showHouse(Main game){//TODO: this function should be changed to showBuilding
        Map map = App.getInstance().game.getCurrentPlayer().getMap();
        for(Building building : map.getBuildings()){
            if(building.sprite != null)
                building.sprite.draw(game.getBatch());
        }
    }
}
