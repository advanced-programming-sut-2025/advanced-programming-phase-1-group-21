package io.github.StardewValley.views.menu.GUI;

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
import io.github.StardewValley.App;
import io.github.StardewValley.Main;
import io.github.StardewValley.asset.Assets;
import io.github.StardewValley.controllers.GameController;
import io.github.StardewValley.controllers.ViewController;
import models.animal.Animal;
import models.game.NPC;
import models.map.Coord;
import models.game.Game;
import models.game.Player;
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
    private SellTab sellTab;
    private GiftTab giftTab;
    private TerminalTab terminalTab;
    private ChatScreen chatScreen;
    private TalkScreen talkScreen;
    private TagNotification tagNotification;
    private FriendshipsTab friendshipsTab;
    private boolean isInventoryShown = false;
    private boolean isShopMenuShown = false;
    private boolean isSellTabShown = false;
    private boolean isTerminalShown = false;
    private boolean isFriendshipShown = false;
    private boolean isChatShown = false;
    private boolean isTalkScreen = false;
    private boolean isGiftTabShown = false;
    private BitmapFont messagePrinter = new BitmapFont();
    private String message;
    private AnimalInfoWindow animalInfoWindow;
    private OtherPlayerInfo otherPlayerInfo;
    private NPCInformationWindow NPCInformation;
    private NPCResponseWindow NPCAnswer;
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
        ShowMap.listOfNPCs = viewController.game.getNpcs();
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
        sellTab = new SellTab(this , skin , viewController.player);
        giftTab = new GiftTab(this , skin);
        terminalTab = new TerminalTab(this , skin);
        friendshipsTab = new FriendshipsTab(this , skin);
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
        if(isSellTabShown)
            sellTab.draw();
        if (isTerminalShown)
            terminalTab.draw();
        if (isChatShown) {
            chatScreen.draw();
        }
        if(isTalkScreen)
            talkScreen.draw();

        if(isFriendshipShown)
            friendshipsTab.draw();
        if (tagNotification.isVisible()) {
            tagNotification.draw();
        }
        if(isGiftTabShown)
            giftTab.draw();

        if(!viewController.player.getNotifications().isEmpty()){
            int size = viewController.player.getNotifications().size();
            showNotification(viewController.player.getNotifications().get(size - 1));
            viewController.player.getNotifications().remove(size - 1);
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

        if(i == Input.Keys.B){
            isSellTabShown = true;
            sellTab.show();
        }
        if(i == Input.Keys.G){
            isGiftTabShown = true;
            giftTab.show();
        }
        if (i == Input.Keys.T && !isTerminalShown && !isChatShown) {
            isTerminalShown = true;
            terminalTab.show();
        }
        if (i == Input.Keys.P) {
            game.setScreen(new PauseMenu());
        }
        if (i == Input.Keys.F && !isTerminalShown && !isChatShown) {
            isFriendshipShown = true;
            friendshipsTab.show();
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
                + c.getY() + "), ";
        Tile tile = currentPlayer.getMap().getTile(c);
        if(tile != null && tile.getPlacable(Animal.class) != null){
            Animal animal = tile.getPlacable(Animal.class);
            animalInfoWindow = new AnimalInfoWindow(this , Assets.getSkin() , animal);
            animalInfoWindow.setPosition(screenX + 30, Gdx.graphics.getHeight() - screenY + 30);
            Gdx.input.setInputProcessor(stage);
            stage.addActor(animalInfoWindow);
        }

        if(tile != null && tile.getPlacable(NPC.class) != null){
            NPC npc = tile.getPlacable(NPC.class);
            NPCInformation = new NPCInformationWindow(this , Assets.getSkin() , npc , currentPlayer);
            NPCInformation.setPosition(screenX + 30, Gdx.graphics.getHeight() - screenY + 30);
            Gdx.input.setInputProcessor(stage);
            stage.addActor(NPCInformation);
        }

        Player player = viewController.getOtherPlayerClick(c);
        if(player != null){
            otherPlayerInfo = new OtherPlayerInfo(this , Assets.getSkin() , player , currentPlayer);
            otherPlayerInfo.setPosition(screenX + 30, Gdx.graphics.getHeight() - screenY + 30);
            Gdx.input.setInputProcessor(stage);
            stage.addActor(otherPlayerInfo);
        }

        for(NPC npc : ShowMap.listOfNPCs){
            if(npc.getCloudSprite() != null && viewController.clickOnSprite(npc.getCloudSprite() , screenX , screenY)) {
                NPCAnswer = new NPCResponseWindow(this , Assets.getSkin() , npc.getResponseToMessage() , screenX ,
                        Gdx.graphics.getHeight() - screenY);
                NPCAnswer.setPosition(screenX, Gdx.graphics.getHeight() - screenY);
                Gdx.input.setInputProcessor(stage);
                stage.addActor(NPCAnswer);
                controller.resetNPCResponse(npc.getName());
            }
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

    public void onTalkClosed(){
        isTalkScreen = false;
        talkScreen = null;
        Gdx.input.setInputProcessor(this);
    }

    public void onShopMenuClosed() {
        isShopMenuShown = false;
        Gdx.input.setInputProcessor(this);
    }

    public void onSellTabClosed(){
        isSellTabShown = false;
        Gdx.input.setInputProcessor(this);
    }

    public void onGiftMenuClosed(){
        isGiftTabShown = false;
        Gdx.input.setInputProcessor(this);
    }

    public void onTerminalClosed(){
        isTerminalShown = false;
        Gdx.input.setInputProcessor(this);
    }

    public void onFriendshipScreenClosed(){
        isFriendshipShown = false;
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

    public void closePlayerInfo(){
        if(otherPlayerInfo != null){
            otherPlayerInfo = null;
        }
        stage.clear();
        Gdx.input.setInputProcessor(this);
    }

    public void closeNPCAnswer(){
        if(NPCAnswer != null){
            NPCAnswer = null;
        }
        stage.clear();
        Gdx.input.setInputProcessor(this);
    }

    public void closeNPCInfo(){
        if(NPCInformation != null){
            NPCInformation = null;
        }
        stage.clear();
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

    public void sendMessageInChat(String sender, String message, Color color) {
        if(talkScreen != null){
            talkScreen.pushMessage(sender , message , color);
            return;
        }
        chatScreen.pushMessage(sender, message, color);
    }

    public void showNotification(String text) {
        tagNotification.show(text);
    }

    public void setTalkScreen(TalkScreen talkScreen) {
        this.talkScreen = talkScreen;
        if(talkScreen != null){
            isTalkScreen = true;
            talkScreen.show();
        }
    }
}
