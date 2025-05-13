package models.skill;

public enum SkillType {
    FARMING("Farming"),
    MINING("Mining"),
    FORAGING("Foraging"),
    FISHING("Fishing");

    private final String displayName;

    SkillType(String displayName) {
        this.displayName = displayName;
    }

    public static SkillType getSkillType(String displayName) {
        for (SkillType skillType : SkillType.values()) {
            if (skillType.displayName.equalsIgnoreCase(displayName)) {
                return skillType;
            }
        }
        return null;
    }

    public String getDisplayName() {
        return displayName;
    }

    /**
     * Calculate level from experience:
     * level = max(0, ⌊(exp - 50) / 100⌋)
     */
    public int calcLevel(int exp) {
        return Math.max(0, (exp - 50) / 100);
    }
}
