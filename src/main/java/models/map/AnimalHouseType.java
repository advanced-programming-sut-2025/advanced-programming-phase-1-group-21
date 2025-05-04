package models.map;

public enum AnimalHouseType {
	NORMAL("Normal", 4),
	BIG("Big", 8),
	DELUXE("Deluxe", 12),
	;

	private final String name;
	private final int size;
	AnimalHouseType(String name, int size) {
		this.name = name;
		this.size = size;
	}

	public String getName() {
		return name;
	}

	public int getSize() {
		return size;
	}

	public static AnimalHouseType getAnimalHouseType(String name) {
		for (AnimalHouseType animalHouseType : AnimalHouseType.values())
			if (animalHouseType.getName().equals(name))
				return animalHouseType;
		return null;
	}
}
