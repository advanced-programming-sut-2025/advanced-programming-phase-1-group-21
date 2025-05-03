package models.crop;

import models.time.Season;

import java.util.ArrayList;
import java.util.List;

public class SeedInfo {
    private final static List<SeedInfo> seedInfoList = new ArrayList<>();
    private final String resultName;
    private final String seedName;
    private final List<Integer> stages;
    private final int totalHarvestTime;
    private final boolean oneTime;
    private final Integer regrowthTime;
    private final int baseSellPrice;
    private final boolean isEdible;
    private final Integer energy;
    private final Integer baseHealth;
    private final List<Season> seasons;
    private final boolean canBecomeGiant;

    public SeedInfo(String resultName, String seedName, List<Integer> stages, int totalHarvestTime, boolean oneTime,
                    Integer regrowthTime, int baseSellPrice, boolean isEdible, Integer energy, Integer baseHealth,
                    List<Season> seasons, boolean canBecomeGiant) {
        this.resultName = resultName;
        this.seedName = seedName;
        this.stages = stages;
        this.totalHarvestTime = totalHarvestTime;
        this.oneTime = oneTime;
        this.regrowthTime = regrowthTime;
        this.baseSellPrice = baseSellPrice;
        this.isEdible = isEdible;
        this.energy = energy;
        this.baseHealth = baseHealth;
        this.seasons = seasons;
        this.canBecomeGiant = canBecomeGiant;
        seedInfoList.add(this);
    }

    public String getResultName() { return resultName; }
    public String getSeedName() { return seedName; }
    public List<Integer> getStages() { return stages; }
    public int getTotalHarvestTime() { return totalHarvestTime; }
    public boolean isOneTime() { return oneTime; }
    public Integer getRegrowthTime() { return regrowthTime; }
    public int getBaseSellPrice() { return baseSellPrice; }
    public boolean isEdible() { return isEdible; }
    public Integer getEnergy() { return energy; }
    public Integer getBaseHealth() { return baseHealth; }
    public List<Season> getSeasons() { return seasons; }
    public boolean canBecomeGiant() { return canBecomeGiant; }
    public static SeedInfo getSeedInfo(String name) {
        if (seedInfoList.isEmpty()) {
            throw new RuntimeException("SeedInfo list is empty! You have to load crops.json first!");
        }
        for (SeedInfo seedInfo : seedInfoList)
            if (seedInfo.getResultName().equals(name) || seedInfo.getSeedName().equals(name))
                return seedInfo;
        return null;
    }
    public int getStage(int day) {
        int sum = 0, result = 0;
        for (int stage: stages) {
            sum += stage;
            if (sum < day)
                result++;
        }
        return result;
    }

    @Override
    public String toString() {
        String result = "Crop{" +
                "Name: " + resultName + "\n" +
                "Source: " + seedName + "\n" +
                "stages: ";

        for (int x: stages) {
            result += x;
            if (x != stages.get(stages.size() - 1)) result += '-';
        }

        result += "\n" +
                "totalHarvestTime: " + totalHarvestTime +
                "oneTime: " + oneTime +
                "regrowthTime: " + regrowthTime +
                "baseSellPrice: " + baseSellPrice +
                "isEdible: " + isEdible +
                "energy: " + energy +
                "baseHealth: " + baseHealth +
                "seasons: ";

        for (Season season: seasons)
            result += season.toString().toLowerCase() + " ";
        result += "\n" +
                "canBecomeGiant: " + canBecomeGiant;

        return result;
    }
}
