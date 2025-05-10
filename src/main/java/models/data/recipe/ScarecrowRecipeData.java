package models.data.recipe;

import com.google.gson.annotations.SerializedName;
import models.data.Data;

import java.util.ArrayList;
import java.util.Map;

public class ScarecrowRecipeData implements Data {
	private static final String dataURL = "data/ScarecrowCraftingRecipes.json";
	private static ArrayList<ScarecrowRecipeData> recipes = null;

	@SerializedName("name")
	private String name;
	@SerializedName("description")
	private String description;

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

	public static void setData(ArrayList<ScarecrowRecipeData> recipes) {
		ScarecrowRecipeData.recipes = recipes;
	}

	public void fullConstruct() {
//		System.out.println(name + "\n" + description + "\n" + ingredients + "\n" + skillType + "\n" + skillLevel + "\n" + radius + "\n------------");
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

	public int getRadius() {
		return radius;
	}

	public static ScarecrowRecipeData getRecipeData(String name) {
		for (ScarecrowRecipeData a : recipes)
			if (a.getName().equalsIgnoreCase(name))
				return a;
		return null;
	}
}
