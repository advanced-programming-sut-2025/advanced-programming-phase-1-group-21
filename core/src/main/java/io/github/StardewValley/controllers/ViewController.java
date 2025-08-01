package io.github.StardewValley.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import io.github.StardewValley.Main;
import io.github.StardewValley.views.menu.GUI.MainMenuScreen;
import models.Item.Item;
import models.Item.ItemType;
import models.game.Game;
import models.game.Inventory;
import models.game.Player;
import models.map.*;

public class ViewController {

    private final GameController gc;
    public final Player player;
    public final Game game;

    public ViewController(GameController gameController) {
        this.gc = gameController;
        this.player = gc.getPlayer();
        this.game = gc.getGame();
    }


    public void buttonController(Main thisGame){
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
            thisGame.setScreen(new MainMenuScreen());
        if(Gdx.input.isKeyPressed(Input.Keys.W))
            gc.walk(Direction.NORTH);
        if(Gdx.input.isKeyPressed(Input.Keys.S))
            gc.walk(Direction.SOUTH);
        if(Gdx.input.isKeyPressed(Input.Keys.D))
            gc.walk(Direction.EAST);
        if(Gdx.input.isKeyPressed(Input.Keys.A))
            gc.walk(Direction.WEST);

        if(Gdx.input.isKeyPressed(Input.Keys.UP))
            gc.useTool(Direction.SOUTH);
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
            gc.useTool(Direction.NORTH);
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            gc.useTool(Direction.EAST);
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
            gc.useTool(Direction.WEST);
    }

    public String clickController(int x , int y){
        Player player = gc.getPlayer();
        Map map = player.getMap();
        Tile tile = map.getTile((x - map.mapType.distanceX)/30 , (y - map.mapType.distanceY)/30);
        if(tile == null)
            return "invalid tile";
        if(tile.getPlacable(Building.class) != null){
            Building building = tile.getPlacable(Building.class);
            if(checkCollision(building.sprite , player.getSprite()) && building.canEnter(game.getGameDate())){
                player.enterBuilding(building);
            }
        }
        if((player.getCoord().getX() - (x - map.mapType.distanceX)/30) > 1)
            return "tile is not neighbor";
        if((player.getCoord().getX() - (x - map.mapType.distanceX)/30) < -1)
            return "tile is not neighbor";
        if((player.getCoord().getY() - (y - map.mapType.distanceY)/30) > 1)
            return "tile is not neighbor";
        if((player.getCoord().getY() - (y - map.mapType.distanceY)/30) < -1)
            return "tile is not neighbor";

        if (tile.getTileType() == TileType.PLOWED || tile.getTileType() == TileType.UNPLOWED){
            if (player.getItemInHand() != null && player.getItemInHand().getItemType() == ItemType.PLACEABLE) {
                gc.placeArtisan(player.getItemInHand(), tile);
            }
        }
        if (tile.getTileType() == TileType.ARTISAN && tile.getPlacable(Artisan.class).isEmpty() && player.getItemInHand() != null) {
            tile.getPlacable(Artisan.class).findRecipeAndCraft(player.getItemInHand().getName(), player.getInventory());
        }

        if (tile.getTileType() == TileType.ARTISAN) {
            Artisan artisan = tile.getPlacable(Artisan.class);
            System.out.println("result: " + artisan.isResultReady() + ", empty: " + artisan.isEmpty());
            Inventory inventory = player.getInventory();
            if (artisan.isResultReady() && inventory.canAdd(artisan.getResultWithoutReset().getName())) {
                Item result = artisan.getResultWithoutReset();
                if (inventory.addItem(result).isSuccess()) {
                    artisan.getResult(inventory);
                }
            }
        }

        return null;

    }

    public boolean checkCollision(Sprite target, Sprite player) {
        Rectangle targetRect = target.getBoundingRectangle();
        Rectangle playerRect = player.getBoundingRectangle();
        return Intersector.overlaps(targetRect, playerRect);
    }
}
