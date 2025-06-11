package io.github.StardewValley.models.data;

import com.google.gson.annotations.SerializedName;
import io.github.StardewValley.models.map.AnimalHouseType;

import java.util.ArrayList;

public class AnimalHouseData implements Data {
	private static final String dataURL = "data/AnimalHouses.json";
	private static ArrayList<AnimalHouseData> animalHouses = null;

	@SerializedName("name")
	private String name;
	@SerializedName("type")
	private String type;
	private AnimalHouseType ctype;

	@SerializedName("size")
	private int size;
	@SerializedName("animals")
	private ArrayList<String> animals;

	protected static String getDataURL() {
		return dataURL;
	}

	protected static void setData(ArrayList<AnimalHouseData> animalHouses) {
		AnimalHouseData.animalHouses = animalHouses;
	}

	public void fullConstruct() {
		ctype = AnimalHouseType.valueOf(type.toUpperCase());
	}

	public AnimalHouseType getType() {
		return ctype;
	}

	public int getSize() {
		return size;
	}

	public String getName() {
		return name;
	}

	public ArrayList<String> getAnimals() {
		return animals;
	}

	public static AnimalHouseData getAnimalHouseData(String name) {
		for (AnimalHouseData a : animalHouses)
			if (a.getName().equalsIgnoreCase(name))
				return a;
		return null;
	}
}
