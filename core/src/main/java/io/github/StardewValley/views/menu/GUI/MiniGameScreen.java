package io.github.StardewValley.views.menu.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.github.czyzby.kiwi.util.gdx.AbstractApplicationListener;
import data.FishData;
import io.github.StardewValley.App;
import io.github.StardewValley.Main;
import io.github.StardewValley.asset.Assets;
import io.github.StardewValley.controllers.GameController;
import models.animal.FishGame;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class MiniGameScreen implements Screen , InputProcessor {
    private Stage stage;
    private final Main game;
    private Texture lakeTexture = Assets.getLakeTexture();
    private Sprite lakeSprite = new Sprite(lakeTexture);
    private Skin skin = Assets.getSkin();
    private boolean snoarIsOn = false;
    private Label fishName;
    private FishGame fish;
    private float greenX = 200;
    private float greenY = 1022;
    private Texture greenTexture;
    private Texture healthTexture;
    private float healthHeight = 0;
    private Random random = new Random(System.currentTimeMillis());
    private ArrayList<FishData> fishDatas = FishData.getFishes();
    private GameController controller = App.getInstance().currentPlayerController;
    private Label perfectNotification = new Label("", skin);


    {
        lakeSprite.setX(200);
        lakeSprite.setY(13);
    }

    public MiniGameScreen() {
        game = Main.getInstance();
        Gdx.input.setInputProcessor(this);
        int index = random.nextInt()%fishDatas.size();
        if(index <0)
            index += fishDatas.size();
        fish = new FishGame(fishDatas.get(index));

        fishName = new Label(fishDatas.get(index).getName() , skin);

        stage = new Stage(new ScreenViewport());
        perfectNotification.setColor(Color.RED);
        stage.addActor(perfectNotification);
        perfectNotification.setX(5);
        perfectNotification.setY(500);
        Gdx.input.setInputProcessor(this);
        createGreenTexture();
    }

    private void createGreenTexture() {
        Pixmap pm = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pm.setColor(Color.GREEN);
        pm.fill();
        greenTexture = new Texture(pm);
        healthTexture = new Texture(pm);
        pm.dispose();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.getBatch().begin();
        lakeSprite.draw(game.getBatch());
        fish.getSprite().draw(game.getBatch());
        if(snoarIsOn){
            fishName.setX(fish.getSprite().getX() - 50);
            fishName.setY(fish.getSprite().getY() + 100);
        }
        fish.move();

        game.getBatch().setColor(Color.GREEN);
        game.getBatch().draw(greenTexture, greenX, greenY, 300, 20);
        game.getBatch().draw(healthTexture , 70 , 100 , 30 , healthHeight);
        fish.handleHealth(greenX);
        game.getBatch().setColor(Color.WHITE);
        healthHeight = 100 - fish.getHealth();
        inputHandler();

        fishLifeHandler();

        game.getBatch().end();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.ESCAPE)
            Main.getInstance().setScreen(new GameScreen());
        if(keycode == Input.Keys.S){
            snoarIsOn = !snoarIsOn;
            if(snoarIsOn)
                stage.addActor(fishName);
            else
                stage.clear();
        }
        return false;
    }

    private void inputHandler(){
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            greenX = Math.max(200 , greenX - (float) 2);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            greenX = Math.min(greenX + (float) 2 , 1436);
        }
    }

    private void fishLifeHandler(){
        if(fish.getHealth() == 0){
            controller.addFishToInventory(fishName.getText().toString() , fish.isPerfect());
            if(fish.isPerfect())
                perfectNotification.setText("PERFECT\n" + fishName.getText() + "\n" + "GOLD");
            else
                perfectNotification.setText("NOT PERFECT\n" + fishName.getText() + "\n" + "COPPER");

            int index = random.nextInt()%fishDatas.size();
            if(index <0)
                index += fishDatas.size();
            fish = new FishGame(fishDatas.get(index));
            fishName = new Label(fishDatas.get(index).getName() , skin);
        }
        else if(fish.getSprite().getX() < 200 || fish.getSprite().getX() > 1636){
            int index = random.nextInt()%fishDatas.size();
            if(index <0)
                index += fishDatas.size();
            fish = new FishGame(fishDatas.get(index));
            fishName = new Label(fishDatas.get(index).getName() , skin);
        }
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }


}
