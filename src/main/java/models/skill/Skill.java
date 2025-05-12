package models.skill;

public class Skill {
    private int farmingSkill = 0;
    private int miningSkill = 0;
    private int natureSkill = 0;
    private int fishingSkill = 0;

    public void setFarmingSkill(int farmingSkill) {
        this.farmingSkill = farmingSkill;
    }

    public void setMiningSkill(int miningSkill) {
        this.miningSkill = miningSkill;
    }

    public void setNatureSkill(int natureSkill) {
        this.natureSkill = natureSkill;
    }

    public void setFishingSkill(int fishingSkill) {
        this.fishingSkill = fishingSkill;
    }

    public int getFarmingLevel(){
        return Math.max(0 , (farmingSkill-50)/100);
    }

    public int getMiningLevel(){
        return Math.max(0 , (miningSkill-50)/100);
    }

    public int getNatureLevel(){
        return Math.max(0 , (natureSkill-50)/100);
    }

    public int getFishingLevel(){
        return Math.max(0 , (fishingSkill-50)/100);
    }

    public int getFarmingSkill() {
        return farmingSkill;
    }

    public int getMiningSkill() {
        return miningSkill;
    }

    public int getNatureSkill() {
        return natureSkill;
    }

    public int getFishingSkill() {
        return fishingSkill;
    }
}
