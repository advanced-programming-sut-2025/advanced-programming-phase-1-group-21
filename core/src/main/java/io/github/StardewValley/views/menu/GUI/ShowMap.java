package io.github.StardewValley.views.menu.GUI;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import io.github.StardewValley.App;
import io.github.StardewValley.Main;
import models.map.Coord;
import models.map.Map;
import models.map.Tile;

public class ShowMap {
    public static void show(Main game){
        showTiles(game);
    }

    public static void showTiles(Main game){
        Map map = App.getInstance().game.getCurrentPlayerMap();
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
        
    }
}
