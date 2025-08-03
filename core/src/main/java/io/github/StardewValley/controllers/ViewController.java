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
import models.animal.Animal;
import models.game.Game;
import models.game.Inventory;
import models.game.Player;
import models.map.*;
import models.result.Result;

import java.util.List;

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
//        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
//            thisGame.setScreen(new MainMenuScreen());
        if(gc.isFainted())
            return;
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
        if(Gdx.input.isKeyPressed(Input.Keys.E)) {
            if(player.getShepherdingAnimal() != null)
                gc.endShepherd(player.getShepherdingAnimal().getName());
        }
    }

    public Coord clickController(int x , int y){
        if(gc.isFainted())
            return new Coord(0,0);
        Map map = player.getMap();
        Tile tile = map.getTile((x - map.mapType.distanceX)/30 , (y - map.mapType.distanceY)/30);
        if(tile == null)
            return new Coord(-1 , -1);
        if(tile.getPlacable(Building.class) != null){
            Building building = tile.getPlacable(Building.class);
            if(checkCollision(building.sprite , player.getSprite()) && building.canEnter(game.getGameDate())) {
                gc.enterBuilding(building.getFullName());
            }
        }

        for(Direction direction : Direction.values()){
            int newX = player.getCoord().getX() + direction.getDx();
            int newY = player.getCoord().getY() + direction.getDy();

            if(newX == (x - map.mapType.distanceX)/30 && newY == (y - map.mapType.distanceY)/30)
                gc.useTool(direction);
        }


        {
//            System.out.println("Tile type: " + tile.getTileType());
            if (tile.getTileType() == TileType.PLOWED || tile.getTileType() == TileType.UNPLOWED){
                if (player.getItemInHand() != null && player.getItemInHand().getItemType() == ItemType.PLACEABLE) {
                    gc.placeArtisan(player.getItemInHand(), tile);
//                    System.out.println("You just Placed an Artisan, tile type: " + tile.getTileType());
                }
            }
            else if (tile.getTileType() == TileType.ARTISAN) {
                Artisan artisan = tile.getPlacable(Artisan.class);
                Inventory inventory = player.getInventory();

//                System.out.println("You Clicked on an artisan");
//                System.out.println("result: " + artisan.isResultReady() + ", empty: " + artisan.isEmpty());

                if (artisan.isResultReady() && inventory.canAdd(artisan.getResultWithoutReset().getName())) {
//                    System.out.println("You tried to collect the result");
                    Item result = artisan.getResultWithoutReset();
                    if (inventory.addItem(result).isSuccess()) {
                        artisan.getResult(inventory);
//                        System.out.println("You collected the result");
                    }
                }
                else if (artisan.isEmpty() && player.getItemInHand() != null) {
//                    artisan.findRecipeAndCraft(player.getItemInHand().getName(), player.getInventory());
                    gc.useArtisan(artisan, player.getItemInHand().getName());
//                    System.out.println("You just Placed an Item in an Artisan");
                }
            }
        }




        return new Coord((x - map.mapType.distanceX)/30 ,(y - map.mapType.distanceY)/30);

    }

    public boolean checkCollision(Sprite target, Sprite player) {
        Rectangle targetRect = target.getBoundingRectangle();
        Rectangle playerRect = player.getBoundingRectangle();
        return Intersector.overlaps(targetRect, playerRect);
    }
}
