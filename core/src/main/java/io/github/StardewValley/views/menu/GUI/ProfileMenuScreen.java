package io.github.StardewValley.views.menu.GUI;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.StardewValley.App;
import io.github.StardewValley.Main;
import io.github.StardewValley.asset.Assets;
import io.github.StardewValley.controllers.ProfileMenuController;
import io.github.StardewValley.network.NetworkDataBaseController;
import models.result.Result;

public class ProfileMenuScreen implements Screen {

    private final Main game;
    private final Stage stage;
    private final ProfileMenuController controller;

    // UI Widgets
    private TextField newUsernameField;
    private TextField newNicknameField;
    private TextField newPasswordField;
    private TextField newEmailField;
    private TextField oldPasswordField;

    private Label messageLabel;
    private Label usernameLabel;
    private Label nicknameLabel;
    private Label emailLabel;
    private Label maxCoinLabel;
    private Label gamesLabel;

    public ProfileMenuScreen() {
        NetworkDataBaseController.updateUser();
        this.game = Main.getInstance();
        this.controller = new ProfileMenuController();
        stage = new Stage(new ScreenViewport());
        createUI();
        setupInputProcessor();
    }

    private void createUI() {
        Skin skin = Assets.getSkin();
        Texture backgroundTexture = Assets.getMenuBackground();

        // Background
        Image backgroundImage = new Image(new TextureRegionDrawable(new TextureRegion(backgroundTexture)));
        backgroundImage.setFillParent(true);
        stage.addActor(backgroundImage);

        // Main layout table
        Table table = new Table();
        table.setFillParent(true);
        table.center();
        stage.addActor(table);

        // --- User Information Labels ---
        usernameLabel = new Label("Username: " + App.getInstance().logedInUser.getUsername(), skin);
        nicknameLabel = new Label("Nickname: " + App.getInstance().logedInUser.getNickname(), skin);
        emailLabel = new Label("Email: " + App.getInstance().logedInUser.getEmail(), skin);
        maxCoinLabel = new Label("Max Coin: " + App.getInstance().logedInUser.getMaxCoin(), skin);
        gamesLabel = new Label("Games Played: " + App.getInstance().logedInUser.getGamesPlayed(), skin);

        // Add user info labels to the table in a vertical row
        table.add(usernameLabel).colspan(3).pad(5).row();
        table.add(nicknameLabel).colspan(3).pad(5).row();
        table.add(emailLabel).colspan(3).pad(5).row();
        table.add(maxCoinLabel).colspan(3).pad(5).row();
        table.add(gamesLabel).colspan(3).padBottom(30).row();

        // --- Change Username ---
        newUsernameField = new TextField("", skin);
        TextButton changeUsernameButton = new TextButton("Change", skin);
        table.add(new Label("New Username", skin)).right().pad(10);
        table.add(newUsernameField).width(200).pad(10);
        table.add(changeUsernameButton).width(100).row();

        // --- Change Password ---

        oldPasswordField = new TextField("", skin);
        oldPasswordField.setPasswordMode(true);
        oldPasswordField.setPasswordCharacter('*');

        newPasswordField = new TextField("", skin);
        newPasswordField.setPasswordMode(true);
        newPasswordField.setPasswordCharacter('*');

        TextButton changePasswordButton = new TextButton("Change", skin);
        table.add(new Label("Old Password", skin)).right().pad(10);
        table.add(oldPasswordField).width(200).pad(10).row();
        table.add(new Label("New Password", skin)).right().pad(10);
        table.add(newPasswordField).width(200).pad(10);
        table.add(changePasswordButton).width(100).row();

        // --- Change Nickname ---
        newNicknameField = new TextField("", skin);
        TextButton changeNicknameButton = new TextButton("Change", skin);
        table.add(new Label("New Nickname", skin)).right().pad(10);
        table.add(newNicknameField).width(200).pad(10);
        table.add(changeNicknameButton).width(100).row();

        // --- Change Email ---
        newEmailField = new TextField("", skin);
        TextButton changeEmailButton = new TextButton("Change", skin);
        table.add(new Label("New Email", skin)).right().pad(10);
        table.add(newEmailField).width(200).pad(10);
        table.add(changeEmailButton).width(100).row();

        // --- Message Label ---
        messageLabel = new Label("", skin);
        table.add(messageLabel).colspan(3).center().padTop(20).row();

        // --- Click Listeners ---
        changeUsernameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                handleProfileChange("username");
            }
        });
        changePasswordButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                handleProfileChange("password");
            }
        });
        changeNicknameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                handleProfileChange("nickname");
            }
        });
        changeEmailButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                handleProfileChange("email");
            }
        });
    }

    private void handleProfileChange(String field) {
        Result<?> result;
        switch (field) {
            case "username":
                result = controller.changeUsername(newUsernameField.getText());
                if (result.isSuccess()) {
                    showMessage("Username updated successfully!", Color.GREEN);
                    usernameLabel.setText("Username: " + App.getInstance().logedInUser.getUsername());
                    newUsernameField.setText("");
                } else {
                    showMessage(result.getMessage(), Color.RED);
                }
                break;

            case "password":
                result = controller.changePassword(oldPasswordField.getText(), newPasswordField.getText());
                if (result.isSuccess()) {
                    showMessage("Password updated successfully!", Color.GREEN);
                    oldPasswordField.setText("");
                    newPasswordField.setText("");
                } else {
                    showMessage(result.getMessage(), Color.RED);
                }
                break;

            case "nickname":
                result = controller.changeNickname(newNicknameField.getText());
                if (result.isSuccess()) {
                    showMessage("Nickname updated successfully!", Color.GREEN);
                    nicknameLabel.setText("Nickname: " + App.getInstance().logedInUser.getNickname());
                    newNicknameField.setText("");
                } else {
                    showMessage(result.getMessage(), Color.RED);
                }
                break;

            case "email":
                result = controller.changeEmail(newEmailField.getText());
                if (result.isSuccess()) {
                    showMessage("Email updated successfully!", Color.GREEN);
                    emailLabel.setText("Email: " + App.getInstance().logedInUser.getEmail());
                    newEmailField.setText("");
                } else {
                    showMessage(result.getMessage(), Color.RED);
                }
                break;
        }
    }

    private void setupInputProcessor() {
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.ESCAPE) {
                    game.setScreen(new MainMenuScreen());
                    return true;
                }
                return false;
            }
        });
        multiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(multiplexer);
    }

    private void showMessage(String msg, Color color) {
        messageLabel.setText(msg);
        messageLabel.setColor(color);
    }

    @Override
    public void show() {
        // It's good practice to set the input processor in show()
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
    }
}