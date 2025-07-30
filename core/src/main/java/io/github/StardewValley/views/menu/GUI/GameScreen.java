package io.github.StardewValley.views.menu.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.StardewValley.App;
import io.github.StardewValley.Main;
import io.github.StardewValley.asset.Assets;
import io.github.StardewValley.controllers.GameController;
import models.game.Game;
import models.game.Player;
import models.map.Coord;
import models.map.Map;
import models.map.MapBuilder;
import models.map.Tile;
import models.tool.Tool;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class GameScreen implements Screen , InputProcessor {
    private final Main game;
    private final GameController controller;
    private Stage stage;
    private InventoryTab inventoryTab;
    private ShopMenuTab shopMenuTab;
    private boolean isInventoryShown = false;
    private boolean isShopMenuShown = false;
    private BitmapFont messagePrinter = new BitmapFont();
    private String message;

    public GameScreen() {
        Map map = new MapBuilder().buildFarm(new Random(1));
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player(App.getInstance().logedInUser, map));
        App.getInstance().game = new Game(players);
        this.game = Main.getInstance();
        this.controller = new GameController();
        controller.game = App.getInstance().game;
        createUI();
    }

    private void createUI() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(this);

        Map map = App.getInstance().game.getCurrentPlayerMap();
        float mapX = map.getMaxX();
        float mapY = map.getMaxY();
        float tileY = stage.getHeight()/ mapY;
        float tileX = stage.getWidth()/ mapX;

        Skin skin = Assets.getSkin();
        inventoryTab = new InventoryTab(this, skin);
        shopMenuTab = new ShopMenuTab(this , skin);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.getBatch().begin();
        controller.buttonController(game);
        ShowMap.show(game , delta);
        messagePrinter.setColor(Color.RED);
        if(message != null)
            messagePrinter.draw(game.getBatch(), message , 100 , 100);
        game.getBatch().end();
        if (isInventoryShown) {
            inventoryTab.draw();
        }
        if(isShopMenuShown)
            shopMenuTab.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        if (inventoryTab != null) {
            inventoryTab.dispose();
        }
    }

    @Override
    public boolean keyDown(int i) {
        if (i == Input.Keys.I && !isInventoryShown) {
            isInventoryShown = true;
            inventoryTab.show();
        }
        if (i == Input.Keys.M && !isInventoryShown) {
            isShopMenuShown = true;
            shopMenuTab.show();

        }
        return false;
    }

    @Override
    public boolean keyUp(int i) {
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        message = controller.clickController(screenX, screenY);
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        if(App.getInstance().game.getCurrentPlayer().getItemInHand() instanceof Tool){
            Tool tool = (Tool) App.getInstance().game.getCurrentPlayer().getItemInHand();
            tool.handleRotation(screenX , Gdx.graphics.getHeight() - screenY);
        }
        return false;
    }

    @Override
    public boolean scrolled(float v, float v1) {
        return false;
    }

    public void onInventoryClosed() {
        isInventoryShown = false;
        Gdx.input.setInputProcessor(this);
    }

    public void onShopMenuClosed() {
        isShopMenuShown = false;
        Gdx.input.setInputProcessor(this);
    }

    public GameController getController() {
        return controller;
    }
}
