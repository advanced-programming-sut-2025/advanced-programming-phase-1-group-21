package models.game;

public class Energy {
    static final int MAX_ENERGY = 200;

    private int currentEnergy;
    private int maxEnergy;

    private int remainingDays;

    public Energy() {
        maxEnergy = MAX_ENERGY;
        currentEnergy = maxEnergy;
        remainingDays = -1;
    }

    public int getCurrentEnergy() {
        return currentEnergy;
    }

    public int getMaxEnergy() {
        return maxEnergy;
    }

    public void decreaseEnergy(int amount) {
        if (currentEnergy < amount) {
            currentEnergy = 0;
        }
        currentEnergy -= amount;
    }

    public void advanceDay() {
        remainingDays--;
        if (remainingDays < 0) {
            remainingDays = -1;
            maxEnergy = MAX_ENERGY;
        }
        currentEnergy = maxEnergy;
    }

    public void setMaxEnergy(int maxEnergy, int days) {
        this.maxEnergy = maxEnergy;
        this.remainingDays = days;
    }

    public void setEnergy(int energy) {
        currentEnergy = energy;
    }

    @Override
    public String toString() {
        return String.format(
                "Energy{current=%d, max=%d, remainingDays=%d}",
                currentEnergy,
                maxEnergy,
                remainingDays
        );
    }

}
