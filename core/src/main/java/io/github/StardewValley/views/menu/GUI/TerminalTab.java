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
import io.github.StardewValley.views.menu.CLI.GameTerminalView;

public class TerminalTab {
    private Stage stage;
    private Skin skin;
    private GameScreen gameScreen;
    private GameTerminalView resultGetter = new GameTerminalView();
    private TextButton submit;
    private TextButton exitButton;
    private TextField input;
    private Label message;


    public TerminalTab(GameScreen gameScreen, Skin skin) {
        stage = new Stage(new ScreenViewport());
        this.gameScreen = gameScreen;
        this.skin = skin;
        resultGetter.setGameController(gameScreen.getController());
        message = new Label("" , skin);
        createUI();
    }

    void createUI() {
        stage.clear();
        setDarkBackground();

        exitButton = new TextButton("X", skin);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameScreen.onTerminalClosed();
            }
        });
        exitButton.setX(1670);
        exitButton.setY(850);


        input = new TextField("" , skin);
        input.setX(750);
        input.setY(600);
        input.setSize(400 , 40);


        message.setX(600);
        message.setY(850);
        message.setColor(Color.RED);

        submit = new TextButton("Submit" , skin);
        submit.setX(1200);
        submit.setY(600);
        submit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                getResult();
                createUI();
                draw();
            }
        });

        stage.addActor(input);
        stage.addActor(message);
        stage.addActor(submit);
        stage.addActor(exitButton);
    }

    private void getResult(){
        String command = input.getText();
        //resultGetter.Result(command);
        message.setText(resultGetter.ResultText(command));

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
