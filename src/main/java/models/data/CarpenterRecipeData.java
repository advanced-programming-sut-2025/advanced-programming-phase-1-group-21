package models.data;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class CarpenterRecipeData implements Data {
	private static final String dataURL = "data/Shops - Carpenter's Shop.json";
	private static List<CarpenterRecipeData> recipes = null;

	@SerializedName("name")
	private String name;
	@SerializedName("description")
	private String description;
	@SerializedName("ingredient")
	private Map<String, Integer> ingredient;
	@SerializedName("required")
	private String required;
	@SerializedName("price")
	private int price;
	@SerializedName("daily-limit")
	private int dailyLimit;

	public static void load() {
		Gson gson = new Gson();

		try (FileReader reader = new FileReader(dataURL)) {
			Type listType = new TypeToken<List<CarpenterRecipeData>>(){}.getType();
			recipes = gson.fromJson(reader, listType);
			for (CarpenterRecipeData a : recipes) {
				a.fullConstruct();
				System.out.println(a.name + " " + a.price + " " + a.description + " " + a.dailyLimit);
				if (a.ingredient != null) {
					System.out.println("Ingredients: " + a.ingredient.size());
					for (Map.Entry<String, Integer> entry : a.ingredient.entrySet()) {
						System.out.println(entry.getKey() + " " + entry.getValue());
					}
				}
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

	public Map<String, Integer> getIngredient() {
		return ingredient;
	}

	public String getRequired() {
		return required;
	}

	public int getPrice() {
		return price;
	}

	public int getDailyLimit() {
		return dailyLimit;
	}

	public static CarpenterRecipeData getRecipeData(String name) {
		for (CarpenterRecipeData a : recipes)
			if (a.getName().equals(name))
				return a;
		return null;
	}
}
