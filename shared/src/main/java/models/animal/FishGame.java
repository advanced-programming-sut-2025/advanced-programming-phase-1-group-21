package models.animal;

import Asset.SharedAssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import data.Data;
import data.FishData;

import java.util.Random;

public class FishGame {
    private FishData fishData;
    private String name;
    private Texture texture;
    private Sprite sprite;
    private float x;
    private float y;
    private Random random;
    private FishMoveType fishMoveType;
    private boolean isPerfect = true;
    private float health = 100;

    public FishGame() {}

    public FishGame(FishData fishData) {
        this.fishData = fishData;
        random = new Random(System.currentTimeMillis());
        x = random.nextInt(300 , 1500);
        y = random.nextInt(300 , 1000);
        name = fishData.getName();
        texture = new Texture(fishData.getTextureAddress());
        sprite = new Sprite(texture);
        sprite.setX(x);
        sprite.setY(y);
        sprite.setSize(100 , 100);
        int n = random.nextInt()%5;
        if(n < 0)
            n += 5;
        if(n == 0)
            fishMoveType = FishMoveType.MIXED;
        else if(n == 1)
            fishMoveType = FishMoveType.SMOOTH;
        else if(n == 2)
            fishMoveType = FishMoveType.Flouter;
        else if(n == 3)
            fishMoveType = FishMoveType.Dart;
        else if(n == 4)
            fishMoveType = FishMoveType.Sneaker;
    }


    public void move(){
        x += fishMoveType.getRandomSpeed(random.nextInt()%3);
        sprite.setX(x);
    }

    public void handleHealth(float x){
        if(sprite.getX() >=x && sprite.getX() <= x + 300)
            health = Math.max(0 , health - (float) 0.1);
        else {
            health = Math.min(100 , health + (float) 0.1);
            isPerfect = false;
        }
    }

    public Sprite getSprite() {
        return sprite;
    }

    public float getHealth() {
        return health;
    }

    public boolean isPerfect() {
        return isPerfect;
    }
}
