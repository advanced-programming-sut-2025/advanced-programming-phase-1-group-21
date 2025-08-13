package io.github.StardewValley.views.menu.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.Separator;
import com.kotcrab.vis.ui.widget.file.FileChooser;
import com.kotcrab.vis.ui.widget.file.FileChooserAdapter;
import io.github.StardewValley.App;
import io.github.StardewValley.Main;
import io.github.StardewValley.asset.Assets;
import io.github.StardewValley.controllers.GameController;
import models.MusicData;
import models.game.Player;

import java.io.File;
import java.util.List;

public class MusicMenuScreen implements Screen {
    private final SpriteBatch batch;
    private final Stage stage;
    private final BitmapFont font;
    private final Skin skin = Assets.getSkin();

    private final List<Player> players;
    private final List<MusicData> myMusic;
    private final String myPlayerName = App.getInstance().logedInUser.getUsername();

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

        if (!VisUI.isLoaded()) VisUI.load();
        FileChooser.setDefaultPrefsName("io.github.StardewValley.musicmenu");

        createUI();
    }

    private void createUI() {
        stage.clear();
        Gdx.input.setInputProcessor(stage);
        mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.pad(20); // Add overall padding to the screen edges
        stage.addActor(mainTable);

        // --- NEW: Create a drawable background for our "boxes" ---
        Drawable tableBackground = skin.newDrawable("white", new Color(0.2f, 0.2f, 0.2f, 0.8f));

        // Players Table (Left Panel)
        playersTable = new Table(skin);
        playersTable.setBackground(tableBackground); // Set the box background
        playersTable.pad(15); // Add padding inside the box

        Label playersHeader = new Label("Players in Session", skin);
        playersTable.add(playersHeader).colspan(2).padBottom(10);
        playersTable.row();

        for (final Player player : players) {
            Label playerLabel = new Label(player.getUser().getUsername(), skin);
            playerLabel.setAlignment(Align.left);
            if (player.getUser().getUsername().equals(myPlayerName)) {
                playerLabel.setColor(Color.YELLOW);
            }

            // --- NEW: Make player labels clickable ---
            playerLabel.setTouchable(Touchable.enabled);
            playerLabel.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    onPlayerClicked(player);
                }
            });

            playersTable.add(playerLabel).expandX().fillX().left();

            Label statusLabel = new Label("•", skin);
            statusLabel.setColor(player.getUser().getUsername().equals(myPlayerName) ? Color.GREEN : Color.LIGHT_GRAY);
            playersTable.add(statusLabel).width(20);
            playersTable.row().padTop(5);
        }

        // Music Table (Right Panel)
        musicTable = new Table(skin);
        musicTable.setBackground(tableBackground); // Set the box background
        musicTable.pad(15); // Add padding inside the box

        updateMusicTable();

        // Add panels to the main table
        mainTable.add(playersTable).width(Gdx.graphics.getWidth() * 0.25f).expandY().fillY().top();
        mainTable.add(musicTable).expand().fill().padLeft(20); // Add space between the two boxes

        UIUtil.createBack(() -> Main.getInstance().setScreen(new GameScreen()), stage);
    }

    private void updateMusicTable() {
        musicTable.clear();
        musicTable.top(); // Align content to the top

        TextButton uploadButton = new TextButton("Upload Music", skin);
        uploadButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                openFileChooser();
            }
        });

        // --- IMPROVED: Use a header row for better structure ---
        Table headerTable = new Table();
        headerTable.add(new Label("Your Music Library", skin)).expandX().left();
        headerTable.add(uploadButton).right();
        musicTable.add(headerTable).fillX().padBottom(15);
        musicTable.row();

        musicTable.add(new Separator()).fillX().padBottom(15).colspan(2);
        musicTable.row();

        if (myMusic.isEmpty()) {
            musicTable.add(new Label("No music files yet. Click 'Upload' to add some!", skin)).colspan(2);
        } else {
            // --- IMPROVED: Put music list in a ScrollPane for long lists ---
            Table musicListTable = new Table(skin);
            for (final MusicData musicData : myMusic) {
                // Use File to get just the filename, not the whole path
                String fileName = new File(musicData.getFilePath()).getName();
                Label musicLabel = new Label(fileName, skin);
                musicLabel.setTouchable(Touchable.enabled);
                musicLabel.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        Gdx.app.log("MusicPlayer", "Playing: " + musicData.getFilePath());
                        gc.playMusic(musicData);
                    }
                });
                musicListTable.add(musicLabel).expandX().left().pad(5);
                musicListTable.row();
            }
            ScrollPane scrollPane = new ScrollPane(musicListTable, skin);
            scrollPane.setFadeScrollBars(false);
            musicTable.add(scrollPane).expand().fill();
        }
    }

    private void onPlayerClicked(Player player) {
        MusicData musicData = player.getCurrentMusic();
        if (musicData == null) {
            System.out.println("nothing is playing");
            return;
        }
        App.getInstance().stopMusic();
        System.out.println("MUSIC IS PLAYING " + musicData.toString());
        musicData.setVolume(1);
    }

    private void openFileChooser() {
        FileChooser chooser = new FileChooser(FileChooser.Mode.OPEN);
        chooser.setSelectionMode(FileChooser.SelectionMode.FILES);

        chooser.setListener(new FileChooserAdapter() {
            @Override
            public void selected(Array<com.badlogic.gdx.files.FileHandle> files) {
                if (files.size > 0) {
                    com.badlogic.gdx.files.FileHandle chosen = files.first();
                    Gdx.app.log("FileChooser", "Selected: " + chosen.path());
                    addMusic(new MusicData(chosen.path()));
                }
            }
        });

        stage.addActor(chooser.fadeIn());
    }

    public void addMusic(MusicData musicData) {
        // Prevent duplicates
        if (!myMusic.contains(musicData)) {
            gc.addMusic(musicData);
            updateMusicTable(); // Refresh the list
        } else {
            Gdx.app.log("Music", "Music already in list: " + musicData.getFilePath());
        }
    }

    @Override public void show() { Gdx.input.setInputProcessor(stage); }
    @Override public void render(float delta) { ScreenUtils.clear(0.1f, 0.1f, 0.1f, 1); stage.act(delta); stage.draw(); }
    @Override public void resize(int width, int height) { stage.getViewport().update(width, height, true); }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() { batch.dispose(); stage.dispose(); font.dispose(); if (VisUI.isLoaded()) VisUI.dispose(); }
}