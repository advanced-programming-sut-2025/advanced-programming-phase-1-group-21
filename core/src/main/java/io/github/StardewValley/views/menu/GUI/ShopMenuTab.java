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
    private TextField amount;
    private TextButton buy;


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
        menuScrollPane.setScrollingDisabled(true, false);
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

        amount = new TextField("" , skin);
        amount.setX(1200);
        amount.setY(170);

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

        buy = new TextButton("Buy" , skin);
        buy.setX(1350);
        buy.setY(170);
        buy.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buy();
                createUI();
                draw();
            }
        });


        stage.addActor(exitButton);
        stage.addActor(menuScrollPane);
        stage.addActor(itemToBy);
        stage.addActor(allProducts);
        stage.addActor(availableProducts);
        stage.addActor(amount);
        stage.addActor(buy);
    }

    private void buy(){

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
