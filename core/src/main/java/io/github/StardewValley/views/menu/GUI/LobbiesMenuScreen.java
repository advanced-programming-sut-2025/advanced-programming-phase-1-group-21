package io.github.StardewValley.views.menu.GUI;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
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

        Skin skin = Assets.getSkin();
        Texture backgroundTexture = Assets.getMenuBackground();

        Image backgroundImage = new Image(new TextureRegionDrawable(new TextureRegion(backgroundTexture)));
        backgroundImage.setFillParent(true);
        stage.addActor(backgroundImage);

        Label lobbiesTitle = new Label("Lobbies", skin);
        lobbiesTitle.setFontScale(1.5f);
        Label onlinePlayersTitle = new Label("Online Players", skin);
        onlinePlayersTitle.setFontScale(1.5f);

//        Table table = new Table();
//        table.setFillParent(true);
//        table.center();
//        stage.addActor(table);
//
//        Label title = new Label("It's going to be Lobbies menu", skin);
//        title.setFontScale(1.5f);
//        table.add(title).colspan(2).padBottom(30).row();

        // Profile Button
//        TextButton profileButton = new TextButton("Profile", skin);
//        profileButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
////                game.setScreen(new LoginScreen());
//            }
//        });
//
//        // Setting Button
//        TextButton settingButton = new TextButton("Setting", skin);
//        settingButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
////                game.setScreen(new RegisterScreen());
//            }
//        });
//
//        // Lobbies Button
//        TextButton lobbiesButton = new TextButton("Lobbies", skin);
//        lobbiesButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
////                game.setScreen(new RegisterScreen());
//            }
//        });
//
//        // Add buttons to layout
//        table.add(profileButton).width(200).height(50).pad(10).row();
//        table.add(settingButton).width(200).height(50).pad(10).row();
//        table.add(lobbiesButton).width(200).height(50).pad(10).row();

        Table itemTable = new Table();
        itemTable.top();
        itemTable.pad(10);
        itemTable.defaults().pad(5);

        // Sample item data: name and price
        String[][] items = {
                {"Apple", "1000"},
                {"Banana", "2000"},
                {"Carrot", "1500"},
                {"Meat", "5000"},
                {"Bread", "800"},
                {"Milk", "2500"},
                {"Egg", "4000"},
                {"Yogurt", "3500"},
                {"Meat", "5000"},
                {"Bread", "800"},
                {"Milk", "2500"},
                {"Egg", "4000"},
                {"Yogurt", "3500"},
                {"Meat", "5000"},
                {"Bread", "800"},
                {"Milk", "2500"},
                {"Egg", "4000"},
                {"Yogurt", "3500"},
                {"Meat", "5000"},
                {"Bread", "800"},
                {"Milk", "2500"},
                {"Egg", "4000"},
                {"Yogurt", "3500"},
                {"Meat", "5000"},
                {"Bread", "800"},
                {"Milk", "2500"},
                {"Egg", "4000"},
                {"Yogurt", "3500"},
                {"Water", "1000"}
        };

        selectedRow = null;

        for (String[] item : items) {
            Label nameLabel = new Label("Name: " + item[0], skin);
            Label priceLabel = new Label("Price: " + item[1] + " Toman", skin);

            Table row = new Table();
            row.left();
            row.add(nameLabel).padRight(20);
            row.add(priceLabel);
            row.pad(5);
            row.setBackground(skin.newDrawable("white", Color.CLEAR));

            // Add click listener to select one row at a time
            row.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (selectedRow != null) {
                        selectedRow.setBackground(skin.newDrawable("white", Color.CLEAR));
                    }

                    row.setBackground(skin.newDrawable("white", new Color(Color.LIGHT_GRAY.r, Color.LIGHT_GRAY.g, Color.LIGHT_GRAY.b, 0.4f)));
                    selectedRow = row;

                    if (getTapCount() == 2) {
                        System.out.println("Double-clicked on: " + nameLabel.getText());
                    }
                }
            });

            itemTable.add(row).expandX().fillX().row();

            // Optional: separator
            Image separator = new Image(skin.newDrawable("white", Color.DARK_GRAY));
            itemTable.add(separator).height(1).expandX().fillX().padTop(5).padBottom(5).row();
        }

        ScrollPane scrollPane = new ScrollPane(itemTable, skin);
        scrollPane.setFadeScrollBars(false);

        Table mainTable = new Table();
        mainTable.setFillParent(true);
//        mainTable.center();
        mainTable.add(lobbiesTitle);
        mainTable.add(onlinePlayersTitle).row();

        mainTable.add(scrollPane).width(stage.getWidth() / 2).height(stage.getHeight() / 2).fill();

        stage.addActor(mainTable);


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