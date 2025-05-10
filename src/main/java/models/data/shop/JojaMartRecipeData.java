package models.data.shop;

import com.google.gson.annotations.SerializedName;
import models.data.Data;

import java.util.ArrayList;

public class JojaMartRecipeData implements Data, ShopData {
	private static final String dataURL = "data/Shops - JojaMart.json";
	private static ArrayList<JojaMartRecipeData> recipes = null;

	@SerializedName("name")
	private String name;
	@SerializedName("description")
	private String description;
	@SerializedName("price")
	private int price;
	@SerializedName("seasons")
	private ArrayList<String> seasons;
	@SerializedName("daily-limit")
	private int dailyLimit;

	public static String getDataURL() {
		return dataURL;
	}

	public static void setData(ArrayList<JojaMartRecipeData> recipes) {
		JojaMartRecipeData.recipes = recipes;
	}

	public void fullConstruct() {
//		System.out.println(name + "\n" + description + "\n" + seasons + "\n" + price + "\n" + dailyLimit + "\n----------");
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

	public ArrayList<String> getSeasons() {
		return seasons;
	}

	public static JojaMartRecipeData getRecipeData(String name) {
		for (JojaMartRecipeData a : recipes)
			if (a.getName().equals(name))
				return a;
		return null;
	}
}
