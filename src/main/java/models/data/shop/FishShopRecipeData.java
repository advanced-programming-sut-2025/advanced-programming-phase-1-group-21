package models.data.shop;

import com.google.gson.annotations.SerializedName;
import models.data.Data;

import java.util.ArrayList;

public class FishShopRecipeData implements Data {
	private static final String dataURL = "data/Shops - Fish Shop.json";
	private static ArrayList<FishShopRecipeData> recipes = null;

	@SerializedName("name")
	private String name;
	@SerializedName("description")
	private String description;
	@SerializedName("price")
	private int price;
	@SerializedName("fishing-skill-level")
	private int skillLevel;
	@SerializedName("daily-limit")
	private int dailyLimit;

	public static String getDataURL() {
		return dataURL;
	}

	public static void setData(ArrayList<FishShopRecipeData> recipes) {
		FishShopRecipeData.recipes = recipes;
	}

	public void fullConstruct() {
//		System.out.println(name + "\n" + description + "\n" + price + "\n" + skillLevel + "\n" + dailyLimit + "\n------------");
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

	public int getSkillLevel() {
		return skillLevel;
	}

	public int getDailyLimit() {
		return dailyLimit;
	}

	public static FishShopRecipeData getRecipeData(String name) {
		for (FishShopRecipeData a : recipes)
			if (a.getName().equals(name))
				return a;
		return null;
	}
}
