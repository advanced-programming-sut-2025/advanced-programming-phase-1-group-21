package io.github.StardewValley.views.menu.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.StardewValley.App;
import io.github.StardewValley.Main;
import io.github.StardewValley.asset.Assets;
import io.github.StardewValley.controllers.GameSaver;
import io.github.StardewValley.network.NetworkLobbyController;

public class WaitScreen implements Screen {

    private final Stage stage;
    private float timer;
    private final Label countdownLabel;

    /** The total time to wait in seconds. */
    private static final float WAIT_TIME = 20f;

    public WaitScreen() {
        this.stage = new Stage(new ScreenViewport());
        this.timer = 0f;

        Skin skin = Assets.getSkin();

        Table table = new Table();
        table.setFillParent(true);
        table.center();

        // UPDATED: The text for this label has been changed per your request.
        Label waitingLabel = new Label("Waiting for Player to REJOIN:", skin);

        countdownLabel = new Label(String.format("%.0f", WAIT_TIME), skin);
        countdownLabel.setFontScale(2f);

        table.add(waitingLabel).padBottom(20).row();
        table.add(countdownLabel);

        stage.addActor(table);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        timer += delta;

        if (timer >= WAIT_TIME) {
            App.getInstance().saveGame();
            NetworkLobbyController.leaveLobby();
            Main.getInstance().setScreen(new MainMenuScreen());
            return;
        }

        float timeLeft = WAIT_TIME - timer;
        countdownLabel.setText(String.format("%.0f", timeLeft));

        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
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
        stage.dispose();
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}
}