package io.github.StardewValley.views.menu.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.StardewValley.App;
import io.github.StardewValley.Main;
import io.github.StardewValley.asset.Assets;
import io.github.StardewValley.controllers.GameController;

public class ReactionScreen implements Screen {
    private Stage stage;
    private Skin skin;
    private Texture bgTexture;
    private TextField messageField;
    private GameController gc;

    public ReactionScreen() {
        this.skin = Assets.getSkin();
        this.stage = new Stage(new ScreenViewport());
        this.gc = App.getInstance().currentPlayerController;
        createUI();
    }

    private void createUI() {
        // Set dark semi-transparent background
        setDarkBackground();

        // Create main table for layout
        Table mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.center();

        // Create message section
        Label messageLabel = new Label("Write a message:", skin);
        messageField = new TextField("", skin);
        TextButton sendMessageButton = new TextButton("Send Message", skin);
        sendMessageButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String message = messageField.getText();
                if (!message.isEmpty()) {
                    System.out.println("Message sent: " + message);
                    gc.setReaction(message);
                    Main.getInstance().setScreen(new GameScreen());
                }
            }
        });

        // Create reaction buttons
        TextButton happyButton = new TextButton(":HAPPY:", skin);
        happyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Happy reaction sent!");
                gc.setReaction("HAPPY");
                Main.getInstance().setScreen(new GameScreen());
            }
        });

        TextButton sadButton = new TextButton(":SAD:", skin);
        sadButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Sad reaction sent!");
                gc.setReaction("SAD");
                Main.getInstance().setScreen(new GameScreen());
            }
        });

        TextButton setDefaultButton = new TextButton("Set Default", skin);
        setDefaultButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                showDefaultReactionDialog();
            }
        });

        TextButton sendDefaultButton = new TextButton("Send Default", skin);
        sendDefaultButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gc.setReactionToDeafult();
                Main.getInstance().setScreen(new GameScreen());
            }
        });

        // Create back button
        TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getInstance().setScreen(new PauseMenu());
            }
        });

        // Add components to main table
        mainTable.add(messageLabel).padBottom(10).row();
        mainTable.add(messageField).width(400).padBottom(20).row();
        mainTable.add(sendMessageButton).padBottom(30).row();

        // Add reaction buttons in a horizontal group
        Table reactionTable = new Table();
        reactionTable.add(happyButton).padRight(20);
        reactionTable.add(sadButton).padLeft(20);
        mainTable.add(reactionTable).padBottom(30).row();

        // Add default reaction section
        mainTable.add(setDefaultButton).padBottom(10).row();
        mainTable.add(sendDefaultButton).padBottom(30).row();

        // Add back button
        mainTable.add(backButton);

        stage.addActor(mainTable);
        Gdx.input.setInputProcessor(stage);
    }

    private void showDefaultReactionDialog() {
        // Create dialog
        Dialog dialog = new Dialog("Set Default Reaction", skin);

        // Create content
        Table contentTable = new Table();
        final TextField reactionField = new TextField("", skin);
        contentTable.add(new Label("Enter default reaction:", skin)).padBottom(10).row();
        contentTable.add(reactionField).width(300).padBottom(20).row();

        // Add buttons
        TextButton confirmButton = new TextButton("Confirm", skin);
        confirmButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gc.setDefaultReaction(reactionField.getText());
                dialog.hide();
                createUI(); // Refresh UI to show new default
            }
        });

        TextButton cancelButton = new TextButton("Cancel", skin);
        cancelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dialog.hide();
            }
        });

        dialog.getContentTable().add(contentTable).pad(20);
        dialog.button(confirmButton);
        dialog.button(cancelButton);

        // Show dialog
        dialog.show(stage);
    }

    private void setDarkBackground() {
        Pixmap bgPixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        bgPixmap.setColor(0, 0, 0, 0.5f);
        bgPixmap.fill();
        bgTexture = new Texture(bgPixmap);
        bgPixmap.dispose();

        Image bgImage = new Image(bgTexture);
        bgImage.setSize(stage.getWidth(), stage.getHeight());
        stage.addActor(bgImage);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
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
        bgTexture.dispose();
    }
}