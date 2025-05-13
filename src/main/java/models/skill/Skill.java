package models.skill;

import java.util.EnumMap;

public class Skill {
    private final EnumMap<SkillType, Integer> expMap = new EnumMap<>(SkillType.class);

    public Skill() {
        for (SkillType type : SkillType.values()) {
            expMap.put(type, 0);
        }
    }

    public void setSkillExp(SkillType type, int exp) {
        expMap.put(type, exp);
    }

    public int getSkillExp(SkillType type) {
        return expMap.getOrDefault(type, 0);
    }

    public int getSkillLevel(SkillType type) {
        return type.calcLevel(getSkillExp(type));
    }
}
