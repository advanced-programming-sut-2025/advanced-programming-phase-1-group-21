package models.data.shop;

import com.google.gson.annotations.SerializedName;
import models.data.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CarpenterRecipeData implements Data, ShopData {
	private static final String dataURL = "data/Shops - Carpenter's Shop.json";
	private static List<CarpenterRecipeData> recipes = null;

	@SerializedName("name")
	private String name;
	@SerializedName("description")
	private String description;
	@SerializedName("ingredients")
	private Map<String, Integer> ingredients;
	@SerializedName("required")
	private String required;
	@SerializedName("price")
	private int price;
	@SerializedName("daily-limit")
	private int dailyLimit;

	public static String getDataURL() {
		return dataURL;
	}

	public static void setData(ArrayList<CarpenterRecipeData> recipes) {
		CarpenterRecipeData.recipes = recipes;
	}

	public void fullConstruct() {

	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Map<String, Integer> getIngredients() {
		return ingredients;
	}

	public String getRequired() {
		return required;
	}

	public int getPrice() {
		return price;
	}

	public int getDailyLimit() {
		return dailyLimit;
	}

	public static CarpenterRecipeData getRecipeData(String name) {
		for (CarpenterRecipeData a : recipes)
			if (a.getName().equalsIgnoreCase(name))
				return a;
		return null;
	}
}
