package models.skill;

public class Skill {
    private final String name;
    private final int level;
    private final int experience;
    private final int requiredExperience;
    private final SkillType skillType;

    public Skill(String name, int level, int experience, int requiredExperience, SkillType skillType) {
        this.name = name;
        this.level = level;
        this.experience = experience;
        this.requiredExperience = requiredExperience;
        this.skillType = skillType;
    }

    public String getName() {
        return name;
    }
    
    
}
