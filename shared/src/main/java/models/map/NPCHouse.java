package models.map;

import com.badlogic.gdx.graphics.g2d.Sprite;
import models.game.NPC;
import models.sprite.GameSprite;
import models.time.Date;

public class NPCHouse extends Building implements Placable {

    private NPC npc;
    public NPCHouse() {}

    public NPCHouse(NPC npc) {
        this.npc = npc;
        this.map = (new MapBuilder()).buildNPCHouse(npc);
        String texture = "Textures/Buildings/" + npc.getName() + "House.png";
        sprite = new GameSprite(texture);
        sprite.setSize(5 * 30 , 5 * 30);
    }

    @Override
    public TileType getTileType() {
        return TileType.NPC;
    }

    @Override
    public String getFullName() {
        return npc.getName() + "'s House";
    }

    @Override
    public boolean canEnter(Date date) {
        return true;
    }

    @Override
    public String getSprite() {
        return "O";
    }

    @Override
    public String getTexture() {
        return null;
    }

    @Override
    public Sprite spriteGetter() {
        return null;
    }
}