package models.data;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class BombRecipeData implements Data {
	private static final String dataURL = "data/BombCraftingRecipes.json";
	private static List<BombRecipeData> recipes = null;

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

	public static void load() {
		Gson gson = new Gson();

		try (FileReader reader = new FileReader(dataURL)) {
			Type listType = new TypeToken<List<BombRecipeData>>(){}.getType();
			recipes = gson.fromJson(reader, listType);
			for (BombRecipeData a : recipes) {
				a.fullConstruct();
				System.out.println(a.name + " " + a.description + " " + a.price);
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
			if (a.getName().equals(name))
				return a;
		return null;
	}
}
