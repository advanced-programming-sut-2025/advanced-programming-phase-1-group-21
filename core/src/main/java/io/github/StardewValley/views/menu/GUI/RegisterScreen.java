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
import io.github.StardewValley.controllers.RegisterMenuController;
import io.github.StardewValley.App;
import io.github.StardewValley.asset.Assets;
import models.result.Result;
import models.user.Gender;

public class RegisterScreen implements Screen {
    private final Main game;
    private final RegisterMenuController controller;
    private Stage stage;
    private TextField usernameField;
    private TextField nicknameField;

    private TextField passwordField;
    private TextField emailField;
    private TextField repeatPasswordField;
    private SelectBox<String> securityQuestionBox;
    private SelectBox<Gender> genderBox;
    private TextField securityAnswerField;
    private Label messageLabel;

    public RegisterScreen() {
        this.game = Main.getInstance();
        this.controller = new RegisterMenuController();
        createUI();
    }

    private void createUI() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Texture backgroundTexture = Assets.getMenuBackground();
        Image backgroundImage = new Image(new TextureRegionDrawable(new TextureRegion(backgroundTexture)));
        backgroundImage.setFillParent(true);
        stage.addActor(backgroundImage);

        Skin skin = Assets.getSkin();

        Table table = new Table();
        table.setFillParent(true);
        table.center();
        stage.addActor(table);

        usernameField = new TextField("", skin);
        table.add(new Label("username", skin)).right().pad(10);
        table.add(usernameField).width(200).row();

        nicknameField = new TextField("", skin);
        table.add(new Label("nickname", skin)).right().pad(10);
        table.add(nicknameField).width(200).row();

        emailField = new TextField("", skin);
        table.add(new Label("email", skin)).right().pad(10);
        table.add(emailField).width(200).row();

        passwordField = new TextField("", skin);
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');
        table.add(new Label("password", skin)).right().pad(10);
        table.add(passwordField).width(200).row();

        repeatPasswordField = new TextField("", skin);
        repeatPasswordField.setPasswordMode(true);
        repeatPasswordField.setPasswordCharacter('*');
        table.add(new Label("repeat password", skin)).right().pad(10);
        table.add(repeatPasswordField).width(200).row();

        genderBox = new SelectBox<>(skin);
        genderBox.setItems(Gender.values());
        table.add(new Label("gender", skin)).right().pad(10);
        table.add(genderBox).width(200).row();

        securityQuestionBox = new SelectBox<>(skin);
        securityQuestionBox.setItems(App.getInstance().getSecurityQuestions().toArray());
        table.add(new Label("security question", skin)).right().pad(10);
        table.add(securityQuestionBox).width(200).row();

        securityAnswerField = new TextField("", skin);
        table.add(new Label("security answer", skin)).right().pad(10);
        table.add(securityAnswerField).width(200).row();

        messageLabel = new Label("", skin);
        table.add(messageLabel).colspan(2).center().padTop(10).row();

        TextButton registerButton = new TextButton("register", skin);
        registerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                handleRegistration();
            }
        });
        table.add(registerButton).colspan(2).center().padTop(20).row();

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
        UIUtil.goToConsole(multiplexer);

        multiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(multiplexer);
    }

    private void handleRegistration() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String repeatPassword = repeatPasswordField.getText();
        String securityQuestion = securityQuestionBox.getSelected();
        String securityAnswer = securityAnswerField.getText();
        String nickname = nicknameField.getText();
        Gender gender = genderBox.getSelected();
        String email = emailField.getText();

        Result<Void> result = controller.register(username, password, repeatPassword, nickname, email, gender);

        if (result.isSuccess()) {
            showMessage("success", Color.GREEN);
            //game.setScreen(new LoginScreen());
        } else {
            showMessage(result.getMessage(), Color.RED);
        }
    }

    public void showMessage(String msg, Color color) {
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
