package models.map;

import org.apache.commons.lang3.StringUtils;

public enum TileType {
	PLACEABLE(1, 1, true, 'P' , null),
	// Buildings
	HOUSE(8, 8, true, 'H' , null),
	GREEN_HOUSE(10, 7, true, 'G' , null),
	MINES(5, 5, true, 'M' , null),
	LAKE(4, 5, false, '~' , null),
	COOP(3, 4, true, 'C' , null),
	BARN(4, 4, true, 'B' , null),

	// Shops
	BLACKSMITH(6, 9, true, 'B' , null),
	JOJAMART(8, 10, true, 'J' , null),
	PIERR_STORE(7, 7, true, 'P' , null),
	CARPENTER_SHOP(6, 7, true, 'C' ,null),
	FISH_SHOP(7, 7, true, 'F' , null),
	MARINE_SHOP(5, 9, true, 'M' , null),
	STARDROP_SALOON(9, 8, true, 'S' , null),

	PLOWED(1, 1, false, '#' , null),
	UNPLOWED(1, 1, false, '#' , null),
	ANIMAL(1, 1, false, 'A' ,null),
	REFRIGERATOR(1, 1, true, 'F' , null),
	PLANTED_SEED(1, 1, false, 'S' , null),
	PLANTED_TREE(1, 1, false, 'T' , null),
	DOOR(1, 1, true, '+' , null),
	BRIDGE(1 , 1 , true , '+' , null),

	// Foragings
	SIMPLE_ROCK(1, 1, false, 'R' , "Textures/Resource/Stone.png"),
	COPPER_ROCK(1, 1, false, 'R' , "Textures/Resource/Copper_Ore.png"),
	STEEL_ROCK(1, 1, false, 'R' , "Textures/Resource/Iron_Ore.png"),
	GOLD_ROCK(1, 1, false, 'g' , "Textures/Resource/Gold_Ore.png"),
	IRIDIUM_ROCK(1, 1, false, 'I' , "Textures/Resource/Iridium_Ore.png"),
	LEAF(1, 1, false, '*' , null),
	FORAGING_CROP(1, 1, false, 'C' , null),

	NPC(1, 1, false, 'N' , null),
	NPC_HOUSE(4, 4, false, 'O'  , null),

	ARTISAN(1, 1, false, '8' , null),
    SHIPPING_BIN(1, 1, false, 'S', null),;

	private final int defaultWidth;
	private final int defaultHeight;
	private final boolean isStructure;
	private final char symbol;
	private final String textureAddress;

	TileType(int defaultHeight, int defaultWidth, boolean isStructure, char symbol , String textureAddress) {
		this.defaultWidth = defaultWidth;
		this.defaultHeight = defaultHeight;
		this.isStructure = isStructure;
		this.symbol = symbol;
		this.textureAddress = textureAddress;
	}

	// Getters
	public int getDefaultWidth() { return defaultWidth; }
	public int getDefaultHeight() { return defaultHeight; }
	public boolean isStructure() { return isStructure; }
	public char getSymbol() { return symbol; }

	public String getTextureAddress() {
		return textureAddress;
	}

	public boolean isForaging() {
		return switch (this) {
			case SIMPLE_ROCK, COPPER_ROCK, STEEL_ROCK, GOLD_ROCK, IRIDIUM_ROCK, LEAF -> true;
			default -> false;
		};
	}

	public boolean isShop() {
		return switch (this) {
			case BLACKSMITH, JOJAMART, PIERR_STORE, CARPENTER_SHOP, FISH_SHOP, MARINE_SHOP, STARDROP_SALOON -> true;
			default -> false;
		};
	}

	public boolean isWater() {
		return switch (this) {
			case LAKE -> true;
			default -> false;
		};
	}

	public boolean isWalkable() {
		return switch (this) {
			case PLOWED, UNPLOWED -> true;
			default -> false;
		};
	}

	public static TileType fromName(String name) {
		for (TileType tileType : TileType.values()) {
			if (StringUtils.containsIgnoreCase(name, tileType.name())) {
				return tileType;
			}
		}
		return null;
	}

	public boolean canThorAttack() {
		return switch (this) {
			case PLOWED, UNPLOWED, PLANTED_TREE, ANIMAL, PLANTED_SEED -> true;
			default -> false;
		};
	}

	public boolean canCrowAttack() {
		return switch (this) {
			case PLOWED, UNPLOWED, PLANTED_SEED -> true;
			default -> false;
		};
	}

}
