package io.github.StardewValley.views.console;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.StardewValley.Main;
import io.github.StardewValley.models.asset.Assets;
import io.github.StardewValley.views.menu.CLI.AppView;
import io.github.StardewValley.views.menu.GUI.PreMenuScreen;

public class ConsoleScreen implements Screen {
    private Stage stage;
    private Skin skin;
    private TextField inputField;
    private Label outputLabel;

    public ConsoleScreen() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin = Assets.getSkin();

        outputLabel = new Label("", skin);

        inputField = new TextField("", skin);
        inputField.setMessageText("Enter command...");

        inputField.setTextFieldListener((textField, c) -> {
            if (c == '\r' || c == '\n') {
                String command = inputField.getText();
                if (command.equals("exit"))
                    Main.getInstance().setScreen(new PreMenuScreen());
                inputField.setText("");
                outputLabel.setText(executeCommand(command));
            }
        });

        Table table = new Table();
        table.setFillParent(true);
        table.pad(20);
        table.top();

        table.add(inputField).left().expandX().fillX().row();

        table.add(outputLabel).left().expandX().fillX().row();

        stage.addActor(table);
    }

    private String executeCommand(String cmd) {
        return AppView.getResult(cmd.trim());
    }

    @Override public void show() { Gdx.input.setInputProcessor(stage); }
    @Override public void render(float delta) {
        Gdx.gl.glClearColor(0.05f, 0.05f, 0.05f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }
    @Override public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}