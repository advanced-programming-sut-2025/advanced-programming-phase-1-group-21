package io.github.StardewValley.views.menu.GUI;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import models.game.NPC;

public class NPCResponseWindow extends Window {

    public NPCResponseWindow(GameScreen screen , Skin skin ,String message , int x , int y) {
        super("", skin);

        this.setModal(true);
        this.setMovable(false);
        this.setResizable(false);
        this.setKeepWithinStage(false);

        Pixmap bgPixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        bgPixmap.setColor(0.1f, 0.1f, 0.1f, 0.85f);
        bgPixmap.fill();
        Drawable bgDrawable = new TextureRegionDrawable(new Texture(bgPixmap));
        bgPixmap.dispose();

        com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle style = new com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle(
                skin.getFont("default-font"),
                com.badlogic.gdx.graphics.Color.WHITE,
                bgDrawable
        );
        this.setStyle(style);

        Table content = new Table();
        content.defaults().pad(10).left();

        Label messageLabel = new Label(message , skin);
        messageLabel.setColor(Color.GREEN);

        content.add(messageLabel);

        this.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ESCAPE) {
                    screen.closePlayerInfo();
                    return true;
                }
                return false;
            }
        });

        this.add(content).pad(10);
        this.pack();


        this.setPosition(x, y);

        this.addAction(Actions.sequence(
                Actions.alpha(0),
                Actions.scaleTo(0.8f, 0.8f),
                Actions.parallel(
                        Actions.fadeIn(0.2f),
                        Actions.scaleTo(1f, 1f, 0.2f)
                )
        ));
        this.toFront();
    }
}
