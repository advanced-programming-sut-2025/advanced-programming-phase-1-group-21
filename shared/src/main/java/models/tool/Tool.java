package models.tool;

import models.Item.Item;
import models.Item.ItemType;
import models.game.Game;
import models.map.Coord;
import models.result.Result;

public abstract class Tool extends Item {
    private final ToolType toolType;
	public ToolMaterialType toolMaterialType = ToolMaterialType.PRIMITIVE;


	public Tool(ToolType toolType) {
		this.toolType = toolType;
	}

	public ToolType getToolType() {
		return toolType;
	}

	public ToolMaterialType getToolMaterialType() {
		return toolMaterialType;
	}

	abstract public Result<Item> use(Coord coord, Game game);

	public String getName() {
		return toolType.getName();
	}

	public ItemType getItemType() {
		return ItemType.TOOL;
	}

	public int getAmount() {
		return 1;
	}

	public void setAmount(int amount) {
		throw new RuntimeException("HEY. You are not supposed to call this method. Because this is a tool: " + toolType.getName() + " and you can't set new amount for it.");
	}

	public void setToolMaterialType(ToolMaterialType toolMaterialType) {
		this.toolMaterialType = toolMaterialType;
	}

	public void changeAmount(int change) {
		throw new RuntimeException("HEY. You are not supposed to call this method. Because this is a tool: " + toolType.getName() + " and you can't change it's amount.");
	}

	public boolean isSalable() {
		return false;
	}

	public int getPrice() {
		return 0;
	}

	@Override
	public String toString() {
		return String.format(
				"%s{toolType=%s, toolMaterialType=%s}",
				getClass().getSimpleName(),
				toolType,
				toolMaterialType
		);
	}
}
