package models.data.recipe;

import com.google.gson.annotations.SerializedName;
import models.data.Data;

import java.util.ArrayList;
import java.util.Map;

public class CraftingRecipeData implements Data {
	private static final String dataURL = "data/CraftingRecipes.json";
	private static ArrayList<CraftingRecipeData> recipes = null;

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

	@SerializedName("salable")
	private boolean salable;

	public static String getDataURL() {
		return dataURL;
	}

	public static void setData(ArrayList<CraftingRecipeData> recipes) {
		CraftingRecipeData.recipes = recipes;
	}

	public void fullConstruct() {
//		System.out.println(name + "\n" + description + "\n" + price + "\n" + ingredients + "\n" + skillType + "\n" + skillLevel + "\n" + salable + "\n----------------");
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

	public boolean isSalable() {
		return salable;
	}

	public static CraftingRecipeData getRecipeData(String name) {
		for (CraftingRecipeData a : recipes)
			if (a.getName().equals(name))
				return a;
		return null;
	}
}
