package models.game;

public class Tool extends Item {
    private final ToolType toolType;

    public Tool(String name, ToolType toolType) {
        this.name = name;
        this.toolType = toolType;
    }

    public String getName() {
        return name;
    }
    
    
}
