package models.data.shop;

import com.google.gson.annotations.SerializedName;
import models.data.Data;

import java.util.ArrayList;

public class StardropSaloonRecipeData implements Data {
	private static final String dataURL = "data/Shops - The Stardrop Saloon.json";
	private static ArrayList<StardropSaloonRecipeData> recipes = null;

	@SerializedName("name")
	private String name;
	@SerializedName("description")
	private String description;
	@SerializedName("price")
	private int price;
	@SerializedName("daily-limit")
	private int dailyLimit;

	public static String getDataURL() {
		return dataURL;
	}

	public static void setData(ArrayList<StardropSaloonRecipeData> recipes) {
		StardropSaloonRecipeData.recipes = recipes;
	}

	public void fullConstruct() {
//		System.out.println(name + "\n" + description + "\n" + price + "\n" + dailyLimit + "\n------------");
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public int getPrice() {
		return price;
	}

	public int getDailyLimit() {
		return dailyLimit;
	}

	public static StardropSaloonRecipeData getRecipeData(String name) {
		for (StardropSaloonRecipeData a : recipes)
			if (a.getName().equalsIgnoreCase(name))
				return a;
		return null;
	}
}
