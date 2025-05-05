package models.data;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class FishShopRecipeData implements Data {
	private static final String dataURL = "data/Shops - Fish Shop.json";
	private static List<FishShopRecipeData> recipes = null;

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

	public static void load() {
		Gson gson = new Gson();

		try (FileReader reader = new FileReader(dataURL)) {
			Type listType = new TypeToken<List<FishShopRecipeData>>(){}.getType();
			recipes = gson.fromJson(reader, listType);
			for (FishShopRecipeData a : recipes) {
				a.fullConstruct();
				System.out.println(a.name + " " + a.price + " " + a.description + " " + a.dailyLimit);
			}
		} catch (Exception e) {
			System.out.println("Can't open " + dataURL);
			e.printStackTrace();
		}
	}

	private void fullConstruct() {

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
