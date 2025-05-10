package models.data.shop;

import com.google.gson.annotations.SerializedName;
import models.data.Data;

import java.util.ArrayList;
import java.util.Map;

public class BlackSmithRecipeData implements Data {
	private static final String dataURL = "data/Shops - Blacksmith.json";
	private static ArrayList<BlackSmithRecipeData> recipes = null;

	@SerializedName("name")
	private String name;
	@SerializedName("description")
	private String description;
	@SerializedName("price")
	private int price;
	@SerializedName("ingredient")
	private Map<String, Integer> ingredient;
	@SerializedName("daily-limit")
	private int dailyLimit;

	public static String getDataURL() {
		return dataURL;
	}

	public static void setData(ArrayList<BlackSmithRecipeData> recipes) {
		BlackSmithRecipeData.recipes = recipes;
	}

	public void fullConstruct() {

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

	public Map<String, Integer> getIngredient() {
		return ingredient;
	}

	public static BlackSmithRecipeData getBlackSmithRecipeData(String name) {
		for (BlackSmithRecipeData a : recipes)
			if (a.getName().equals(name))
				return a;
		return null;
	}
}
