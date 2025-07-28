package io.github.StardewValley.views.menu.GUI;

import java.util.List;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.StardewValley.Main;
import io.github.StardewValley.asset.Assets;
import io.github.StardewValley.network.NetworkLobbyController;
import models.network.Lobby;
import models.user.User;

import java.util.ArrayList;

public class LobbiesMenuScreen implements Screen {

    private final Main game;
    private Stage stage;
    private Table selectedRow = null;
    private Table onlinePlayersTable, lobbyTable;
    List <Lobby> lobbies = new ArrayList <>();
    List <User> users = new ArrayList <>();
    Skin skin;

    public LobbiesMenuScreen() {
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

        Label lobbiesTitle = new Label("Lobbies", skin);
        lobbiesTitle.setFontScale(1.5f);
        Label onlinePlayersTitle = new Label("Online Players", skin);
        onlinePlayersTitle.setFontScale(1.5f);

        lobbyTable = new Table();
        lobbyTable.top();
        lobbyTable.pad(10);
        lobbyTable.defaults().pad(5);

        selectedRow = null;

        lobbies = getLobbies();
        fillLobbyTable();

        ScrollPane lobbyScrollPane = new ScrollPane(lobbyTable, skin);
        lobbyScrollPane.setFadeScrollBars(false);

        onlinePlayersTable = new Table();
        onlinePlayersTable.top();
        onlinePlayersTable.pad(10);
        onlinePlayersTable.defaults().pad(5);

        users = getUsers();
        fillOnlinePlayerTable();


        ScrollPane onlineScrollPane = new ScrollPane(onlinePlayersTable, skin);
        onlineScrollPane.setFadeScrollBars(false);

        TextButton backButton = new TextButton("Back", skin);
        TextButton createLobbyButton = new TextButton("Create Lobby", skin);
        TextButton refreshButton = new TextButton("Refresh", skin);
        TextButton joinByIDButton = new TextButton("Join by ID", skin);

        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen());
            }
        });

        createLobbyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                createLobbyDialog();
            }
        });

        refreshButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                refreshWindow();
            }
        });

        joinByIDButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                joinByIDDialog();
            }
        });

        Table mainTable = new Table();
        mainTable.add(lobbiesTitle).padLeft(50).align(Align.left);
        mainTable.add(onlinePlayersTitle).row();
        mainTable.add(lobbyScrollPane).padTop(sh / 40).padRight(sw / 20).width((int) (sw * (2. / 3 - 1. / 20))).height(sh * 3 / 5);
        mainTable.add(onlineScrollPane).padTop(sh / 40).width(sw / 4).height(sh * 3 / 5).row();

        mainTable.setPosition(sw / 2, sh * 5 / 8);
        int backW = sw / 15, refreshW = sw / 10, createW = sw / 8, joinW = sw / 8, d = sw / 7, base = sw / 15;
        backButton.setPosition(base, sh / 8);
        backButton.setSize(sw / 15, sh / 15);
        refreshButton.setPosition(base + backW + d, sh / 8);
        refreshButton.setSize(sw / 10, sh / 15);
        createLobbyButton.setPosition(base + backW + refreshW + 2 * d, sh / 8);
        createLobbyButton.setSize(sw / 8, sh / 15);
        joinByIDButton.setPosition(base + backW + refreshW + createW + 3 * d, sh / 8);
        joinByIDButton.setSize(sw / 8, sh / 15);


        stage.addActor(mainTable);
        stage.addActor(backButton);
        stage.addActor(refreshButton);
        stage.addActor(createLobbyButton);
        stage.addActor(joinByIDButton);


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

    private void fillOnlinePlayerTable() {
        onlinePlayersTable.clear();
        for (User user: users) {
            Label nameLabel = new Label(user.getNickname(), skin);
            onlinePlayersTable.add(nameLabel).expandX().fillX().row();
        }
    }

    private void fillLobbyTable() {
        lobbyTable.clear();
        for (Lobby l: lobbies) {
            Label nameLabel = new Label("Name: " + l.getName(), skin);
            Label idLabel = new Label("ID: " + l.getID(), skin);

            StringBuilder membersString = new StringBuilder();
            for (User user: l.getUsers())
                membersString.append(user.getNickname()).append(", ");
            membersString.setLength(membersString.length() - 2);
            Label members = new Label("Members: " + membersString, skin);


            Table row = new Table();
            row.left();
            row.setTouchable(Touchable.enabled);
            row.add(nameLabel).padRight(20);
            row.add(idLabel);
            row.pad(5).row();
            row.add(members).expandX().fillX();

            row.setBackground(skin.newDrawable("white", Color.CLEAR));

            row.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (selectedRow != null)
                        selectedRow.setBackground(skin.newDrawable("white", Color.CLEAR));

                    row.setBackground(skin.newDrawable("white", new Color(Color.LIGHT_GRAY.r, Color.LIGHT_GRAY.g, Color.LIGHT_GRAY.b, 0.4f)));
                    selectedRow = row;

                    if (getTapCount() == 2) {
                        Lobby l = getLobbyByName(nameLabel.getText().replace("Name: ", "").toString());
                        if (l.isPrivate()) {
                            getPasswordDialog(l);
                            // ...
                        }
                        else {
                            joinTheLobby(l);
                        }
                    }
                }
            });

            lobbyTable.add(row).expandX().fillX().row();

            // Optional: separator
            Image separator = new Image(skin.newDrawable("white", Color.DARK_GRAY));
            lobbyTable.add(separator).height(1).expandX().fillX().padTop(5).padBottom(5).row();
        }
    }

    private List <Lobby> getLobbies() {
        return NetworkLobbyController.getAllLobbies();
    }

    private List <User> getUsers() {
        return NetworkLobbyController.getOnlineUsers();
    }


    private void refreshWindow() {
        lobbies = getLobbies();
        users = getUsers();

        fillOnlinePlayerTable();
        fillLobbyTable();
    }

    private Lobby getLobbyByName(String name) {
        for (Lobby l: lobbies)
            if (l.checkName(name))
                return l;
        return null;
    }

    private Lobby getLobbyByID(int id) {
        for (Lobby l: lobbies)
            if (l.checkID(id))
                return l;
        return null;
    }

    private void getPasswordDialog(Lobby lobby) {
        Dialog dialog = new Dialog("Enter Password", skin) {
            @Override
            protected void result(Object object) {
                if (object.equals(true)) {
                    TextField tf = findActor("passwordInput");
                    String password = tf.getText();
                    joinTheLobby(lobby, password);
                }
            }
        };

        TextField passwordField = new TextField("", skin);
        passwordField.setMessageText("Password");
        passwordField.setName("passwordInput");

        dialog.getContentTable().pad(10);


        dialog.getContentTable().add(passwordField).width(200).padBottom(10).row();

        dialog.button("OK", true);
        dialog.button("Cancel", false);
        dialog.setModal(true);
        dialog.setMovable(true);
        dialog.setResizable(false);
        dialog.show(stage);
    }

    private void createLobbyDialog() {
        Dialog dialog = new Dialog("Create Lobby", skin) {
            @Override
            protected void result(Object object) {
                if (object.equals(true)) {
                    String name = ((TextField) findActor("nameField")).getText();
                    String password = ((TextField) findActor("passwordField")).getText();
                    boolean isPrivate = ((CheckBox) findActor("isPrivateBox")).isChecked();
                    boolean isInvisible = ((CheckBox) findActor("isInvisibleBox")).isChecked();

                    if (!isPrivate) {
                        password = null;
                    }
                    createLobby(name, password, isPrivate, isInvisible);
                }
            }
        };

        // Name input
        Label nameLabel = new Label("Name:", skin);
        TextField nameField = new TextField("", skin);
        nameField.setMessageText("Name");
        nameField.setName("nameField");

        // Password input
        Label passwordLabel = new Label("Password:", skin);
        TextField passwordField = new TextField("", skin);
        passwordField.setMessageText("Password");
        passwordField.setName("passwordField");
        passwordField.setDisabled(true);

        // Private Checkbox
        CheckBox isPrivate = new CheckBox("Private", skin);
        isPrivate.setName("isPrivateBox");
        isPrivate.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                passwordField.setDisabled(!isPrivate.isChecked());
                if (!isPrivate.isChecked()) {
                    passwordField.setMessageText("Password");
                }
            }
        });

        // Invisible Checkbox
        CheckBox isInvisible = new CheckBox("Invisible", skin);
        isInvisible.setName("isInvisibleBox");

        // Layout
        Table content = dialog.getContentTable();
        content.pad(20);
        content.defaults().pad(10);

        content.add(nameLabel).left();
        content.add(nameField).width(200).row();

        content.add(isPrivate).colspan(2).left().row();

        content.add(passwordLabel).left();
        content.add(passwordField).width(200).row();

        content.add(isInvisible).colspan(2).left().row();


        // Buttons
        dialog.button("OK", true);
        dialog.button("Cancel", false);
        dialog.setModal(true);
        dialog.setMovable(true);
        dialog.setResizable(false);
        dialog.show(stage);
    }

    // joinByIDDialog
    private void joinByIDDialog() {
        Dialog dialog = new Dialog("Enter Lobby's ID", skin) {
            @Override
            protected void result(Object object) {
                if (object.equals(true)) {
                    TextField tf = findActor("IDInput");
                    String ID = tf.getText();
                    int intID = 0;
                    try {
                        intID = Integer.parseInt(ID);
                    }
                    catch (NumberFormatException e) {
                        lobbyNotFoundDialog();
                        return;
                    }

                    Lobby lobby = getLobbyByID(intID);
                    if (lobby == null) {
                        lobbyNotFoundDialog();
                        return;
                    }
                    else if (lobby.isPrivate()) {
                        getPasswordDialog(lobby);
                    }
                    else {
                        joinTheLobby(lobby);
                    }
                }
            }
        };

        TextField passwordField = new TextField("", skin);
        passwordField.setMessageText("ID");
        passwordField.setName("IDInput");

        dialog.getContentTable().pad(10);

        dialog.getContentTable().add(passwordField).width(200).padBottom(10).row();

        dialog.button("OK", true);
        dialog.button("Cancel", false);
        dialog.setModal(true);
        dialog.setMovable(true);
        dialog.setResizable(false);
        dialog.show(stage);
    }

    private void lobbyNotFoundDialog() {
        Dialog dialog = new Dialog("Lobby not found", skin);
        dialog.text("You typed a wrong ID because there is no lobby with that ID.");

        dialog.button("OK");
        dialog.setModal(true);
        dialog.setMovable(true);
        dialog.setResizable(false);
        dialog.show(stage);
    }

    private void joinTheLobby(Lobby lobby, String password) {
        if (!lobby.isPrivate())
            System.out.println("This lobby didn't have password");

        System.out.println("Trying to join the lobby. lobby name: " + lobby.getName() + ", lobby password: " + lobby.getPassword() + ", entered password: " + password);

        NetworkLobbyController.joinLobby(lobby.getID(), password);
        game.setScreen(new LobbyScreen(NetworkLobbyController.getLobby().getData()));
    }

    private void joinTheLobby(Lobby lobby) {
        if (lobby.isPrivate()) {
            System.out.println("You can't join a private lobby without passowrd");
        }
        System.out.println("Trying to join the lobby. name: " + lobby.getName());
        NetworkLobbyController.joinLobby(lobby.getID(), null);
        game.setScreen(new LobbyScreen(NetworkLobbyController.getLobby().getData()));
    }

    private void createLobby(String name, String password, boolean isPrivate, boolean isInvisible) {
        NetworkLobbyController.createLobby(name, password, isPrivate, isInvisible);
        game.setScreen(new LobbyScreen(NetworkLobbyController.getLobby().getData()));
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