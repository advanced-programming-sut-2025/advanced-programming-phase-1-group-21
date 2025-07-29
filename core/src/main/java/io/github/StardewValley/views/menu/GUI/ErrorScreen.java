package io.github.StardewValley.views.menu.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import models.result.Error;

public class ErrorScreen implements Screen {

    private SpriteBatch batch;
    private BitmapFont font;
    private OrthographicCamera camera;
    private String errorMessage;

    public ErrorScreen(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ErrorScreen(Error error) {
        this.errorMessage = error.getMessage();
    }

                       @Override
    public void show() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.getData().setScale(4);
        font.setColor(Color.RED);
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.setToOrtho(false);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        float x = (Gdx.graphics.getWidth() - font.getRegion().getRegionWidth()) / 2f;
        float y = Gdx.graphics.getHeight() / 2f;
        font.draw(batch, errorMessage, x, y);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
    }

    @Override public void pause() { }
    @Override public void resume() { }
    @Override public void hide() { }
    @Override public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
