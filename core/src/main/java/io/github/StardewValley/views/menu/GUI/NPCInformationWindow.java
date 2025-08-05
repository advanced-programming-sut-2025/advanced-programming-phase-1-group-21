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
import models.game.NPCFriendship;
import models.game.Player;
import models.result.Result;

import java.util.regex.Pattern;

public class NPCInformationWindow extends Window{
    TextButton talk , gift;
    TextField chatBox , inputAmount;
    Label name , friendship , message;
    NPC npc;
    Player me;
    Skin skin;
    GameScreen gameScreen;

    public NPCInformationWindow(GameScreen screen , Skin skin ,NPC npc , Player player) {
        super("NPC Menu", skin);
        this.npc = npc;
        this.skin = skin;
        this.gameScreen = screen;
        me = player;

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
        name = new Label("name : " + npc.getName(), skin);

        content.add(name).row();

        inputAmount = new TextField("", skin);
        inputAmount.setMessageText("amount");

        chatBox = new TextField("" , skin);
        chatBox.setMessageText("talk to npc");
        chatBox.setWidth(200);

        talk = new TextButton("Talk", skin);
        talk.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                talk();
            }
        });

        content.add(chatBox);
        content.add(talk).row();

        gift = new TextButton("Gift", skin);
        gift.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                giftNPC();
            }
        });

        content.add(inputAmount);
        content.add(gift).row();

        NPCFriendship npcFriendship = npc.getFriendshipByPlayer(player);
        for (int i = 0; i < Math.min(npcFriendship.getLevel().getLevel() + 1, 3); i++) {
            String stringBuilder = (i) + ": " +
                    "Request Item: " + npc.getTasks().get(i).getRequestItem() +
                    ": " + npc.getTasks().get(i).getRequestAmount() + " | " +
                    "Reward Item: " + npc.getTasks().get(i).getRewardItem() +
                    ": " + npc.getTasks().get(i).getRewardAmount();
            Label label = new Label(stringBuilder, skin);
            if(!npc.getTasksFlag().get(i)){
                TextButton textButton = new TextButton("Finish Quest" , skin);
                int finalI = i;
                textButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        finishQuest(finalI);
                    }
                });
                content.add(label);
                content.add(textButton).row();
            }
            else{
                label.setText(stringBuilder + " **FINISHED**");
                content.add(label).row();
            }
        }


        message = new Label("", skin);
        message.setColor(Color.RED);


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

        float NPCX = npc.spriteGetter().getX();
        float NPCY = npc.spriteGetter().getY();

        this.setPosition(NPCX, NPCY);

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
        String string = chatBox.getText();
        if(string.isEmpty()){
            message.setText("Type a message");
            return;
        }
        gameScreen.getController().meetNPC(npc.getName() , string);
    }

    private void giftNPC(){
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
        Result<?> result = gameScreen.getController().giftNPC(npc.getName() , giftName, amount);
        if(result.isError()){
            message.setText(result.getMessage());
            return;
        }
        message.setText("Gift Sent");
    }

    private void finishQuest(int i){
        Result<?> result = gameScreen.getController().finishQuest(npc.getName() , i);
        if(result.isError()){
            message.setText(result.getMessage());
            return;
        }
        message.setText("Quest finished successfully");
    }
}
