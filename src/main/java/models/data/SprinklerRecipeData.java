package models.data;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SprinklerRecipeData implements Data {
	private static final String dataURL = "data/SprinklerCraftingRecipes.json";
	private static List<SprinklerRecipeData> recipes = null;

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

	public static void load() {
		Gson gson = new Gson();

		try (FileReader reader = new FileReader(dataURL)) {
			Type listType = new TypeToken<List<SprinklerRecipeData>>(){}.getType();
			recipes = gson.fromJson(reader, listType);
			for (SprinklerRecipeData a : recipes) {
				a.fullConstruct();
				System.out.println(a.name + " " + a.description + " " + a.skillType + " " + a.skillLevel);
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
			if (a.getName().equals(name))
				return a;
		return null;
	}
}
