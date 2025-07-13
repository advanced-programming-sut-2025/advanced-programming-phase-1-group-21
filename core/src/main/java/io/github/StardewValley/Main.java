package io.github.StardewValley;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.StardewValley.models.asset.Assets;
import io.github.StardewValley.views.menu.GUI.PreMenuScreen;
import io.github.StardewValley.views.menu.GUI.RegisterScreen;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.LinkedList;
import java.util.List;

public class Main extends Game {
    private final LinkedList <Stage> layers = new LinkedList<>();


    private static Main main;

    public Main() {
        super();
        main = this;
    }

    public static Main getInstance() {
        return main;
    }

    @Override
    public void create() {
        Assets.load();
        Assets.finishLoading();
        setScreen(new PreMenuScreen());
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
        System.err.println("Switching to " + screen.getClass().getSimpleName());
        //if (this.screen != null)
        //    this.screen.dispose();

        this.screen = screen;
        //if (this.screen != null) {
        //    this.screen.show();
        //    this.screen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //}
    }
}

