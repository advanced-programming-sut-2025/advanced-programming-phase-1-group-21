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
import models.map.Coord;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShopMenuTab {
    private Stage stage;
    private Skin skin;
    private GameScreen gameScreen;
    private TextButton exitButton;
    private ArrayList<Label> products = new ArrayList<>();
    private TextButton allProducts;
    private TextButton availableProducts;
    private boolean isAll = true;
    private TextField input;
    private TextButton buyItem , buyAnimal , build;
    private Label message;



    private Table scrollTable;
    private ScrollPane menuScrollPane;
    private Label itemToBy;

    public ShopMenuTab(GameScreen gameScreen, Skin skin) {
        stage = new Stage(new ScreenViewport());
        this.gameScreen = gameScreen;
        this.skin = skin;
        this.itemToBy = new Label("" , skin);
        this.allProducts = new TextButton("Show All" , skin);
        this.availableProducts = new TextButton("Show Available" , skin);
        message = new Label("" , skin);
        createUI();
    }

    void createUI() {
        stage.clear();
        setDarkBackground();
        products.clear();
        setProducts();

        scrollTable = new Table();
        scrollTable.defaults().left().top().expandX().fillX();

        for(Label label : products){
            label.setWrap(true);
            scrollTable.add(label).left().expandX().fillX().row();
            label.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    itemToBy(getName(label.getText().toString()));
                    System.out.println(label.getText());
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
                gameScreen.onShopMenuClosed();
            }
        });
        exitButton.setX(1670);
        exitButton.setY(850);

        input = new TextField("" , skin);
        input.setX(1200);
        input.setY(170);

        allProducts = new TextButton("Show All" , skin);
        allProducts.setX(400);
        allProducts.setY(170);
        allProducts.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                isAll = true;
                createUI();
                draw();
            }
        });

        availableProducts = new TextButton("Show Available" , skin);
        availableProducts.setX(600);
        availableProducts.setY(170);
        availableProducts.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                isAll = false;
                createUI();
                draw();
            }
        });

        itemToBy.setX(900);
        itemToBy.setY(190);

        buyItem = new TextButton("Buy Item" , skin);
        buyItem.setX(1380);
        buyItem.setY(170);
        buyItem.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buyItem();
                createUI();
                draw();
            }
        });

        buyAnimal = new TextButton("Buy Animal" , skin);
        buyAnimal.setX(1380);
        buyAnimal.setY(120);
        buyAnimal.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buyAnimal();
                createUI();
                draw();
            }
        });

        build = new TextButton("Build" , skin);
        build.setX(1380);
        build.setY(70);
        build.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                build();
                createUI();
                draw();
            }
        });

        message.setX(900);
        message.setY(850);
        message.setColor(Color.RED);


        stage.addActor(exitButton);
        stage.addActor(menuScrollPane);
        stage.addActor(itemToBy);
        stage.addActor(allProducts);
        stage.addActor(availableProducts);
        stage.addActor(input);
        stage.addActor(buyItem);
        stage.addActor(buyAnimal);
        stage.addActor(build);
        stage.addActor(message);
    }

    private void buyItem(){
        if(input.getText().isEmpty()){
            message.setText("invalid amount");
            return;
        }
        int amount = Integer.parseInt(input.getText());
        String itemName = itemToBy.getText().toString();
        message.setText(gameScreen.getController().purchaseItem(itemName , amount).getMessage());
    }

    private void buyAnimal(){
        String name = input.getText();
        String animalName = itemToBy.getText().toString();
        message.setText(gameScreen.getController().purchaseAnimal(animalName , name).getMessage());
    }

    private void build(){
        String regex = "\\((?<x>\\d+),(?<y>\\d+)\\)";
        Matcher matcher = Pattern.compile(regex).matcher(input.getText());
        if(!matcher.matches()) {
            message.setText("invalid coordinate");
            return;
        }
        matcher.matches();

        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        String buildingName = itemToBy.getText().toString();
        message.setText(gameScreen.getController().purchaseBuilding(buildingName , new Coord(x,y)).getMessage());
    }

    private void setProducts(){
        String[] productsNames;
        String[] all = new String[0];
        if(gameScreen.getController().showAllShopProducts().getMessage() != null)
            all = gameScreen.getController().showAllShopProducts().getMessage().split("\n");
        String[] available = new String[0];
        if(gameScreen.getController().showAvailableShopProducts().getMessage() != null)
            available = gameScreen.getController().showAvailableShopProducts().getMessage().split("\n");
        if(isAll)
            productsNames = all;
        else
            productsNames = available;
        List<String> arrayAvailable = Arrays.asList(available);
        for(String string : productsNames){
            Label label = new Label(string , skin);
            if(isAll){
                if(arrayAvailable.contains(string))
                    label.setColor(Color.GREEN);
                else
                    label.setColor(Color.RED);
            }
            products.add(label);
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


    private void itemToBy(String name){
        this.itemToBy.setText(name);
        createUI();
        draw();
    }

    private String getName(String information){
        String regex = "name\\s:\\s(?<name>.+)";
        Matcher matcher = Pattern.compile(regex).matcher(information);
        if(!matcher.matches())
            return null;
        matcher.matches();
        String withoutName = matcher.group("name");
        String[] nameBlock = withoutName.split("price");
        return nameBlock[0].trim();
    }
}
