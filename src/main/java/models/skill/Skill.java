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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Skills:\n");
        for (SkillType type : SkillType.values()) {
            int exp = getSkillExp(type);
            int level = getSkillLevel(type);
            sb.append(String.format("  %s - Exp: %d, Level: %d%n", type.getDisplayName(), exp, level));
        }
        return sb.toString();
    }
}
