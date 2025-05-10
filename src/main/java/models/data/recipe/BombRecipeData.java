package models.data.recipe;

import com.google.gson.annotations.SerializedName;
import models.data.Data;

import java.util.ArrayList;
import java.util.Map;

public class BombRecipeData implements Data {
	private static final String dataURL = "data/BombCraftingRecipes.json";
	private static ArrayList<BombRecipeData> recipes = null;

	@SerializedName("name")
	private String name;
	@SerializedName("description")
	private String description;
	@SerializedName("price")
	private int price;

	@SerializedName("ingredients")
	private Map<String, Integer> ingredients;

	@SerializedName("skill-type")
	private String skillType;

	@SerializedName("skill-level")
	private int skillLevel;

	@SerializedName("radius")
	private int radius;

	public static String getDataURL() {
		return dataURL;
	}

	public static void setData(ArrayList<BombRecipeData> recipes) {
		BombRecipeData.recipes = recipes;
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

	public Map<String, Integer> getIngredients() {
		return ingredients;
	}

	public String getSkillType() {
		return skillType;
	}

	public int getSkillLevel() {
		return skillLevel;
	}

	public int getRadius() {
		return radius;
	}

	public static BombRecipeData getBombRecipeData(String name) {
		for (BombRecipeData a : recipes)
			if (a.getName().equalsIgnoreCase(name))
				return a;
		return null;
	}
}
