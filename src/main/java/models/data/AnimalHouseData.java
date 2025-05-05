package models.data;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AnimalHouseData implements Data {
	private static final String dataURL = "data/AnimalHouses.json";
	private static List<AnimalHouseData> animalHouses = null;

	@SerializedName("name")
	private String name;
	@SerializedName("animals")
	private ArrayList<String> animals;

	public static void load() {
		Gson gson = new Gson();

		try (FileReader reader = new FileReader(dataURL)) {
			Type listType = new TypeToken<List<AnimalHouseData>>(){}.getType();
			animalHouses = gson.fromJson(reader, listType);
			for (AnimalHouseData a : animalHouses) {
				a.fullConstruct();
				System.out.println(a.name);
				for (String s : a.animals) {
					System.out.print(s + " ");
				}
				System.out.println();
			}
		} catch (Exception e) {
			System.out.println("Can't open Animals.json");
			e.printStackTrace();
		}
	}

	private void fullConstruct() {

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
