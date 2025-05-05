package models.data;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class CraftingRecipeData implements Data {
	private static final String dataURL = "data/CraftingRecipes.json";
	private static List<CraftingRecipeData> recipes = null;

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

	public static void load() {
		Gson gson = new Gson();

		try (FileReader reader = new FileReader(dataURL)) {
			Type listType = new TypeToken<List<CraftingRecipeData>>(){}.getType();
			recipes = gson.fromJson(reader, listType);
			for (CraftingRecipeData a : recipes) {
				a.fullConstruct();
				System.out.println(a.name + " " + a.description + " " + a.salable);
			}
		} catch (Exception e) {
			System.out.println("Can't open Animals.json");
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
