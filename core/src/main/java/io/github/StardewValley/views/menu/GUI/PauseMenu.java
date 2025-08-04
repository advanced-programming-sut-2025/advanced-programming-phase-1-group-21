package io.github.StardewValley.views.menu.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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

public class PauseMenu implements Screen {
    private Stage stage;
    private Skin skin;
    private TextButton exitButton, scoreboardButton;
    private Label date, time, weather, energy;
    private Texture bgTexture;
    private GameController controller = App.getInstance().currentPlayerController;

    public PauseMenu() {

        this.skin = Assets.getSkin();
        this.stage = new Stage(new ScreenViewport());
        createUI();
    }

    private void createUI() {
        stage.clear();
        setDarkBackground();

        // Create exit button
        exitButton = new TextButton("X", skin);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("CLICKED");
                Main.getInstance().setScreen(new GameScreen());
            }
        });
        exitButton.setPosition(1670, 850);

        // Create scoreboard button
        scoreboardButton = new TextButton("SCOREBOARD", skin);
        scoreboardButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Scoreboard button clicked");
                // Add your scoreboard functionality here
                Main.getInstance().setScreen(new ScoreboardScreen());
            }
        });
        // Position the scoreboard button below the info labels
        float centerX = Gdx.graphics.getWidth() / 2f;
        float centerY = Gdx.graphics.getHeight() / 2f;
        scoreboardButton.setPosition(centerX - scoreboardButton.getWidth() / 2, centerY - 150);

        // Create info labels
        date = new Label("Date: " + controller.getDate().getData(), skin);
        time = new Label("Time: " + controller.getTime(), skin);
        weather = new Label("Weather: " + controller.getWeather(), skin);
        energy = new Label("Energy: " + controller.showEnergy(), skin);

        // Style labels
        date.setColor(Color.WHITE);
        time.setColor(Color.WHITE);
        weather.setColor(Color.WHITE);
        energy.setColor(Color.WHITE);


        date.setPosition(centerX - date.getWidth() / 2, centerY + 75);
        time.setPosition(centerX - time.getWidth() / 2, centerY + 25);
        weather.setPosition(centerX - weather.getWidth() / 2, centerY - 25);
        energy.setPosition(centerX - energy.getWidth() / 2, centerY - 75);

        // Add actors to stage
        stage.addActor(exitButton);
        stage.addActor(scoreboardButton);
        stage.addActor(date);
        stage.addActor(time);
        stage.addActor(weather);
        stage.addActor(energy);
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

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        updateLabels();
    }

    private void updateLabels() {
        date.setText("Date: " + controller.getDate().getData());
        time.setText("Time: " + controller.getTime());
        weather.setText("Weather: " + controller.getWeather());
        energy.setText("Energy: " + controller.showEnergy());

        // Re-center labels after text changes
        float centerX = Gdx.graphics.getWidth() / 2f;
        date.setX(centerX - date.getWidth() / 2);
        time.setX(centerX - time.getWidth() / 2);
        weather.setX(centerX - weather.getWidth() / 2);
        energy.setX(centerX - energy.getWidth() / 2);
    }

    @Override
    public void render(float delta) {
        // Clear screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Update and draw stage
        stage.act(delta);
        stage.draw();

        // Debug input (optional)
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            // System.out.println("Click at: " + Gdx.input.getX() + ", " + Gdx.input.getY());
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        createUI(); // Recreate UI to adjust to new size
    }

    @Override
    public void pause() {
        // Optional: Handle game pause
    }

    @Override
    public void resume() {
        // Optional: Handle game resume
    }

    @Override
    public void hide() {
        // Optional: Clean up when screen is hidden
    }

    @Override
    public void dispose() {
    }
}