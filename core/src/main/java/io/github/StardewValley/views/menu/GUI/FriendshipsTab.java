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
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.StardewValley.views.menu.CLI.GameTerminalView;

import java.util.ArrayList;

public class FriendshipsTab {
    private Stage stage;
    private Skin skin;
    private GameScreen gameScreen;
    private TextButton exitButton;


    public FriendshipsTab(GameScreen gameScreen, Skin skin) {
        stage = new Stage(new ScreenViewport());
        this.gameScreen = gameScreen;
        this.skin = skin;
        createUI();
    }

    void createUI() {
        stage.clear();
        setDarkBackground();

        exitButton = new TextButton("X", skin);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameScreen.onFriendshipScreenClosed();
            }
        });
        exitButton.setX(1670);
        exitButton.setY(850);

        ArrayList<String> printedFriendships = gameScreen.getController().showFriendships().getData();
        for(int i = 0 ; i < printedFriendships.size() ; i++){
            String string = printedFriendships.get(i);
            Label label = new Label(string , skin);
            label.setPosition((float) Gdx.graphics.getWidth() /2 - label.getWidth()/2 , (float)
                    Gdx.graphics.getHeight() /2 + ((float) printedFriendships.size() /2 - i)*50);
            stage.addActor(label);
        }

        stage.addActor(exitButton);
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
}
