package io.github.StardewValley;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.LinkedList;

public class Main extends Game {
    private final LinkedList <Stage> layers = new LinkedList<>();

    @Override
    public void create() {
        setScreen(new FirstScreen());
//        try {
//            AppView appView = new AppView();
//            appView.resetStatic();
//            App.reset();
//            appView.run();
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    public void addStage(Stage stage) {
        layers.add(stage);
    }

    public void removeStage() {
        if (!layers.isEmpty())
            layers.removeLast();
        else {
            System.err.println("No stage to remove");
        }
    }

    @Override
    public void render() {
        ScreenUtils.clear(Color.BLACK);
        super.render();
        for (Stage stage : layers)
            stage.draw();
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void setScreen (Screen screen) {
        if (this.screen != null)
            this.screen.dispose();

        this.screen = screen;
        if (this.screen != null) {
            this.screen.show();
            this.screen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
    }
}
