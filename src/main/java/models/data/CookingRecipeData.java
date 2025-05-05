package models.data;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class CookingRecipeData implements Data {
	private static final String dataURL = "data/CookingRecipes.json";
	private static List<CookingRecipeData> recipes = null;

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

	public static void load() {
		Gson gson = new Gson();

		try (FileReader reader = new FileReader(dataURL)) {
			Type listType = new TypeToken<List<CookingRecipeData>>(){}.getType();
			recipes = gson.fromJson(reader, listType);
			for (CookingRecipeData a : recipes) {
				a.fullConstruct();
				System.out.println(a.name + " " + a.price + " " + a.energy + " " + a.skillType + " " + a.skillLevel + " " + a.source);
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
			if (a.getName().equals(name))
				return a;
		return null;
	}
}
