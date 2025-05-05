package models.data;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;

public class StardropSaloonRecipeData implements Data {
	private static final String dataURL = "data/Shops - The Stardrop Saloon.json";
	private static List<StardropSaloonRecipeData> recipes = null;

	@SerializedName("name")
	private String name;
	@SerializedName("description")
	private String description;
	@SerializedName("price")
	private int price;
	@SerializedName("daily-limit")
	private int dailyLimit;

	public static void load() {
		Gson gson = new Gson();

		try (FileReader reader = new FileReader(dataURL)) {
			Type listType = new TypeToken<List<StardropSaloonRecipeData>>(){}.getType();
			recipes = gson.fromJson(reader, listType);
			for (StardropSaloonRecipeData a : recipes) {
				a.fullConstruct();
				System.out.println(a.name + " " + a.price + " " + a.description + " " + a.dailyLimit);
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

	public int getDailyLimit() {
		return dailyLimit;
	}

	public static StardropSaloonRecipeData getRecipeData(String name) {
		for (StardropSaloonRecipeData a : recipes)
			if (a.getName().equals(name))
				return a;
		return null;
	}
}
