package io.github.StardewValley.views.menu.GUI;

import Network.Message;
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
import com.github.czyzby.lml.parser.impl.attribute.progress.AnimateDurationLmlAttribute;
import io.github.StardewValley.App;
import io.github.StardewValley.Main;
import io.github.StardewValley.asset.Assets;
import io.github.StardewValley.controllers.GameController;
import io.github.StardewValley.controllers.ViewController;
import models.map.Map;
import models.map.MapBuilder;
import models.map.Tile;
import models.network.HeartBeatPacket;
import models.tool.Tool;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class GameScreen implements Screen , InputProcessor {
    private final Main game;
    private final GameController controller;
    private final ViewController viewController;
    private Stage stage;
    private InventoryTab inventoryTab;
    private ShopMenuTab shopMenuTab;
    private TerminalTab terminalTab;
    private boolean isInventoryShown = false;
    private boolean isShopMenuShown = false;
    private boolean isTerminalShown = false;
    private BitmapFont messagePrinter = new BitmapFont();
    private String message;
    private AnimalInfoWindow animalInfoWindow;

    public GameScreen() {
        this.game = Main.getInstance();
        this.controller = App.getInstance().currentPlayerController;
        this.viewController = App.getInstance().currentPlayerViewController;
        ShowMap.player = viewController.player;
        createUI();
    }

    private void createUI() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(this);

        Skin skin = Assets.getSkin();
        inventoryTab = new InventoryTab(viewController.player, this, skin);
        shopMenuTab = new ShopMenuTab(this , skin);
        terminalTab = new TerminalTab(this , skin);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.getBatch().begin();
        viewController.buttonController(game);
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
        if(isTerminalShown)
            terminalTab.draw();
        stage.draw();
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
        if (i == Input.Keys.T && !isInventoryShown) {
            isTerminalShown = true;
            terminalTab.show();

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
        message = "(" + viewController.clickController(screenX, screenY).getX() + ","
                + viewController.clickController(screenX , screenY).getY() + ")";
        Tile tile = controller.getPlayer().getMap().getTile(viewController.clickController(screenX , screenY));
        if(tile != null && tile.getPlacable(Animal.class) != null){
            Animal animal = tile.getPlacable(Animal.class);
            animalInfoWindow = new AnimalInfoWindow(this , Assets.getSkin() , animal);
            animalInfoWindow.setPosition(screenX + 30, Gdx.graphics.getHeight() - screenY + 30);
            Gdx.input.setInputProcessor(stage);
            stage.addActor(animalInfoWindow);
        }
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
        controller.handleToolMove(screenX, screenY);
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

    public void onTerminalClosed(){
        isTerminalShown = false;
        Gdx.input.setInputProcessor(this);
    }

    public GameController getController() {
        return controller;
    }

    public void closeAnimalInfo(){
        if(animalInfoWindow != null){
            animalInfoWindow = null;
        }
        stage.clear();
        Gdx.input.setInputProcessor(this);
    }
}
