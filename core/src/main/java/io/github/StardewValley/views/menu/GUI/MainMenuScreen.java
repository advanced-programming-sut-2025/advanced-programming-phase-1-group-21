package io.github.StardewValley.views.menu.GUI;

import com.badlogic.gdx.*;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.kotcrab.vis.ui.widget.file.FileChooser;
import com.kotcrab.vis.ui.widget.file.FileChooserAdapter;
import io.github.StardewValley.Main;
import io.github.StardewValley.asset.Assets;
import io.github.StardewValley.controllers.GameSaver;
import io.github.StardewValley.controllers.LoginMenuController;
import io.github.StardewValley.network.NetworkLobbyController;

import java.io.File;

// Note: This implementation assumes you have the VisUI library in your project dependencies
// for the FileChooser to work. You also need to call VisUI.load() once at startup.

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
                // FIXED: The second setScreen call was removed as it would prevent the setting screen from ever showing.
                game.setScreen(new SettingMenuScreen());
            }
        });

        // --- NEW CODE START ---

        // Load Game Button
        TextButton loadGameButton = new TextButton("Load Game", skin);
        loadGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                openFileChooser();
            }
        });

        // --- NEW CODE END ---

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
        // --- NEW CODE START ---
        table.add(loadGameButton).width(200).height(50).pad(10).row();
        // --- NEW CODE END ---
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

    // --- NEW CODE START ---

    /**
     * Opens a file chooser dialog to select a game file.
     */
    private void openFileChooser() {
        FileChooser chooser = new FileChooser(FileChooser.Mode.OPEN);
        chooser.setSelectionMode(FileChooser.SelectionMode.FILES);

        chooser.setListener(new FileChooserAdapter() {
            @Override
            public void selected(Array<FileHandle> files) {
                FileHandle chosenFile = files.first();
                Gdx.app.log("FileChooser", "Selected file to load: " + chosenFile.path());
                File file = chosenFile.file();
                NetworkLobbyController.createLobby("LOADING GAME", null, false, false);
                NetworkLobbyController.setGame(GameSaver.loadGame(file).getData());
                game.setScreen(new LobbyScreen(NetworkLobbyController.getLobby().getData()));
            }

            @Override
            public void canceled() {
                Gdx.app.log("FileChooser", "Selection canceled");
            }
        });

        stage.addActor(chooser.fadeIn());
    }

    // --- NEW CODE END ---

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