package models.game;

public class Tool extends Item {
    private final ToolType toolType;

    public Tool(String name, ItemType itemType, int cost, ToolType toolType) {
        super(name, itemType, cost);
		this.toolType = toolType;
	}

    public String getName() {

        return getName();
    }
    
    
}
