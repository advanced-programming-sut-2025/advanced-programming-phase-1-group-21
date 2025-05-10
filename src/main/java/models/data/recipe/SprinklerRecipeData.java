package models.data.recipe;

import com.google.gson.annotations.SerializedName;
import models.data.Data;

import java.util.ArrayList;
import java.util.Map;

public class SprinklerRecipeData implements Data {
	private static final String dataURL = "data/SprinklerCraftingRecipes.json";
	private static ArrayList<SprinklerRecipeData> recipes = null;

	@SerializedName("name")
	private String name;

	@SerializedName("description")
	private String description;

	@SerializedName("tiles")
	private ArrayList <ArrayList <Integer>> tiles;

	@SerializedName("ingredients")
	private Map<String, Integer> ingredients;

	@SerializedName("skill-type")
	private String skillType;

	@SerializedName("skill-level")
	private int skillLevel;

	public static String getDataURL() {
		return dataURL;
	}

	public static void setData(ArrayList<SprinklerRecipeData> recipes) {
		SprinklerRecipeData.recipes = recipes;
	}

	public void fullConstruct() {
//		System.out.println(name + "\n" + description + "\n" + tiles + "\n" + ingredients + "\n" + skillType + "\n" + skillLevel + "\n---------");
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

	public String getSkillType() {
		return skillType;
	}

	public int getSkillLevel() {
		return skillLevel;
	}

	public ArrayList<ArrayList<Integer>> getTiles() {
		return tiles;
	}

	public static SprinklerRecipeData getRecipeData(String name) {
		for (SprinklerRecipeData a : recipes)
			if (a.getName().equalsIgnoreCase(name))
				return a;
		return null;
	}
}
