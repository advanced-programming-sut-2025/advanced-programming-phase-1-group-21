package io.github.StardewValley.views.menu.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.StardewValley.App;
import io.github.StardewValley.controllers.ChatController;
import models.game.Player;

public class TalkScreen {
    private Player player;
    private final Player otherPlayer;
    private Stage stage;
    private Skin skin;
    private GameScreen gameScreen;
    private Table messageTable;
    private ScrollPane scrollPane;
    private TextField inputField;
    private final ChatController chatController;

    public TalkScreen(Player me, Player otherPlayer , GameScreen gameScreen, Skin skin) {
        this.otherPlayer = otherPlayer;
        this.gameScreen = gameScreen;
        this.skin = skin;
        stage = new Stage(new ScreenViewport());
        chatController = new ChatController(App.getInstance().logedInUser.getUsername());
        createUI();
    }

    private void createUI() {
        Table table = new Table();
        table.setSize(800, 200);
        table.setPosition(Gdx.graphics.getWidth() - 800, Gdx.graphics.getHeight() / 2f - 100); // وسط راست
        table.align(Align.top);

        table.setColor(0.3f, 0.3f, 0.3f, 0.5f);
        table.setBackground(skin.newDrawable("white", table.getColor()));

        messageTable = new Table();
        messageTable.top().left();
        messageTable.defaults().left().padBottom(5);

        scrollPane = new ScrollPane(messageTable, skin);
        scrollPane.setFadeScrollBars(false);
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setForceScroll(false, true);

        inputField = new TextField("", skin);
        inputField.setMessageText("Message");
        inputField.getStyle().background = skin.newDrawable("white", new Color(0.2f, 0.2f, 0.2f, 0.8f));
        inputField.getStyle().fontColor = Color.WHITE;

        inputField.setTextFieldListener((textField, c) -> {
            if (c == '\r' || c == '\n') {
                String text = textField.getText();
                chatController.sendMessage(text , otherPlayer.getUser().getUsername());
                textField.setText("");
            }
        });

        table.add(scrollPane).expand().fill().pad(10).row();
        table.add(inputField).height(50).pad(10).fillX();

        stage.addActor(table);

        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ESCAPE) {
                    gameScreen.onTalkClosed();
                    return true;
                }
                return false;
            }
        });
    }

    public void pushMessage(String senderName, String message, Color color) {
        Label messageLabel = new Label(senderName + ": " + message, skin);
        messageLabel.setWrap(true);
        messageLabel.setColor(color);
        messageLabel.setAlignment(Align.left);

        messageTable.add(messageLabel).width(760).left().row();
        scrollPane.layout();
        scrollPane.setScrollPercentY(1f);

        gameScreen.showChat();
    }

    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    public void draw() {
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    public void dispose() {
        stage.dispose();
    }
}
