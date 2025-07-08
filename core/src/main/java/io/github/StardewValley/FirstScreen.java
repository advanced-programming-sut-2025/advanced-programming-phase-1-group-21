package io.github.StardewValley;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.kotcrab.vis.ui.VisUI;
import com.kotcrab.vis.ui.widget.VisTable;
import io.github.StardewValley.models.App;
import io.github.StardewValley.views.menu.AppView;

/** First screen of the application. Displayed after the application is created. */
public class FirstScreen implements Screen {
    SpriteBatch batch = new SpriteBatch();
    TextButton textButton;
    private Stage stage;


    @Override
    public void show() {
        // Initialize the VisUI skin
//        VisUI.load();
        VisUI.load(VisUI.SkinScale.X2);


        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        VisTable table = new VisTable();
        table.setFillParent(true);
        stage.addActor(table);

        Label label = new Label("Welcome to Stardew-like Game!", VisUI.getSkin());
        TextButton button = new TextButton("Start Game", VisUI.getSkin());

        table.center();
        table.add(label).pad(10);
        table.row();
        table.add(button).pad(10);

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("start");
                AppView appView = new AppView();
                appView.resetStatic();
                App.reset();
                try {
                    appView.run();
                }
                catch (Exception e) {

                }
            }
        });

//        VisUI.load();
//
//        // Prepare your screen here.
//        textButton = new TextButton("FASF", VisUI.getSkin());
    }

    @Override
    public void render(float delta) {
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        // Draw your screen here. "delta" is the time since last render in seconds.
//        batch.begin();
//        textButton.draw(batch, delta);
//        batch.end();

    }

    @Override
    public void resize(int width, int height) {
        // If the window is minimized on a desktop (LWJGL3) platform, width and height are 0, which causes problems.
        // In that case, we don't resize anything, and wait for the window to be a normal size before updating.
        if(width <= 0 || height <= 0) return;

        // Resize your screen here. The parameters represent the new window size.
    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void hide() {
        // This method is called when another screen replaces this one.
    }

    @Override
    public void dispose() {
        // Destroy screen's assets here.

        stage.dispose();
        VisUI.dispose(); // Clean up skin resources
    }
}
