package models.crops;

import models.time.Season;
import java.util.List;
import java.util.Arrays;

public class SeedSource {
    private String name;
    private String source;
    private List<Integer> stages;
    private int totalHarvestTime;
    private boolean oneTime;
    private Integer regrowthTime;
    private int baseSellPrice;
    private boolean isEdible;
    private Integer energy;
    private Integer baseHealth;
    private List<Season> seasons;
    private boolean canBecomeGiant;

    public SeedSource(String name, String source, List<Integer> stages, int totalHarvestTime, boolean oneTime,
                Integer regrowthTime, int baseSellPrice, boolean isEdible, Integer energy, Integer baseHealth,
                List<Season> seasons, boolean canBecomeGiant) {
        this.name = name;
        this.source = source;
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
    }

    public String getName() { return name; }
    public String getSource() { return source; }
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

    @Override
    public String toString() {
        return "Crop{" +
                "name='" + name + '\'' +
                ", source='" + source + '\'' +
                ", stages=" + stages +
                ", totalHarvestTime=" + totalHarvestTime +
                ", oneTime=" + oneTime +
                ", regrowthTime=" + regrowthTime +
                ", baseSellPrice=" + baseSellPrice +
                ", isEdible=" + isEdible +
                ", energy=" + energy +
                ", baseHealth=" + baseHealth +
                ", seasons=" + seasons +
                ", canBecomeGiant=" + canBecomeGiant +
                '}';
    }
}
