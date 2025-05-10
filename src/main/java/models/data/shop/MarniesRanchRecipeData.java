package models.data.shop;

import com.google.gson.annotations.SerializedName;
import models.data.Data;

import java.util.ArrayList;

public class MarniesRanchRecipeData implements Data, ShopData {
	private static final String dataURL = "data/Shops - Marnie's Ranch.json";
	private static ArrayList<MarniesRanchRecipeData> recipes = null;

	@SerializedName("name")
	private String name;
	@SerializedName("description")
	private String description;
	@SerializedName("price")
	private int price;
	@SerializedName("building-required")
	private String buildingRequired;
	@SerializedName("daily-limit")
	private int dailyLimit;

	public static String getDataURL() {
		return dataURL;
	}

	public static void setData(ArrayList<MarniesRanchRecipeData> recipes) {
		MarniesRanchRecipeData.recipes = recipes;
	}

	public void fullConstruct() {
//		System.out.println(name + "\n" + description + "\n" + price + "\n" + buildingRequired + "\n" + dailyLimit + "\n--------");
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

	public String getBuildingRequired() {
		return buildingRequired;
	}

	public static MarniesRanchRecipeData getRecipeData(String name) {
		for (MarniesRanchRecipeData a : recipes)
			if (a.getName().equalsIgnoreCase(name))
				return a;
		return null;
	}
}
