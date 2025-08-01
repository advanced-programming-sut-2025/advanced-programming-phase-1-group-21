package io.github.StardewValley.views.menu.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import models.animal.Animal;

public class AnimalInfoWindow extends Window {
    TextButton pet , shepherd , feed , collectProduce , sell;
    Label name , friendship , produces , isFeed , isOut , isPet;
    Animal animal;
    Skin skin;
    Stage stage;
    GameScreen gameScreen;

    public AnimalInfoWindow(GameScreen screen , Skin skin ,Animal animal) {
        super("Animal Menu", skin);
        this.animal = animal;
        this.skin = skin;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        this.gameScreen = screen;

        this.setModal(true);
        this.setMovable(false);
        this.setResizable(false);
        this.setKeepWithinStage(false);

        Pixmap bgPixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        bgPixmap.setColor(0.1f, 0.1f, 0.1f, 0.85f);
        bgPixmap.fill();
        Drawable bgDrawable = new TextureRegionDrawable(new Texture(bgPixmap));
        bgPixmap.dispose();

        WindowStyle style = new WindowStyle(
                skin.getFont("default-font"),
                Color.WHITE,
                bgDrawable
        );
        this.setStyle(style);

        Table content = new Table();
        content.defaults().pad(10).left();
        name = new Label("name : " + animal.getName() , skin);

        friendship = new Label("friendship : " + animal.getFriendship() , skin);

        produces = new Label("produces : " + animal.getTodayProduct() , skin);

        isFeed = new Label("Is Feed : " + animal.isFeedToday() , skin);

        isOut = new Label("Is Out : " + animal.isOut() , skin);

        isPet = new Label("Is Pet : " + animal.isTodayPet() , skin);

        pet = new TextButton("Pet" , skin);
        pet.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameScreen.closeAnimalInfo();
                gameScreen.getController().pet(animal.getName());
            }
        });

        shepherd = new TextButton("Shepherd" , skin);
        shepherd.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameScreen.closeAnimalInfo();

            }
        });

        feed = new TextButton("Feed" , skin);
        feed.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameScreen.closeAnimalInfo();
                gameScreen.getController().feedHay(animal.getName());
            }
        });

        collectProduce = new TextButton("Collect Produce" , skin);
        collectProduce.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameScreen.closeAnimalInfo();
                gameScreen.getController().collectProducts(animal.getName());
            }
        });

        sell = new TextButton("Sell" , skin);
        sell.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameScreen.closeAnimalInfo();
                gameScreen.getController().sellAnimal(animal.getName());
            }
        });

        content.add(name).row();
        content.add(friendship).row();
        content.add(produces).row();
        content.add(isPet).row();
        content.add(isOut).row();
        content.add(isFeed).row();
        content.add(pet).row();
        content.add(shepherd).row();
        content.add(feed).row();
        content.add(collectProduce).row();
        content.add(sell).row();

        this.add(content).pad(10);
        this.pack();

        float animalX = animal.getSpriteX();
        float animalY = animal.getSpriteY();

        this.setPosition(animalX, animalY);

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
