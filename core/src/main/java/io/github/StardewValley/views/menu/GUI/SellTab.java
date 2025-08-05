package io.github.StardewValley.views.menu.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import models.Item.Item;
import models.game.Player;
import models.map.Coord;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SellTab {
    private Stage stage;
    private Skin skin;
    private GameScreen gameScreen;
    private TextButton exitButton;
    private ArrayList<Label> inventory = new ArrayList<>();
    private TextField input;
    private TextButton sell;
    private Label message;
    private Table scrollTable;
    private ScrollPane menuScrollPane;
    private Label itemToSell;
    private Player player;

    public SellTab(GameScreen gameScreen, Skin skin , Player player) {
        stage = new Stage(new ScreenViewport());
        this.gameScreen = gameScreen;
        this.skin = skin;
        this.itemToSell = new Label("" , skin);
        message = new Label("" , skin);
        this.player = player;
        createUI();
    }

    void createUI() {
        stage.clear();
        setDarkBackground();
        inventory.clear();
        setInventory();

        scrollTable = new Table();
        scrollTable.defaults().left().top().expandX().fillX();

        for(Label label : inventory){
            label.setWrap(true);
            scrollTable.add(label).left().expandX().fillX().row();
            label.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    itemToSell(getName(label.getText().toString()));
                }
            });
        }

        menuScrollPane = new ScrollPane(scrollTable, skin);
        menuScrollPane.setScrollingDisabled(false, false);
        menuScrollPane.setFadeScrollBars(true);
        menuScrollPane.setSize(1200, 600);
        menuScrollPane.setPosition(360, 227);

        exitButton = new TextButton("X", skin);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameScreen.onSellTabClosed();
            }
        });
        exitButton.setX(1670);
        exitButton.setY(850);

        input = new TextField("" , skin);
        input.setX(1200);
        input.setY(170);


        itemToSell.setX(900);
        itemToSell.setY(190);

        sell = new TextButton("Sell" , skin);
        sell.setX(1380);
        sell.setY(170);
        sell.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sellItem();
                createUI();
                draw();
            }
        });

        message.setX(900);
        message.setY(850);
        message.setColor(Color.RED);


        stage.addActor(exitButton);
        stage.addActor(menuScrollPane);
        stage.addActor(itemToSell);
        stage.addActor(input);
        stage.addActor(sell);
        stage.addActor(message);
    }

    private void sellItem(){
        if(input.getText().isEmpty()){
            message.setText("invalid amount");
            return;
        }
        int amount = Integer.parseInt(input.getText());
        String itemName = itemToSell.getText().toString();
        message.setText(gameScreen.getController().sell(itemName , amount).getMessage());
    }

    private void setInventory(){
        for(Item item : player.getInventory().getItems()){
            inventory.add(new Label("Item Name : " + item.getName() + "   Amount : " + item.getAmount() , skin));
        }
    }

    public void show() {
        createUI();
        Gdx.input.setInputProcessor(stage);
    }

    public void draw() {
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
//			System.out.println("Left mouse button clicked!, coor: " + Gdx.input.getX() + " " + Gdx.input.getY());
        }
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    public void dispose() {
    }

    private void setDarkBackground() {
        Pixmap bgPixmap = new Pixmap((int) stage.getWidth(), (int) stage.getHeight(), Pixmap.Format.RGBA8888);
        bgPixmap.setColor(0, 0, 0, 0.5f);
        bgPixmap.fill();
        Texture bgTexture = new Texture(bgPixmap);
        bgPixmap.dispose();
        stage.addActor(new Image(bgTexture));
    }


    private void itemToSell(String name){
        this.itemToSell.setText(name);
        createUI();
        draw();
    }

    private String getName(String information){
        String regex = "Item Name\\s:\\s(?<name>\\S+)\\s.*";
        Matcher matcher = Pattern.compile(regex).matcher(information);
        if(!matcher.matches())
            return null;
        matcher.matches();
        return matcher.group("name");
    }
}
