package models.map;

import models.animal.Animal;
import models.crop.Tree;
import models.game.Refrigerator;

public enum TileType {
	// Buildings
	HOUSE(4, 4, true, 'H'),
	GREEN_HOUSE(5, 4, true, 'G'),
	MINES(6, 6, true, 'M'),
	LAKE(5, 4, false, '~'),
	COOP(3, 4, true, 'C'),
	BARN(4, 4, true, 'B'),

	// Shops
	BLACKSMITH(6, 10, true, 'B'),
	JOJAMART(6, 10, true, 'J'),
	PIERR_STORE(6, 10, true, 'P'),
	CARPENTER_SHOP(6, 10, true, 'C'),
	FISH_SHOP(6, 10, true, 'F'),
	MARINE_SHOP(6, 10, true, 'M'),
	STARDROP_SALOON(6, 10, true, 'S'),

	PLOWED(1, 1, false, '#'),
	UNPLOWED(1, 1, false, '#'),
	TREE(1, 1, false, 'T'),
	ANIMAL(1, 1, false, 'A'),
	REFRIGERATOR(1, 1, true, 'F'),
	PLANTED_SEED(1, 1, false, '*'),
	DOOR(1, 1, true, '+'),

	// Foragings
	SIMPLE_ROCK(1, 1, false, 'R'),
	COPPER_ROCK(1, 1, false, 'R'),
	STEEL_ROCK(1, 1, false, 'R'),
	GOLD_ROCK(1, 1, false, 'g'),
	IRIDIUM_ROCK(1, 1, false, 'I'),
	LEAF(1, 1, false, '*');

	private final int defaultWidth;
	private final int defaultHeight;
	private final boolean isStructure;
	private final char symbol;

	TileType(int defaultHeight, int defaultWidth, boolean isStructure, char symbol) {
		this.defaultWidth = defaultWidth;
		this.defaultHeight = defaultHeight;
		this.isStructure = isStructure;
		this.symbol = symbol;
	}

	// Getters
	public int getDefaultWidth() { return defaultWidth; }
	public int getDefaultHeight() { return defaultHeight; }
	public boolean isStructure() { return isStructure; }
	public char getSymbol() { return symbol; }

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
}