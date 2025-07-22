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
import models.result.Result;

public class ProfileMenuScreen implements Screen {

    private final Main game;
    private Stage stage;
    private ProfileMenuController controller;

    private TextField newUsernameField;
    private TextField newNicknameField;
    private TextField newPasswordField;
    private TextField newEmailField;
    private TextField oldPasswordField;
    private TextButton changeUsername;
    private TextButton changePassword;
    private TextButton changeEmail;
    private TextButton changeNickname;

    private Label messageLabel;
    private Label usernameLabel;
    private Label nicknameLabel;
    private Label emailLabel;

    public ProfileMenuScreen() {
        this.game = Main.getInstance();
        this.controller = new ProfileMenuController();
        createUI();
    }

    private void createUI() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Skin skin = Assets.getSkin();
        Texture backgroundTexture = Assets.getMenuBackground();

        usernameLabel = new Label("Username : " + App.getInstance().logedInUser.getUsername() , skin);
        nicknameLabel = new Label("Nickname : " + App.getInstance().logedInUser.getNickname() , skin);
        emailLabel = new Label("Email : " + App.getInstance().logedInUser.getEmail() , skin);

        Image backgroundImage = new Image(new TextureRegionDrawable(new TextureRegion(backgroundTexture)));
        backgroundImage.setFillParent(true);
        stage.addActor(backgroundImage);

        Table table = new Table();
        table.setFillParent(true);
        table.center();
        stage.addActor(table);

        usernameLabel.setX(stage.getWidth() / 2 - usernameLabel.getWidth() / 2);
        usernameLabel.setY(stage.getHeight() / 2 + 400);
        stage.addActor(usernameLabel);

        nicknameLabel.setX(stage.getWidth() / 2 -nicknameLabel.getWidth() / 2);
        nicknameLabel.setY(stage.getHeight() / 2 + 320);
        stage.addActor(nicknameLabel);

        emailLabel.setX(stage.getWidth() / 2 - emailLabel.getWidth() / 2);
        emailLabel.setY(stage.getHeight() / 2 + 240);
        stage.addActor(emailLabel);

        newUsernameField = new TextField("", skin);
        table.add(new Label("New Username", skin)).right().pad(10);
        table.add(newUsernameField).width(200).pad(10);
        changeUsername = new TextButton("Change", skin);
        changeUsername.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                handleProfile(newUsernameField);
            }
        });
        table.add(changeUsername).width(100).row();

        newPasswordField= new TextField("", skin);
        oldPasswordField = new TextField("", skin);
        table.add(new Label("Old Password", skin)).right().pad(10);
        table.add(oldPasswordField).width(200).pad(10).row();
        table.add(new Label("New Password", skin)).right().pad(10);
        table.add(newPasswordField).width(200).pad(10);
        changePassword = new TextButton("Change", skin);
        changePassword.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                handleProfile(newPasswordField);
            }
        });
        table.add(changePassword).width(100).row();

        newNicknameField = new TextField("", skin);
        table.add(new Label("New Nickname", skin)).right().pad(10);
        table.add(newNicknameField).width(200).pad(10);
        changeNickname = new TextButton("Change", skin);
        changeNickname.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                handleProfile(newNicknameField);
            }
        });
        table.add(changeNickname).width(100).row();

        newEmailField = new TextField("", skin);
        table.add(new Label("New Email", skin)).right().pad(10);
        table.add(newEmailField).width(200).pad(10);
        changeEmail = new TextButton("Change", skin);
        changeEmail.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                handleProfile(newEmailField);
            }
        });
        table.add(changeEmail).width(100).row();

        messageLabel = new Label("", skin);
        messageLabel.setX(stage.getWidth()/2 - 200);
        messageLabel.setY(stage.getHeight()/2 - 200);
        stage.addActor(messageLabel);



//        Label title = new Label("It's going to be profile menu", skin);
//        title.setFontScale(1.5f);
//        table.add(title).colspan(2).padBottom(30).row();

        // Profile Button
//        TextButton profileButton = new TextButton("Profile", skin);
//        profileButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
////                game.setScreen(new LoginScreen());
//            }
//        });
//
//        // Setting Button
//        TextButton settingButton = new TextButton("Setting", skin);
//        settingButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
////                game.setScreen(new RegisterScreen());
//            }
//        });
//
//        // Lobbies Button
//        TextButton lobbiesButton = new TextButton("Lobbies", skin);
//        lobbiesButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
////                game.setScreen(new RegisterScreen());
//            }
//        });
//
//        // Add buttons to layout
//        table.add(profileButton).width(200).height(50).pad(10).row();
//        table.add(settingButton).width(200).height(50).pad(10).row();
//        table.add(lobbiesButton).width(200).height(50).pad(10).row();

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
        UIUtil.goToConsole(multiplexer);
        multiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(multiplexer);
    }

    private void handleProfile(TextField textField) {
        if(textField.equals(newUsernameField)) {
            String newUsername = newUsernameField.getText();
            Result<?> result= controller.changeUsername(newUsername);
            if(result.isSuccess())
                showMessage("" , Color.RED);
            else
                showMessage(result.getMessage() , Color.RED);
            usernameLabel.setText("Username : " + App.getInstance().logedInUser.getUsername());
        }
        else if(textField.equals(newPasswordField)) {
            String oldPassword = oldPasswordField.getText();
            String newPassword = newPasswordField.getText();
            Result<?> result= controller.changePassword(oldPassword, newPassword);
            if(result.isSuccess())
                showMessage("" , Color.RED);
            else
                showMessage(result.getMessage() , Color.RED);
        }
        else if(textField.equals(newNicknameField)) {
            String newNickname = newNicknameField.getText();
            Result<?> result= controller.changeNickname(newNickname);
            if(result.isSuccess())
                showMessage("" , Color.RED);
            else
                showMessage(result.getMessage() , Color.RED);
            nicknameLabel.setText("Nickname : " + App.getInstance().logedInUser.getNickname());
        }
        else if(textField.equals(newEmailField)) {
            String newEmail = newEmailField.getText();
            Result<?> result= controller.changeEmail(newEmail);
            if(result.isSuccess())
                showMessage("" , Color.RED);
            else
                showMessage(result.getMessage() , Color.RED);
            emailLabel.setText("Email : " + App.getInstance().logedInUser.getEmail());
        }
    }

    private void showMessage(String msg, Color color) {
        messageLabel.setText(msg);
        messageLabel.setColor(color);
    }



    @Override
    public void show() {}

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