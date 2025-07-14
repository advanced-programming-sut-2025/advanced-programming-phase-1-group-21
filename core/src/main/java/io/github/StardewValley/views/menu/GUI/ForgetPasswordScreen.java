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

public class ForgetPasswordScreen implements Screen {

    private final Main game;
    private final LoginMenuController controller;
    private Stage stage;

    private TextField usernameField;
    private Label questionLabel;
    private TextField answerField;
    private TextField newPasswordField;
    private TextField confirmPasswordField;
    private Label messageLabel;

    private String fetchedQuestion;

    public ForgetPasswordScreen() {
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

        TextButton getQuestionButton = new TextButton("Get Security Question", skin);
        getQuestionButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                fetchSecurityQuestion();
            }
        });
        table.add(getQuestionButton).colspan(2).center().padTop(5).row();

        questionLabel = new Label("Security question will appear here", skin);
        table.add(questionLabel).colspan(2).center().padTop(10).row();

        answerField = new TextField("", skin);
        table.add(new Label("Your Answer", skin)).right().pad(10);
        table.add(answerField).width(200).row();

        newPasswordField = new TextField("", skin);
        newPasswordField.setPasswordMode(true);
        newPasswordField.setPasswordCharacter('*');
        table.add(new Label("New Password", skin)).right().pad(10);
        table.add(newPasswordField).width(200).row();

        confirmPasswordField = new TextField("", skin);
        confirmPasswordField.setPasswordMode(true);
        confirmPasswordField.setPasswordCharacter('*');
        table.add(new Label("Confirm Password", skin)).right().pad(10);
        table.add(confirmPasswordField).width(200).row();

        messageLabel = new Label("", skin);
        table.add(messageLabel).colspan(2).center().padTop(10).row();

        TextButton submitButton = new TextButton("Submit", skin);
        submitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                handleResetPassword();
            }
        });
        table.add(submitButton).colspan(2).center().padTop(20).row();

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

        UIUtil.goToConsole(multiplexer);
    }

    private void fetchSecurityQuestion() {
        String username = usernameField.getText().trim();
        String question = controller.getQuestion(username);

        if (question == null || question.isEmpty()) {
            showMessage("User not found or has no security question!", Color.RED);
            questionLabel.setText("No question");
        } else {
            fetchedQuestion = question;
            questionLabel.setText(question);
        }
    }

    private void handleResetPassword() {
        String username = usernameField.getText().trim();
        String answer = answerField.getText().trim();
        String newPass = newPasswordField.getText();
        String confirmPass = confirmPasswordField.getText();

        Result<Void> re = controller.forgetPass(username, answer, newPass, confirmPass);
        if (re.isSuccess()) {
            game.setScreen(new LoginScreen());
        } else {
            showMessage(re.getMessage(), Color.RED);
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
