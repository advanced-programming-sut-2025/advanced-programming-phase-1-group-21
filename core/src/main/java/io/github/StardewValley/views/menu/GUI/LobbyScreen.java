package io.github.StardewValley.views.menu.GUI;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.StardewValley.App;
import io.github.StardewValley.Main;
import io.github.StardewValley.asset.Assets;
import io.github.StardewValley.network.NetworkLobbyController;
import io.github.StardewValley.network.Refreshable;
import models.network.Lobby;
import models.network.LobbyUser;
import models.user.User;

import java.util.HashSet;
import java.util.Set;

public class LobbyScreen implements Screen, Refreshable {
    private final Main game;
    private Stage stage;
    private Lobby lobby;
    private Table playersTable;
    private Set<String> readyPlayers = new HashSet<>();
    private TextField mapNumberField;
    private TextButton readyButton;

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
        lobbyTitle.setPosition(sw / 2f - 50, sh * 15 / 16f);

        Label nameLabel = new Label("Name: " + lobby.getName(), skin);
        nameLabel.setFontScale(1.5f);
        nameLabel.setPosition(sw / 16f, sh * 13 / 16f);

        Label passwordLabel = new Label(lobby.getPassword() != null ?
                "Password: " + lobby.getPassword() : "Password: None", skin);
        passwordLabel.setFontScale(1.5f);
        passwordLabel.setPosition(sw * 8 / 16f, sh * 13 / 16f);

        Label idLabel = new Label("ID: " + lobby.getID(), skin);
        idLabel.setFontScale(1.5f);
        idLabel.setPosition(sw / 16f, sh * 11 / 16f);

        // MAP Number Input
        Label mapLabel = new Label("MAP:", skin);
        mapLabel.setFontScale(1.5f);
        mapLabel.setPosition(sw / 16f, sh * 10 / 16f - 40);

        mapNumberField = new TextField("1", skin); // Default value 1
        mapNumberField.setMessageText("Enter your map seed");
        mapNumberField.setSize(sw / 30f, sh / 20f);
        mapNumberField.setPosition(sw / 16f + 120, sh * 10 / 16f - 40);
        mapNumberField.addListener(new InputListener() {
            @Override
            public boolean keyTyped(InputEvent event, char character) {
                return true;
            }
        });
        TextButton minusButton = new TextButton("-", skin);
        minusButton.setPosition(sw / 16f + 200, sh * 10 / 16f - 40);
        minusButton.setHeight(sh / 20f);
        TextButton plusButton = new TextButton("+", skin);
        plusButton.setPosition(sw / 16f + 250, sh * 10 / 16f - 40);
        plusButton.setHeight(sh / 20f);

        minusButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                int amount = Integer.parseInt(mapNumberField.getText());
                if(amount > 1) amount--;
                mapNumberField.setText(String.valueOf(amount));
            }
        });

        plusButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                int amount = Integer.parseInt(mapNumberField.getText());
                if (amount < 4) amount++;
                mapNumberField.setText(String.valueOf(amount));
            }
        });

        stage.addActor(mapLabel);
        stage.addActor(mapNumberField);
        stage.addActor(minusButton);
        stage.addActor(plusButton);

        // Players Table
        playersTable = new Table();
        playersTable.setPosition(sw / 16f, sh * 9 / 16f);
        playersTable.top().left();
        updatePlayersTable(); // initialize with player list

        TextButton leaveButton = new TextButton("Leave", skin);
        readyButton = new TextButton("Ready", skin);

        leaveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                leaveLobby();
            }
        });

        readyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                toggleReady();
            }
        });


        int backW = sw / 15, refreshW = sw / 10, createW = sw / 8, joinW = sw / 8, d = sw / 7, base = sw / 15;
        leaveButton.setPosition(base + backW + d, sh / 8);
        leaveButton.setSize(sw / 10, sh / 15);
        readyButton.setPosition(base + backW + refreshW + 2 * d, sh / 8);
        readyButton.setSize(sw / 8, sh / 15);


        stage.addActor(lobbyTitle);
        stage.addActor(nameLabel);
        stage.addActor(passwordLabel);
        stage.addActor(playersTable);
        stage.addActor(idLabel);
        stage.addActor(leaveButton);
        stage.addActor(readyButton);

        // ESC handling
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

    private void updatePlayersTable() {
        playersTable.clear();
        for (LobbyUser lobbyUser : lobby.getUsers()) {
            User user = lobbyUser.user;
            String displayName = user.getUsername();
            if (isAdmin(user)) {
                displayName += " (SAR JOOKHE)";
            }

            Label playerLabel = new Label(displayName, skin);

            if (lobbyUser.isReady) {
                playerLabel.setColor(Color.GREEN);
            } else {
                playerLabel.setColor(Color.RED);
            }

            playersTable.add(playerLabel).left().pad(5);
            playersTable.row();
        }
    }

    private boolean isAdmin(User user) {
        return lobby.getAdmin().equals(user);
    }

    private void toggleReady() {
        NetworkLobbyController.toggleReady();
    }

    public void refresh() {
        lobby = NetworkLobbyController.getLobby().getData();
        updatePlayersTable();
        readyButton.setText(lobby.getUserByUsername(App.getInstance().logedInUser.getUsername()).isReady ? "Unready" : "Ready");
    }

    private void leaveLobby() {
        NetworkLobbyController.leaveLobby();
        backToLobbies();
    }

    private void startGame() {
        // Logic to start game
    }

    private void backToLobbies() {
        game.setScreen(new LobbiesMenuScreen());
    }

    @Override public void show() {}
    @Override public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }
    @Override public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {
        stage.dispose();
    }

    public void sendMap() {
        String mapInput = mapNumberField.getText().trim();
        int mapNumber;
        try {
            mapNumber = Integer.parseInt(mapInput);
        } catch (NumberFormatException e) {
            mapNumber = 1;
        }
        NetworkLobbyController.sendMap(mapNumber);
    }
}