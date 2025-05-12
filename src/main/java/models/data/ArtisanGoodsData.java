package models.data;

import com.google.gson.annotations.SerializedName;
import models.data.items.ItemData;

import java.util.ArrayList;
import java.util.Map;

public class ArtisanGoodsData implements Data, ItemData {
	private static final String dataURL = "data/ArtisanGoods.json";
	private static ArrayList<ArtisanGoodsData> recipes = null;

	@SerializedName("artisan")
	private String artisan;
	@SerializedName("description")
	private String description;
	@SerializedName("name")
	private String name;
	@SerializedName("ingredients")
	private ArrayList<Map<String, Integer>> ingredients;
	@SerializedName("processing-time")
	private int processingTime;

	public static String getDataURL() {
		return dataURL;
	}

	public static void setData(ArrayList<ArtisanGoodsData> recipes) {
		ArtisanGoodsData.recipes = recipes;
	}

	public void printData() {
		System.out.println("----------------------------------");
		System.out.println("artisan: " + artisan);
		System.out.println("description: " + description);
		System.out.println("name: " + name);
		System.out.println("processingTime: " + processingTime);
		System.out.println("ingredients: ");
		if (ingredients != null)
			for (Map<String, Integer> map : ingredients)
				for (Map.Entry<String, Integer> entry : map.entrySet()) {
					String key = entry.getKey();
					int value = entry.getValue();
					System.out.println("key: " + key + " value: " + value);
				}
		else
			System.out.println("ingredients is null");
		System.out.println("----------------------------------");
	}

	public void fullConstruct() {
//		printData();
	}

	@Override
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

	public static ArtisanGoodsData getRecipeData(String name) {
		for (ArtisanGoodsData a : recipes)
			if (a.getName().equalsIgnoreCase(name))
				return a;
		return null;
	}
}
