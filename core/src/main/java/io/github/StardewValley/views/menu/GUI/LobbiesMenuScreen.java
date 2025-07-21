package io.github.StardewValley.views.menu.GUI;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.StardewValley.Main;
import io.github.StardewValley.asset.Assets;
import models.Lobby;
import models.user.User;

import java.util.ArrayList;

public class LobbiesMenuScreen implements Screen {

    private final Main game;
    private Stage stage;
    private Table selectedRow = null;

    public LobbiesMenuScreen() {
        this.game = Main.getInstance();
        createUI();
    }

    private void createUI() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        int sw = Gdx.graphics.getWidth();
        int sh = Gdx.graphics.getHeight();

        Skin skin = Assets.getSkin();
        Texture backgroundTexture = Assets.getMenuBackground();

        Image backgroundImage = new Image(new TextureRegionDrawable(new TextureRegion(backgroundTexture)));
        backgroundImage.setFillParent(true);
        stage.addActor(backgroundImage);

        Label lobbiesTitle = new Label("Lobbies", skin);
        lobbiesTitle.setFontScale(1.5f);
        Label onlinePlayersTitle = new Label("Online Players", skin);
        onlinePlayersTitle.setFontScale(1.5f);

        Table lobbyTable = new Table();
        lobbyTable.top();
        lobbyTable.pad(10);
        lobbyTable.defaults().pad(5);

        selectedRow = null;

        ArrayList <Lobby> lobbies = getLobbies();
        for (Lobby l: lobbies) {
            Label nameLabel = new Label("Name: " + l.getName(), skin);
            Label idLabel = new Label("ID: " + l.getID(), skin);

            String membersString = "";
            for (User user: l.getUsers())
                membersString += user.getNickname() + ", ";
            membersString = membersString.substring(0, membersString.length() - 2);
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
                        System.out.println("Double-clicked on: " + nameLabel.getText());
                    }
                }
            });

            lobbyTable.add(row).expandX().fillX().row();

            // Optional: separator
            Image separator = new Image(skin.newDrawable("white", Color.DARK_GRAY));
            lobbyTable.add(separator).height(1).expandX().fillX().padTop(5).padBottom(5).row();
        }

        ScrollPane lobbyScrollPane = new ScrollPane(lobbyTable, skin);
        lobbyScrollPane.setFadeScrollBars(false);

        Table onlinePlayersTable = new Table();
        onlinePlayersTable.top();
        onlinePlayersTable.pad(10);
        onlinePlayersTable.defaults().pad(5);

        ArrayList <User> users = getUsers();
        for (User user: users) {
            Label nameLabel = new Label(user.getNickname(), skin);
            onlinePlayersTable.add(nameLabel).expandX().fillX().row();
        }

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

        Table mainTable = new Table();
//        mainTable.setFillParent(true);
//        mainTable.center();
        mainTable.add(lobbiesTitle).padLeft(50).align(Align.left);
        mainTable.add(onlinePlayersTitle).row();

        mainTable.add(lobbyScrollPane).padTop(sh / 40).padRight(sw / 20).width((int) (sw * (2. / 3 - 1. / 20))).height(sh * 3 / 5);
        mainTable.add(onlineScrollPane).padTop(sh / 40).width(sw / 4).height(sh * 3 / 5);

        mainTable.row();

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

    private ArrayList <Lobby> getLobbies() {
        /*
        ArrayList <Lobby> result = new ArrayList <>();
        User u1 = new User(null, "1234", null, "Parsa", null, null, null, false);
        User u2 = new User(null, "1234", null, "Parsa2", null, null, null, false);
        User u3 = new User(null, "1234", null, "Parsa3", null, null, null, false);
        User u4 = new User(null, "1234", null, "Parsa4", null, null, null, false);
        Lobby l1 = new Lobby("lobby 1", 5125632, null, false, false);
        l1.addUser(u1);
        l1.addUser(u2);
        l1.addUser(u3);
        l1.addUser(u4);
        Lobby l2 = new Lobby("lobby 2", 1895819, "pass", true, false);
        l2.addUser(u1);
        Lobby l3 = new Lobby("lobby 3", 7848952, null, false, false);
        l3.addUser(u4);
        Lobby l4 = new Lobby("lobby 4", 9125283, null, false, true);
        l4.addUser(u3);
        Lobby l5 = new Lobby("lobby 5", 7621053, "1234", true, false);
        l5.addUser(u2);
        result.add(l1);
        result.add(l2);
        result.add(l3);
        result.add(l4);
        result.add(l5);
        return result;
        */
        return null;
    }

    private ArrayList <User> getUsers() {
        ArrayList <User> result = new ArrayList <>();
        for (int i = 0; i < 20; i++) {
            result.add(new User(null, "1234", null, "player " + i, null, null, null, false));
        }
        return result;
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