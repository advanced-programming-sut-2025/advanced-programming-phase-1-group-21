package io.github.StardewValley.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Map;

public class ArtisanRecipeData implements Data {
	@SerializedName("name")
	private String name;
	@SerializedName("description")
	private String description;
	@SerializedName("ingredients")
	private ArrayList<Map<String, Integer>> ingredients;
	@SerializedName("processing-time")
	private int processingTime;

	public void printData() {
		System.out.println("	----------------------------------");
		System.out.println("	name: " + name);
		System.out.println("	description: " + description);
		System.out.println("	processingTime: " + processingTime);
		System.out.println("	ingredients: ");
		if (ingredients != null)
			for (Map<String, Integer> map : ingredients)
				for (Map.Entry<String, Integer> entry : map.entrySet()) {
					String key = entry.getKey();
					int value = entry.getValue();
					System.out.println("	key: " + key + " value: " + value);
				}
		else
			System.out.println("	ingredients is null");
		System.out.println("	----------------------------------");
	}

	public void fullConstruct() {
//		printData();
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public ArrayList<Map<String, Integer>> getIngredients() {
		return ingredients;
	}

	public int getProcessingTime() {
		return processingTime;
	}
}
