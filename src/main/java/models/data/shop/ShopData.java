package models.data.shop;

import com.google.gson.annotations.SerializedName;
import models.data.Data;
import models.time.Season;

import java.util.ArrayList;
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
	@SerializedName("item-required")
	private String itemRequired;
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
//		System.out.println("shop: " + shop);
//		System.out.println("name: " + name);
//		System.out.println("description: " + description);
//		System.out.println("seasons: " + seasons);
//		System.out.println("cseasons: " + cseasons);
//		System.out.println("price: " + price);
//		System.out.println("ingredients: " + ingredients);
//		System.out.println("backpack-required: " + backpackRequired);
//		System.out.println("item-required: " + itemRequired);
//		System.out.println("building-required: " + buildingRequired);
//		System.out.println("skill-type-required: " + skillTypeRequired);
//		System.out.println("skill-level-required: " + skillLevelRequired);
//		System.out.println("daily-limit: " + dailyLimit);
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
		return ingredients;
	}

	public String getBackpackRequired() {
		return backpackRequired;
	}

	public String getItemRequired() {
		return itemRequired;
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
}
