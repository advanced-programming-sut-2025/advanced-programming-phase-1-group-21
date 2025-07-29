package io.github.StardewValley.views.menu.GUI;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.StardewValley.Main;
import io.github.StardewValley.asset.Assets;
import io.github.StardewValley.controllers.LoginMenuController;

public class MainMenuScreen implements Screen {

    private final Main game;
    private Stage stage;

    public MainMenuScreen() {
        this.game = Main.getInstance();
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

        Table table = new Table();
        table.setFillParent(true);
        table.center();
        stage.addActor(table);

        Label title = new Label("Main Menu", skin);
        title.setFontScale(1.5f);
        table.add(title).colspan(2).padBottom(30).row();

        // Profile Button
        TextButton profileButton = new TextButton("Profile", skin);
        profileButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new ProfileMenuScreen());
            }
        });

        // Setting Button
        TextButton settingButton = new TextButton("Setting", skin);
        settingButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new SettingMenuScreen());
                game.setScreen(new GameScreen());
            }
        });

        // Lobbies Button
        TextButton lobbiesButton = new TextButton("Lobbies", skin);
        lobbiesButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new LobbiesMenuScreen());
            }
        });

        // Add buttons to layout
        table.add(profileButton).width(200).height(50).pad(10).row();
        table.add(settingButton).width(200).height(50).pad(10).row();
        table.add(lobbiesButton).width(200).height(50).pad(10).row();

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.ESCAPE) {
                    LoginMenuController.logout();
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