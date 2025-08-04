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

public class GiftTab {
    private Stage stage;
    private Skin skin;
    private GameScreen gameScreen;
    private TextButton exitButton;
    private ArrayList<Label> gifts = new ArrayList<>();
    private TextButton allGifts;
    private TextButton receivedGifts;
    private boolean isAll = true;
    private TextField input;
    private TextButton rate;
    private Label message;
    private Table scrollTable;
    private ScrollPane menuScrollPane;
    private Label giftToRate;

    public GiftTab(GameScreen gameScreen, Skin skin) {
        stage = new Stage(new ScreenViewport());
        this.gameScreen = gameScreen;
        this.skin = skin;
        this.giftToRate = new Label("" , skin);
        this.allGifts = new TextButton("Show All" , skin);
        this.receivedGifts = new TextButton("Show Receives" , skin);
        message = new Label("" , skin);
        createUI();
    }

    void createUI() {
        stage.clear();
        setDarkBackground();
        gifts.clear();
        setProducts();

        scrollTable = new Table();
        scrollTable.defaults().left().top().expandX().fillX();

        for(Label label : gifts){
            label.setWrap(true);
            scrollTable.add(label).left().expandX().fillX().row();
            label.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if(!isAll)
                        giftToRate(getName(label.getText().toString()));
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
                gameScreen.onGiftMenuClosed();
            }
        });
        exitButton.setX(1670);
        exitButton.setY(850);

        input = new TextField("" , skin);
        input.setX(1200);
        input.setY(170);

        allGifts = new TextButton("Show All" , skin);
        allGifts.setX(400);
        allGifts.setY(170);
        allGifts.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                isAll = true;
                createUI();
                draw();
            }
        });

        receivedGifts = new TextButton("Show Receives" , skin);
        receivedGifts.setX(600);
        receivedGifts.setY(170);
        receivedGifts.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                isAll = false;
                createUI();
                draw();
            }
        });

        giftToRate.setX(900);
        giftToRate.setY(190);

        rate = new TextButton("Rate" , skin);
        rate.setX(1380);
        rate.setY(170);
        rate.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                rateGift();
                createUI();
                draw();
            }
        });

        message.setX(900);
        message.setY(850);
        message.setColor(Color.RED);


        stage.addActor(exitButton);
        stage.addActor(menuScrollPane);
        stage.addActor(giftToRate);
        stage.addActor(allGifts);
        stage.addActor(receivedGifts);
        stage.addActor(input);
        stage.addActor(rate);
        stage.addActor(message);
    }

    private void rateGift(){
        if(input.getText().isEmpty()){
            message.setText("invalid amount");
            return;
        }
        int amount = Integer.parseInt(input.getText());
        String giftNameAndID = giftToRate.getText().toString();
        String giftRegex = "(?<ID>\\d+)\\s+(?<sender>.*)";
        Matcher matcher = Pattern.compile(giftRegex).matcher(giftNameAndID);
        matcher.matches();
        message.setText(gameScreen.getController().giftRate(matcher.group("sender") ,Integer.parseInt(matcher.group("ID"))
                , amount).getMessage());
    }

    private void setProducts(){
        ArrayList<String> output;
        if(isAll)
            output = gameScreen.getController().allGifts().getData();
        else
            output = gameScreen.getController().receivedGiftList().getData();

        for(String string : output){
            Label label = new Label(string , skin);
            gifts.add(label);
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


    private void giftToRate(String name){
        this.giftToRate.setText(name);
        createUI();
        draw();
    }

    private String getName(String information){
        String giftRegex = "GiftID\\s:\\s(?<ID>\\d+)\\s\\|\\sSender\\s:\\s(?<sender>\\S+)\\s\\|\\s.*";
        Matcher matcher = Pattern.compile(giftRegex).matcher(information);
        if(!matcher.matches()){
            return null;
        }
        matcher.matches();
        return matcher.group("ID") + "   " + matcher.group("sender");
    }
}
