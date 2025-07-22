package io.github.StardewValley.views.menu.GUI;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.StardewValley.Main;
import io.github.StardewValley.controllers.LoginMenuController;
import io.github.StardewValley.asset.Assets;
import models.result.Result;
import models.user.User;

public class LoginScreen implements Screen {

    private final Main game;
    private final LoginMenuController controller;
    private Stage stage;

    private TextField usernameField;
    private TextField passwordField;
    private CheckBox stayLoggedInBox;
    private Label messageLabel;

    public LoginScreen() {
        this.game = Main.getInstance();
        this.controller = new LoginMenuController();
        createUI();
    }

    private void createUI() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Skin skin = Assets.getSkin();
        Texture backgroundTexture = Assets.getMenuBackground();

        Image backgroundImage = new Image(new TextureRegionDrawable(new TextureRegion(backgroundTexture)));
        backgroundImage.setFillParent(true);
        stage.addActor(backgroundImage);

        Table table = new Table(skin);
        table.setFillParent(true);
        table.center();
        stage.addActor(table);

        usernameField = new TextField("", skin);
        table.add(new Label("Username", skin)).right().pad(10);
        table.add(usernameField).width(200).row();

        passwordField = new TextField("", skin);
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');
        table.add(new Label("Password", skin)).right().pad(10);
        table.add(passwordField).width(200).row();

        stayLoggedInBox = new CheckBox("Stay Logged In", skin);
        table.add(stayLoggedInBox).colspan(2).left().pad(10).row();

        messageLabel = new Label("", skin);
        table.add(messageLabel).colspan(2).center().padTop(10).row();

        TextButton loginButton = new TextButton("Login", skin);
        TextButton forgetButton = new TextButton("Forgot Password", skin);

        loginButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                handleLogin();
            }
        });

        forgetButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new ForgetPasswordScreen());
            }
        });

        InputMultiplexer multiplexer = new InputMultiplexer();

        multiplexer.addProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.ESCAPE) {
                    game.setScreen(new PreMenuScreen());
                    return true;
                }
                return false;
            }
        });

        multiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(multiplexer);

        Table buttonTable = new Table();
        buttonTable.add(loginButton).padRight(20);
        buttonTable.add(forgetButton);

        table.add(buttonTable).colspan(2).center().padTop(20).row();
        UIUtil.goToConsole(multiplexer);
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        boolean stayLoggedIn = stayLoggedInBox.isChecked();

        Result<Void> result = controller.login(username, password, stayLoggedIn);
        if (result.isSuccess()) {
             game.setScreen(new MainMenuScreen());
        } else {
            showMessage(result.getMessage(), Color.RED);
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
