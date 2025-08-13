package io.github.StardewValley.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import io.github.StardewValley.Animations.FertilizerAnimation;
import io.github.StardewValley.Animations.HarvestAnimation;
import io.github.StardewValley.Main;
import io.github.StardewValley.views.menu.GUI.GameScreen;
import io.github.StardewValley.views.menu.GUI.ShowMap;
import models.Item.Item;
import models.Item.ItemType;
import models.crop.PlantedSeed;
import models.crop.PlantedTree;
import models.game.Game;
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


    public void buttonController(Main thisGame) {
//        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
//            thisGame.setScreen(new MainMenuScreen());
        if (player.isFainted())
            return;
        if (Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.D))
            gc.walk(Direction.NORTH_EAST);
        else if (Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.D))
            gc.walk(Direction.SOUTH_EAST);
        else if (Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.A))
            gc.walk(Direction.SOUTH_WEST);
        else if (Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.A))
            gc.walk(Direction.NORTH_WEST);
        else if (Gdx.input.isKeyPressed(Input.Keys.W))
            gc.walk(Direction.NORTH);
        else if (Gdx.input.isKeyPressed(Input.Keys.D))
            gc.walk(Direction.EAST);
        else if (Gdx.input.isKeyPressed(Input.Keys.S))
            gc.walk(Direction.SOUTH);
        else if (Gdx.input.isKeyPressed(Input.Keys.A))
            gc.walk(Direction.WEST);


        if (Gdx.input.isKeyPressed(Input.Keys.UP))
            gc.useTool(Direction.SOUTH);
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            gc.useTool(Direction.NORTH);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            gc.useTool(Direction.EAST);
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            gc.useTool(Direction.WEST);
        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            if (player.getShepherdingAnimal() != null)
                gc.endShepherd(player.getShepherdingAnimal().getName());
        }
    }

    public Coord clickController(int x , int y) {
        if (player.isFainted())
            return new Coord(0,0);

        Map map = player.getMap();
        Coord coord = new Coord((x - map.mapType.distanceX) / 30, (y - map.mapType.distanceY) / 30);
        Tile tile = map.getTile(coord);
        if (tile == null)
            return new Coord(-1 , -1);

        if(player.getBuildingToBuy() != null){
            gc.purchaseBuilding(player.getBuildingToBuy(), coord);
            player.setBuildingToBuy(null);
            return coord;
        }

        if (tile.getPlacable(Building.class) != null) {
            Building building = tile.getPlacable(Building.class);
            if (checkCollision(building.sprite , player.getSprite()) && building.canEnter(game.getGameDate())) {
                gc.enterBuilding(building.getFullName());
            }
        }

        for (Direction direction : Direction.values()) {
            int newX = player.getCoord().getX() + direction.getDx();
            int newY = player.getCoord().getY() + direction.getDy();

            if (newX == (x - map.mapType.distanceX)/30 && newY == (y - map.mapType.distanceY)/30)
                gc.useTool(direction);
        }


        {
            Item itemInHand = player.getItemInHand();
            if (tile.getTileType() == TileType.PLOWED || tile.getTileType() == TileType.UNPLOWED) {
                if (itemInHand != null) {
                    if (itemInHand.getItemType() == ItemType.PLACEABLE)
                        gc.placeArtisan(coord, itemInHand.getName());
                    else if (itemInHand.getItemType() == ItemType.SEED)
                        gc.plant(coord, itemInHand.getName());
                    else if (itemInHand.getItemType() == ItemType.SAPLING)
                        gc.plant(coord, itemInHand.getName());
                }
            }
            else if (tile.getTileType() == TileType.ARTISAN) {
                Artisan artisan = tile.getPlacable(Artisan.class);
                ((GameScreen) Main.getInstance().getScreen()).showArtisanTab(artisan, x, y);
            }
            else if (tile.getTileType() == TileType.REFRIGERATOR) {
                ((GameScreen) Main.getInstance().getScreen()).showRefrigeratorTab(coord);
            }
            else if (tile.getTileType() == TileType.PLANTED_SEED) {
                if (tile.getPlacable(PlantedSeed.class).isHarvestReady()) {
                    ShowMap.addAnimation(new HarvestAnimation().show(player, tile.getPlacable(PlantedSeed.class).getResultName(), x, y));
                    gc.harvest(coord);
                }
                else if (itemInHand != null && itemInHand.getName().contains("Soil")) {
                    gc.fertilize(coord, itemInHand.getName());
                    ShowMap.addAnimation(new FertilizerAnimation().show(itemInHand.getName(), x, y));
                }
            }
            else if (tile.getTileType() == TileType.PLANTED_TREE) {
                if (tile.getPlacable(PlantedTree.class).isHarvestReady()) {
                    ShowMap.addAnimation(new HarvestAnimation().show(player, tile.getPlacable(PlantedTree.class).getResultName(), x, y));
                    gc.harvest(coord);
                }
            }
            else if (tile.getTileType() == TileType.FORAGING_CROP) {
                ForagingCrop crop = tile.getPlacable(ForagingCrop.class);
                if (player.getInventory().canAdd(crop.getResultName())) {
                    player.getInventory().addItem(Item.build(crop.getResultName(), 1));
                    ShowMap.addAnimation(new HarvestAnimation().show(player, crop.getResultName(), x, y));
                    tile.resetTile();
                }
            }
        }

        return coord;

    }

    public boolean clickOnSprite(Sprite sprite , int x , int y) {
        return sprite.getBoundingRectangle().contains(x, Gdx.graphics.getHeight() - y);
    }

    public Player getOtherPlayerClick(Coord coord) {
        for (Player player1 : gc.getGame().getPlayers()) {
            if (player1.getMap().equals(player.getMap()) && player1.getCoord().equals(coord) && !player1.equals(player))
                return player1;
        }
        return null;
    }

    public boolean checkCollision(Sprite target, Sprite player) {
        Rectangle targetRect = target.getBoundingRectangle();
        Rectangle playerRect = player.getBoundingRectangle();
        return Intersector.overlaps(targetRect, playerRect);
    }
}
