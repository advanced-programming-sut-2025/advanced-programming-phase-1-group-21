package io.github.StardewValley.views.menu.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.file.FileChooser;
import com.kotcrab.vis.ui.widget.file.FileChooserAdapter;
import com.kotcrab.vis.ui.widget.file.FileTypeFilter;
import io.github.StardewValley.App;
import io.github.StardewValley.Main;
import io.github.StardewValley.asset.Assets;
import io.github.StardewValley.controllers.GameController;
import models.MusicData;
import models.game.Player;

import java.util.List;

public class MusicMenuScreen implements Screen {
    private final SpriteBatch batch;
    private final Stage stage;
    private final BitmapFont font;
    private final Skin skin = Assets.getSkin();

    private List<Player> players;
    private List<MusicData> myMusic;
    private String myPlayerName = App.getInstance().logedInUser.getUsername();
    private FileHandle lastSelectedFile;

    private Table mainTable;
    private Table playersTable;
    private Table musicTable;

    private final GameController gc = App.getInstance().currentPlayerController;

    public MusicMenuScreen() {
        this.players = App.getInstance().currentPlayerViewController.game.getPlayers();
        this.myMusic = App.getInstance().currentPlayerViewController.player.getMusics();

        batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport());
        font = new BitmapFont();

        // Load VisUI skin for FileChooser
        if (!VisUI.isLoaded()) VisUI.load();
        FileChooser.setDefaultPrefsName("io.github.StardewValley.musicmenu");

        createUI();
    }

    private void createUI() {
        stage.clear();
        Gdx.input.setInputProcessor(stage);
        mainTable = new Table();
        mainTable.setFillParent(true);
        stage.addActor(mainTable);

        // Players Table (Left)
        playersTable = new Table();
        playersTable.defaults().pad(5);
        Label playersHeader = new Label("Players", skin);
        playersTable.add(playersHeader).colspan(2);
        playersTable.row();

        for (Player player : players) {
            Label playerLabel = new Label(player.getUser().getUsername(), skin);
            playerLabel.setAlignment(Align.left);
            if (player.getUser().getUsername().equals(myPlayerName)) {
                playerLabel.setColor(Color.YELLOW);
            }
            playersTable.add(playerLabel).expandX().fillX().left();

            Label statusLabel = new Label("•", skin);
            statusLabel.setColor(player.getUser().getUsername().equals(myPlayerName) ? Color.GREEN : Color.LIGHT_GRAY);
            playersTable.add(statusLabel).width(20);
            playersTable.row();
        }

        // Music Table (Right)
        musicTable = new Table();
        updateMusicTable();

        mainTable.add(playersTable).width(Gdx.graphics.getWidth() * 0.25f).expandY().fillY().pad(10);
        mainTable.add(musicTable).expand().fill().pad(10);

        UIUtil.createBack(() -> Main.getInstance().setScreen(new GameScreen()), stage);
    }

    private void updateMusicTable() {
        musicTable.clear();

        TextButton uploadButton = new TextButton("Upload Music", skin);
        uploadButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                openFileChooser();
            }
        });
        musicTable.add(uploadButton).padBottom(20).colspan(2);
        musicTable.row();

        musicTable.add(new Label("Your Music", skin)).colspan(2);
        musicTable.row();

        if (myMusic.isEmpty()) {
            musicTable.add(new Label("No music files yet", skin)).colspan(2);
            musicTable.row();
        } else {
            for (final MusicData musicData : myMusic) {
                Label musicLabel = new Label(musicData.getFilePath(), skin);
                musicLabel.setTouchable(Touchable.enabled);
                musicLabel.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        musicData.play();
                    }
                });
                musicTable.add(musicLabel).expandX().left().pad(5);
                musicTable.row();
            }
        }
    }

    private void openFileChooser() {
        FileChooser chooser = new FileChooser(FileChooser.Mode.OPEN);
        chooser.setSelectionMode(FileChooser.SelectionMode.FILES);

        chooser.setListener(new FileChooserAdapter() {
            @Override
            public void selected(Array<FileHandle> files) {
                FileHandle chosen = files.first();
                Gdx.app.log("FileChooser", "Selected: " + chosen.path());
                addMusic(new MusicData(chosen.path()));
            }

            @Override
            public void canceled() {
                Gdx.app.log("FileChooser", "Selection canceled");
            }
        });

        stage.addActor(chooser.fadeIn());
    }

    public void addMusic(MusicData musicData) {
        gc.addMusic(musicData);
        updateMusicTable();
    }

    @Override public void show() { Gdx.input.setInputProcessor(stage); }
    @Override public void render(float delta) { ScreenUtils.clear(0.1f, 0.1f, 0.1f, 1); stage.act(delta); stage.draw(); }
    @Override public void resize(int width, int height) { stage.getViewport().update(width, height, true); }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() { batch.dispose(); stage.dispose(); font.dispose(); if (VisUI.isLoaded()) VisUI.dispose(); }
}
