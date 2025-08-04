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
import models.game.Player;
import models.result.Result;

import java.util.regex.Pattern;

public class OtherPlayerInfo extends Window{
    TextButton hug , gift , askMarriage, trade , talk , flower , yes , no;
    TextField inputAmount;
    Label name , friendship , message;
    Player otherPlayer;
    Player me;
    Skin skin;
    GameScreen gameScreen;

    public OtherPlayerInfo(GameScreen screen , Skin skin ,Player player , Player me) {
        super("Animal Menu", skin);
        this.otherPlayer = player;
        this.skin = skin;
        this.gameScreen = screen;
        this.me = me;

        this.setModal(true);
        this.setMovable(false);
        this.setResizable(false);
        this.setKeepWithinStage(false);

        Pixmap bgPixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        bgPixmap.setColor(0.1f, 0.1f, 0.1f, 0.85f);
        bgPixmap.fill();
        Drawable bgDrawable = new TextureRegionDrawable(new Texture(bgPixmap));
        bgPixmap.dispose();

        Window.WindowStyle style = new Window.WindowStyle(
                skin.getFont("default-font"),
                Color.WHITE,
                bgDrawable
        );
        this.setStyle(style);

        Table content = new Table();
        content.defaults().pad(10).left();
        name = new Label("name : " + player.getUser().getUsername() , skin);

        friendship = new Label("friendship : " + gameScreen.getController().showFriendship(player.getUser().getUsername())
                 , skin);

        inputAmount = new TextField("Gift Amount" , skin);

        hug = new TextButton("Hug" , skin);
        hug.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hug();
            }
        });

        talk = new TextButton("Talk" , skin);
        talk.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                talk();
            }
        });

        gift = new TextButton("Gift" , skin);
        gift.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sendGift();
            }
        });

        askMarriage = new TextButton("Ask Marriage" , skin);
        askMarriage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                askMarriage();
            }
        });

        trade = new TextButton("Trade" , skin);
        trade.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameScreen.closePlayerInfo();
            }
        });

        flower = new TextButton("Flower" , skin);
        flower.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                flower();
            }
        });

        message = new Label("" , skin);
        message.setColor(Color.RED);


        content.add(name).row();
        content.add(friendship).row();
        content.add(talk).row();
        content.add(trade).row();
        content.add(inputAmount);
        content.add(gift).row();
        content.add(hug).row();
        content.add(flower).row();
        content.add(askMarriage).row();
        if(otherPlayer.getUser().getUsername().equals(me.getSuitor())){
            yes = new TextButton("Yes" , skin);
            yes.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    gameScreen.getController().marriageResponse("yes");
                    gameScreen.closePlayerInfo();
                }
            });

            no = new TextButton("No" ,skin);
            no.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    gameScreen.getController().marriageResponse("no");
                    gameScreen.closePlayerInfo();
                }
            });
            content.add(yes);
            content.add(no);
        }
        content.add(message);

        this.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.ESCAPE) {
                    gameScreen.closePlayerInfo();
                    return true;
                }
                return false;
            }
        });

        this.add(content).pad(10);
        this.pack();

        float playerX = player.getSprite().getX();
        float playerY = player.getSprite().getY();

        this.setPosition(playerX, playerY);

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

    private void talk(){
        if(gameScreen.getController().talk(otherPlayer.getUser().getUsername()).isError()) {
            message.setText(gameScreen.getController().talk(otherPlayer.getUser().getUsername()).getMessage());
            return;
        }
        gameScreen.closePlayerInfo();
        gameScreen.setTalkScreen(new TalkScreen(me , otherPlayer , gameScreen , skin));
    }

    private void flower(){
        Result<?> result = gameScreen.getController().sendFlower(otherPlayer.getUser().getUsername());
        if(result.isError()){
            message.setText(result.getMessage());
            return;
        }
        message.setText("Flower sent");
    }

    private void hug(){
        Result<?> result = gameScreen.getController().hug(otherPlayer.getUser().getUsername());
        if(result.isError()){
            message.setText(result.getMessage());
            return;
        }
        message.setText("Hugging");
    }

    private void askMarriage(){
        Result<?> result = gameScreen.getController().askMarriage(otherPlayer.getUser().getUsername());
        if(result.isError()){
            message.setText(result.getMessage());
            return;
        }
        message.setText("Payam e risky");
    }

    private void sendGift(){
        if(me.getItemInHand() == null){
            message.setText("You should pick up your gift");
            return;
        }
        String giftName = me.getItemInHand().getName();
        if(inputAmount.getText() == null || !Pattern.compile("\\d+").matcher(inputAmount.getText()).matches()){
            message.setText("Invalid Amount");
            return;
        }
        int amount = Integer.parseInt(inputAmount.getText());
        Result<?> result = gameScreen.getController().sendGift(otherPlayer.getUser().getUsername() , giftName , amount);
        if(result.isError()){
            message.setText(result.getMessage());
            return;
        }
        message.setText("Gift Sent");
    }

    private void answerSuitor(String response){

    }
}
