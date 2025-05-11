package models.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SprinklerData implements Data {
	private static final String dataURL = "data/SprinklerCraftingRecipes.json";
	private static ArrayList<SprinklerData> data = null;

	@SerializedName("name")
	private String name;
	@SerializedName("description")
	private String description;
	@SerializedName("tiles")
	private ArrayList <ArrayList <Integer>> tiles;

	public static String getDataURL() {
		return dataURL;
	}

	public static void setData(ArrayList<SprinklerData> data) {
		SprinklerData.data = data;
	}

	public void fullConstruct() {
//		System.out.println(name + "\n" + description + "\n" + tiles + "\n" + ingredients + "\n" + skillType + "\n" + skillLevel + "\n---------");
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public ArrayList<ArrayList<Integer>> getTiles() {
		return tiles;
	}

	public static SprinklerData getData(String name) {
		for (SprinklerData a : data)
			if (a.getName().equalsIgnoreCase(name))
				return a;
		return null;
	}
}
