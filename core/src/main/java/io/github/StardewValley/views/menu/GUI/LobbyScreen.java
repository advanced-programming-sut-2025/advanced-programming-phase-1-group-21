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
import io.github.StardewValley.App;
import io.github.StardewValley.Main;
import io.github.StardewValley.asset.Assets;
import io.github.StardewValley.network.NetworkLobbyController;
import models.network.Lobby;
import models.user.User;

public class LobbyScreen implements Screen {
    private final Main game;
    private Stage stage;
    private Lobby lobby;
    Skin skin;

    public LobbyScreen(Lobby lobby) {
        this.lobby = lobby;
        this.game = Main.getInstance();
        createUI();
    }

    private void createUI() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        int sw = Gdx.graphics.getWidth();
        int sh = Gdx.graphics.getHeight();

        skin = Assets.getSkin();
        Texture backgroundTexture = Assets.getMenuBackground();

        Image backgroundImage = new Image(new TextureRegionDrawable(new TextureRegion(backgroundTexture)));
        backgroundImage.setFillParent(true);
        stage.addActor(backgroundImage);

        Label lobbyTitle = new Label("Lobby", skin);
        lobbyTitle.setFontScale(1.5f);
        lobbyTitle.setPosition(sw / 2 - 50, sh * 15 / 16);

        Label nameLabel = new Label("Name: " + lobby.getName(), skin);
        nameLabel.setFontScale(1.5f);
        nameLabel.setPosition(sw / 16, sh * 13 / 16);

        Label passwordLabel = new Label("Password: ", skin);
        if (lobby.getPassword() != null)
            passwordLabel.setText("Password: " + lobby.getPassword());
        else
            passwordLabel.setText("Password: None");
        passwordLabel.setFontScale(1.5f);
        passwordLabel.setPosition(sw * 8 / 16, sh * 13 / 16);

        StringBuilder playersString = new StringBuilder();
        for (User user: lobby.getUsers())
            playersString.append(user.getNickname()).append(", ");
        playersString.setLength(playersString.length() - 2);
        Label playersLabel = new Label("Players: " + playersString, skin);
        playersLabel.setFontScale(1.5f);
        playersLabel.setPosition(sw / 16, sh * 11 / 16);


        Label idLabel = new Label("ID: " + lobby.getID(), skin);
        idLabel.setFontScale(1.5f);
        idLabel.setPosition(sw / 16, sh * 9 / 16);

        TextButton backButton = new TextButton("Back", skin);
        TextButton leaveButton = new TextButton("Leave Lobby", skin);
        TextButton startButton = new TextButton("Start Game", skin);
        TextButton deleteButton = new TextButton("Delete Lobby", skin);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                backToLobbies();
            }
        });

        leaveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                leaveLobby();
            }
        });

        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                startGame();
            }
        });

        deleteButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                deleteLobby();
            }
        });

        int backW = sw / 15, refreshW = sw / 10, createW = sw / 8, joinW = sw / 8, d = sw / 7, base = sw / 15;
        backButton.setPosition(base, sh / 8);
        backButton.setSize(sw / 15, sh / 15);
        leaveButton.setPosition(base + backW + d, sh / 8);
        leaveButton.setSize(sw / 10, sh / 15);
        startButton.setPosition(base + backW + refreshW + 2 * d, sh / 8);
        startButton.setSize(sw / 8, sh / 15);
        deleteButton.setPosition(base + backW + refreshW + createW + 3 * d, sh / 8);
        deleteButton.setSize(sw / 8, sh / 15);


        stage.addActor(lobbyTitle);
        stage.addActor(nameLabel);
        stage.addActor(passwordLabel);
        stage.addActor(playersLabel);
        stage.addActor(idLabel);
        stage.addActor(backButton);
        stage.addActor(leaveButton);
        if (lobby.getUsers().get(0).getUsername().equalsIgnoreCase(App.getInstance().logedInUser.getUsername())) {
            // Admin
            stage.addActor(startButton);
            stage.addActor(deleteButton);
        }



        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.ESCAPE) {
                    game.setScreen(new MainMenuScreen());
                    return true;
                }
                return false;
            }
        });
        UIUtil.goToConsole(multiplexer);
        multiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(multiplexer);
    }

    private void leaveLobby() {
        NetworkLobbyController.leaveLobby();
        backToLobbies();
    }

    private void startGame() {

    }

    private void deleteLobby() {
        for (User user: NetworkLobbyController.getUsersOfLobby()) {
            NetworkLobbyController.removeFromLobby(user.getUsername());
        }
        backToLobbies();
    }

    private void backToLobbies() {
        game.setScreen(new LobbiesMenuScreen());
    }

    private void refresh() {
        lobby = NetworkLobbyController.getLobby().getData();
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