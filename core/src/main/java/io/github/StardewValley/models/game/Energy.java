package io.github.StardewValley.models.game;

import io.github.StardewValley.models.DailyUpdate;

import java.io.Serializable;

public class Energy implements DailyUpdate, Serializable {
    static final int MAX_ENERGY = 200;

    private int currentEnergy;
    private int totalEnergyForRound;
    private int maxEnergy;

    private int remainingDays;

    public Energy() {
        maxEnergy = MAX_ENERGY;
        currentEnergy = 50;
        totalEnergyForRound = maxEnergy;
        remainingDays = -1;
    }

    public int getCurrentEnergy() {
        return currentEnergy;
    }

    public int getMaxEnergy() {
        return maxEnergy;
    }

    public void decreaseEnergy(int amount) {
        amount = Math.min(amount, currentEnergy);
        currentEnergy -= amount;
        totalEnergyForRound -= amount;
    }

    @Override
    public boolean nextDay(Game g) {
        remainingDays--;
        if (remainingDays < 0) {
            remainingDays = -1;
            maxEnergy = MAX_ENERGY;
        }
        totalEnergyForRound = maxEnergy;
        reset();
        return false;
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

    public void reset() {
        currentEnergy = Math.min(totalEnergyForRound, 50);
    }
}
