package data;


import com.google.gson.annotations.SerializedName;
import models.time.Season;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopData implements Data {
	private static final String dataURL = "data/Shop Recipes.json";
	private static ArrayList<ShopData> recipes = null;

	@SerializedName("shop")
	private String shop;
	@SerializedName("name")
	private String name;
	@SerializedName("description")
	private String description;
	@SerializedName("seasons")
	private ArrayList <String> seasons;
	private ArrayList <Season> cseasons;
	@SerializedName("prices")
	private ArrayList <Integer> price;
	@SerializedName("ingredients")
	private Map<String, Integer> ingredients;

	@SerializedName("backpack-required")
	private String backpackRequired;
	@SerializedName("building-required")
	private String buildingRequired;
	@SerializedName("skill-type-required")
	private String skillTypeRequired;
	@SerializedName("skill-level-required")
	private int skillLevelRequired;
	@SerializedName("daily-limit")
	private int dailyLimit;

	public static String getDataURL() {
		return dataURL;
	}

	public static void setData(ArrayList<ShopData> recipes) {
		ShopData.recipes = recipes;
	}

	public void fullConstruct() {
		cseasons = new ArrayList<>();
		for (String season : seasons)
			cseasons.add(Season.valueOf(season.toUpperCase()));
	}

	public String getShop() {
		return shop;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}


	public ArrayList<Season> getSeasons() {
		return cseasons;
	}

	public ArrayList<Integer> getPrices() {
		return price;
	}

	public int getPrice(Season season) {
		for (int i = 0; i < cseasons.size(); i++) {
			if (cseasons.get(i).equals(season)) {
				return price.get(i);
			}
		}
		return -1;
	}

	public Map<String, Integer> getIngredients() {
		return (ingredients == null ? new HashMap<String, Integer>() :  ingredients);
	}

	public String getBackpackRequired() {
		return backpackRequired;
	}

	public String getBuildingRequired() {
		return buildingRequired;
	}

	public String getSkillTypeRequired() {
		return skillTypeRequired;
	}

	public int getSkillLevelRequired() {
		return skillLevelRequired;
	}

	public int getDailyLimit() {
		return dailyLimit;
	}

	public static ShopData getRecipeData(String name) {
		for (ShopData a : recipes)
			if (a.getName().equalsIgnoreCase(name))
				return a;
		return null;
	}

	public static List<ShopData> getRecipeByShop(String shop) {
		List<ShopData> r = new ArrayList<>();
		for (ShopData a : recipes)
			if (a.getShop().equalsIgnoreCase(shop))
				r.add(a);
		return r;
	}

	@Override
	public String toString() {
		return "ShopData{" +
				"shop='" + shop + '\'' +
				", name='" + name + '\'' +
				", description='" + description + '\'' +
				", cseasons=" + cseasons +
				", prices=" + price +
				", ingredients=" + ingredients +
				", backpackRequired='" + backpackRequired + '\'' +
				", buildingRequired='" + buildingRequired + '\'' +
				", skillTypeRequired='" + skillTypeRequired + '\'' +
				", skillLevelRequired=" + skillLevelRequired +
				", dailyLimit=" + (dailyLimit == -1 ? "NO LIMIT" : dailyLimit) +
				'}';
	}
}
