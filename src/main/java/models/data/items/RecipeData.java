package models.data.items;

import com.google.gson.annotations.SerializedName;
import models.Item.Recipe;
import models.Item.RecipeType;
import models.data.Data;

import java.util.ArrayList;
import java.util.Map;

public class RecipeData implements Data, ItemData {
	private static final String cookingDataURL = "data/Items/CookingRecipes.json";
	private static final String craftingDataURL = "data/Items/CraftingRecipes.json";
	private static ArrayList<RecipeData> cookingRecipes = null;
	private static ArrayList<RecipeData> craftingRecipes = null;

	@SerializedName("name")
	private String name;
	@SerializedName("description")
	private String description;
	@SerializedName("result-name")
	private String resultName;
	@SerializedName("ingredients")
	private Map<String, Integer> ingredients;
	@SerializedName("skill-type")
	private String skillType;
	@SerializedName("skill-level")
	private int skillLevel;

	public static String getCookingDataURL() {
		return cookingDataURL;
	}

	public static String getCraftingDataURL() {
		return craftingDataURL;
	}

	public static void setCookingData(ArrayList<RecipeData> recipes) {
		RecipeData.cookingRecipes = recipes;
	}

	public static void setCraftingData(ArrayList<RecipeData> recipes) {
		RecipeData.craftingRecipes = recipes;
	}

	public void fullConstruct() {
//		System.out.println("name: " + name);
//		System.out.println("description: " + description);
//		System.out.println("result-name: " + resultName);
//		System.out.println("ingredients: " + ingredients);
//		System.out.println("skill-type: " + skillType);
//		System.out.println("skill-level: " + skillLevel);
//		System.out.println("-------------------------------");
	}

	@Override
	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getResultName() {
		return resultName;
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

	public static RecipeData getCookingRecipeData(String name) {
		for (RecipeData a : cookingRecipes)
			if (a.getName().equalsIgnoreCase(name))
				return a;
		return null;
	}

	public static RecipeData getCraftingRecipeData(String name) {
		for (RecipeData a : craftingRecipes)
			if (a.getName().equalsIgnoreCase(name))
				return a;
		return null;
	}

	public static RecipeData getRecipeData(String name) {
		for (RecipeData a : cookingRecipes)
			if (a.getName().equalsIgnoreCase(name))
				return a;
		for (RecipeData a : craftingRecipes)
			if (a.getName().equalsIgnoreCase(name))
				return a;
		return null;
	}

	public static Recipe getRecipe(String name, RecipeType type) {
		RecipeData recipeData = (type == RecipeType.COOKING ? getCookingRecipeData(name) : getCraftingRecipeData(name));
		if (recipeData == null) return null;
		return new Recipe(recipeData, type, 1);
	}
}
