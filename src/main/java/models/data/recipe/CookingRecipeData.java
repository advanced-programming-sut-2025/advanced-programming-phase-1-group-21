package models.data.recipe;

import com.google.gson.annotations.SerializedName;
import models.data.Data;

import java.util.ArrayList;
import java.util.Map;

public class CookingRecipeData implements Data {
	private static final String dataURL = "data/CookingRecipes.json";
	private static ArrayList<CookingRecipeData> recipes = null;

	@SerializedName("name")
	private String name;
	@SerializedName("energy")
	private int energy;
	@SerializedName("price")
	private int price;

	@SerializedName("ingredients")
	private Map<String, Integer> ingredients;

	@SerializedName("buff-type")
	private String buffType;

	@SerializedName("buff-amount")
	private String buffAmount;

	@SerializedName("buff-time")
	private int buffTime;

	@SerializedName("skill-type")
	private String skillType;

	@SerializedName("skill-level")
	private int skillLevel;

	@SerializedName("source")
	private String source;

	public static String getDataURL() {
		return dataURL;
	}

	public static void setData(ArrayList<CookingRecipeData> recipes) {
		CookingRecipeData.recipes = recipes;
	}

	public void fullConstruct() {
//		System.out.println(name + "\n" + energy + "\n" + price + "\n" + ingredients + "\n" + buffType + "\n" + buffAmount + "\n" + buffTime + "\n" + skillType + "\n" + skillLevel + "\n" + source + "\n-------------------");
	}

	public String getName() {
		return name;
	}

	public int getEnergy() {
		return energy;
	}

	public int getPrice() {
		return price;
	}

	public Map<String, Integer> getIngredients() {
		return ingredients;
	}

	public String getBuffType() {
		return buffType;
	}

	public String getBuffAmount() {
		return buffAmount;
	}

	public int getBuffTime() {
		return buffTime;
	}

	public String getSkillType() {
		return skillType;
	}

	public int getSkillLevel() {
		return skillLevel;
	}

	public String getSource() {
		return source;
	}

	public static CookingRecipeData getRecipeData(String name) {
		for (CookingRecipeData a : recipes)
			if (a.getName().equalsIgnoreCase(name))
				return a;
		return null;
	}
}
