package controllers;

import models.App;
import models.data.ShopData;
import models.game.Player;
import models.map.Map;
import models.skill.SkillType;

public class RequirementChecker {
    Player player;

    public RequirementChecker() {
        player = App.game.getCurrentPlayer();
    }

    private boolean checkSkillLevel(String typeStr, int lvl) {
        if (typeStr == null) return true;
        SkillType type = SkillType.getSkillType(typeStr);
        if (type == null) throw new RuntimeException("Skill type is null");
        return player.getSkillLevel(type) >= lvl;
    }

    private boolean checkBuilding(String name) {
        if (name == null) return true;
        Map map = player.getMap();
        return map.getBuildingByFullName(name) != null;
    }

    public boolean checkShopDate(ShopData data) {
        if (data == null) return true;
        if (!checkBuilding(data.getBuildingRequired())) return false;
        if (!checkSkillLevel(data.getSkillTypeRequired(), data.getSkillLevelRequired())) return false;
        //TODO others...
        return true;
    }
}
