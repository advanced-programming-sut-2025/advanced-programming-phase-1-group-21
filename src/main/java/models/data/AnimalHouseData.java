package models.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AnimalHouseData implements Data {
	private static final String dataURL = "data/AnimalHouses.json";
	private static ArrayList<AnimalHouseData> animalHouses = null;

	@SerializedName("name")
	private String name;
	@SerializedName("animals")
	private ArrayList<String> animals;

	protected static String getDataURL() {
		return dataURL;
	}

	protected static void setData(ArrayList<AnimalHouseData> animalHouses) {
		AnimalHouseData.animalHouses = animalHouses;
	}

	public void fullConstruct() {

	}

	public String getName() {
		return name;
	}

	public ArrayList<String> getAnimals() {
		return animals;
	}

	public static AnimalHouseData getAnimalHouseData(String name) {
		for (AnimalHouseData a : animalHouses)
			if (a.getName().equals(name))
				return a;
		return null;
	}
}
