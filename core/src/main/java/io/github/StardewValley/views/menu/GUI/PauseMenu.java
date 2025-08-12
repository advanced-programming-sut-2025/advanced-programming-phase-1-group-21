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
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.StardewValley.App;
import io.github.StardewValley.Main;
import io.github.StardewValley.asset.Assets;
import io.github.StardewValley.controllers.GameController;

public class PauseMenu implements Screen {
    private final Stage stage;
    private final Skin skin;
    private Texture bgTexture;
    private final GameController controller = App.getInstance().currentPlayerController;

    private final Label dateLabel;
    private final Label timeLabel;
    private final Label weatherLabel;
    private final Label energyLabel;

    public PauseMenu() {
        this.skin = Assets.getSkin();
        this.stage = new Stage(new ScreenViewport());

        // Initialize labels with empty text
        dateLabel = new Label("", skin, "default-font", Color.WHITE);
        timeLabel = new Label("", skin, "default-font", Color.WHITE);
        weatherLabel = new Label("", skin, "default-font", Color.WHITE);
        energyLabel = new Label("", skin, "default-font", Color.WHITE);

        createUI();
    }

    private void createUI() {
        Gdx.input.setInputProcessor(stage);

        Table rootTable = new Table();
        rootTable.setFillParent(true);
        stage.addActor(rootTable);

        // --- Set Dark Background ---
        Pixmap bgPixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        bgPixmap.setColor(0, 0, 0, 0.5f);
        bgPixmap.fill();
        bgTexture = new Texture(bgPixmap);
        bgPixmap.dispose();
        rootTable.setBackground(new Image(bgTexture).getDrawable());

        // --- Create UI Components ---
        TextButton exitButton = new TextButton("X", skin);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getInstance().setScreen(new GameScreen());
            }
        });

        TextButton scoreboardButton = new TextButton("SCOREBOARD", skin);
        scoreboardButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getInstance().setScreen(new ScoreboardScreen());
            }
        });
        TextButton reactionButton = new TextButton("REACTION", skin);
        reactionButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getInstance().setScreen(new ReactionScreen());
            }
        });
        TextButton musicButton = new TextButton("MUSIC", skin);
        musicButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Main.getInstance().setScreen(new MusicMenuScreen());
            }
        });

        updateLabels();

        // --- Arrange Components in the Table ---
        rootTable.add(exitButton).top().right().pad(20);
        rootTable.row();

        Table contentTable = new Table();
        contentTable.add(dateLabel).align(Align.center).padBottom(10);
        contentTable.row();
        contentTable.add(timeLabel).align(Align.center).padBottom(10);
        contentTable.row();
        contentTable.add(weatherLabel).align(Align.center).padBottom(10);
        contentTable.row();
        contentTable.add(energyLabel).align(Align.center).padBottom(50);
        contentTable.row();

        float buttonWidth = 300f;
        contentTable.add(scoreboardButton).width(buttonWidth).padBottom(15);
        contentTable.row();
        contentTable.add(reactionButton).width(buttonWidth).padBottom(15);
        contentTable.row();
        contentTable.add(musicButton).width(buttonWidth);

        rootTable.add(contentTable).expand().center();
    }


    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        updateLabels();
    }

    private void updateLabels() {
        dateLabel.setText("Date: " + controller.getDate().getData());
        timeLabel.setText("Time: " + controller.getTime());
        weatherLabel.setText("Weather: " + controller.getWeather());
        energyLabel.setText("Energy: " + controller.showEnergy());
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
    public void dispose() {
        if (bgTexture != null) bgTexture.dispose();
        stage.dispose();
    }

    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}