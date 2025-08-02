package io.github.StardewValley.views.menu.GUI;

import Asset.SharedAssetManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.github.czyzby.lml.parser.impl.attribute.progress.AnimateDurationLmlAttribute;
import io.github.StardewValley.App;
import io.github.StardewValley.Main;
import io.github.StardewValley.asset.Assets;
import io.github.StardewValley.controllers.GameController;
import io.github.StardewValley.controllers.ViewController;
import models.animal.Animal;
import models.map.Coord;
import models.game.Game;
import models.game.Player;
import models.map.Map;
import models.map.Tile;

public class GameScreen implements Screen , InputProcessor {
    private final Main game;
    private final GameController controller;
    private final ViewController viewController;
    private final Player currentPlayer;
    private final Game gameModel;
    private Stage stage;
    private InventoryTab inventoryTab;
    private ShopMenuTab shopMenuTab;
    private TerminalTab terminalTab;
    private PauseMenu pauseMenu;
    private ChatScreen chatScreen;
    private TagNotification tagNotification;
    private boolean isInventoryShown = false;
    private boolean isShopMenuShown = false;
    private boolean isTerminalShown = false;
    private boolean isChatShown = false;
    private BitmapFont messagePrinter = new BitmapFont();
    private String message;
    private AnimalInfoWindow animalInfoWindow;
    private Pixmap darkLayout;
    private Texture darkLayoutTexture;
    private Image darkModeImage;
    private boolean isNight;

    public GameScreen() {
        this.game = Main.getInstance();
        this.controller = App.getInstance().currentPlayerController;
        this.viewController = App.getInstance().currentPlayerViewController;
        this.gameModel = this.viewController.game;
        currentPlayer = this.viewController.player;
        ShowMap.currentPlayer = viewController.player;
        ShowMap.listOfPlayers = viewController.game.getPlayers();
        createUI();
    }

    private void createUI() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(this);

        darkLayout = new Pixmap((int) stage.getWidth(), (int) stage.getHeight(), Pixmap.Format.RGBA8888);
        darkLayout.setColor(0, 0, 0, 0.5f);
        darkLayout.fill();
        darkLayoutTexture = new Texture(darkLayout);
        darkLayout.dispose();
        darkModeImage = new Image(darkLayoutTexture);

        Skin skin = Assets.getSkin();
        inventoryTab = new InventoryTab(viewController.player, this, skin);
        shopMenuTab = new ShopMenuTab(this , skin);
        terminalTab = new TerminalTab(this , skin);
        chatScreen = new ChatScreen(viewController.player, this, skin);
        tagNotification = new TagNotification(skin);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {

//        System.out.println("Game Screen test render");
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.getBatch().begin();
        viewController.buttonController(game);
        ShowMap.show(delta);
        messagePrinter.setColor(Color.RED);
        if(message != null)
            messagePrinter.draw(game.getBatch(), message , 100 , 100);
        game.getBatch().end();

        if (gameModel.getGameDate().getHour() >= 22 || gameModel.getGameDate().getHour() <= 5)
            setDarkMode();
        else {
            if(isNight){
                isNight = false;
                stage.clear();
            }
        }
        stage.draw();

        if (isInventoryShown) {
            inventoryTab.draw();
        }
        if (isShopMenuShown)
            shopMenuTab.draw();
        if (isTerminalShown)
            terminalTab.draw();
        if (pauseMenu != null)
            pauseMenu.draw();
        if (isChatShown) {
            chatScreen.draw();
        }
        if (tagNotification.isVisible()) {
            tagNotification.draw();
        }
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
        if (chatScreen != null) {
            chatScreen.dispose();
        }
    }

    @Override
    public boolean keyDown(int i) {
        if (i == Input.Keys.I && !isInventoryShown && !isChatShown) {
            isInventoryShown = true;
            inventoryTab.show();
        }
        if (i == Input.Keys.Y && !isChatShown) {
            showChat();
        }
        if (i == Input.Keys.M && !isShopMenuShown && !isChatShown) {
            isShopMenuShown = true;
            shopMenuTab.show();

        }
        if (i == Input.Keys.T && !isTerminalShown && !isChatShown) {
            isTerminalShown = true;
            terminalTab.show();
        }
        if (i == Input.Keys.P && pauseMenu == null) {
            pauseMenu = new PauseMenu(this , Assets.getSkin());
            pauseMenu.show();
        }

        if (i == Input.Keys.F1) {
            tagNotification.show("You Pressed F1");
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
        Coord c = viewController.clickController(screenX, screenY);
        message = "(" + c.getX() + ","
                + c.getY() + ")";
        Tile tile = controller.getPlayer().getMap().getTile(c);
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

    public void onChatClosed() {
        isChatShown = false;
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

    public void onPauseClosed(){
        pauseMenu = null;
        Gdx.input.setInputProcessor(this);
    }

    private void setDarkMode(){
        stage.addActor(darkModeImage);
        isNight = true;
    }

    public void showChat() {
        isChatShown = true;
        chatScreen.show();
    }

    public ChatScreen getChatScreen() {
        return chatScreen;
    }

    public void showNotification(String text) {
        tagNotification.show(text);
    }
}
