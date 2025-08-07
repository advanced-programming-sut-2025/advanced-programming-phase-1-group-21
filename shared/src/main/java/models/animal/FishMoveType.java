package models.animal;

public enum FishMoveType {
    MIXED(-4, 4),
    SMOOTH(0,4),
    Sneaker(-4,8),
    Flouter(-8,4),
    Dart(-8,8);

    private int leftSpeed;
    private int rightSpeed;

    FishMoveType(int leftSpeed, int rightSpeed) {
        this.leftSpeed = leftSpeed;
        this.rightSpeed = rightSpeed;
    }

    public int getRandomSpeed(int random){
        if(random == 0)
            return 0;
        if(random == 1)
            return this.leftSpeed;
        if(random == 2)
            return this.rightSpeed;
        return 0;
    }
}
