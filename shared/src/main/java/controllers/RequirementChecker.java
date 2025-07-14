package controllers;

import data.ShopData;
import models.game.Player;
import models.map.Map;
import models.skill.SkillType;

public class RequirementChecker {
    Player player;

    public RequirementChecker(Player player) {
        this.player = player;
    }

    private boolean checkSkillLevel(String typeStr, int lvl) {
        if (typeStr == null) return true;
        SkillType type = SkillType.getSkillType(typeStr);
        if (type == null) throw new RuntimeException("Skill type is null");
        return player.getSkillLevel(type) >= lvl;
    }

    private boolean checkBuilding(String name) {
        if (name == null) return true;
        Map map = player.getDefaultMap();
        System.err.println("MAP: " + map);
        return map.getBuildingByFullName(name) != null;
    }

    public boolean checkShopDate(ShopData data) {
        if (data == null) return true;
        if (!checkBuilding(data.getBuildingRequired())) {
            System.err.println("Building required failed " + data.getBuildingRequired());
            return false;
        }
        if (!checkSkillLevel(data.getSkillTypeRequired(), data.getSkillLevelRequired())) {
            System.err.println("Required skill level required failed");
            return false;
        }
        //TODO others...
        return true;
    }
}
