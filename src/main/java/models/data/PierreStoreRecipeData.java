package models.data;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PierreStoreRecipeData implements Data {
	private static final String dataURL = "data/Shops - Pierre's General Store.json";
	private static List<PierreStoreRecipeData> recipes = null;

	@SerializedName("name")
	private String name;
	@SerializedName("description")
	private String description;
	@SerializedName("price")
	private ArrayList <Integer> price;
	@SerializedName("required")
	private String required;
	@SerializedName("daily-limit")
	private int dailyLimit;

	public static void load() {
		Gson gson = new Gson();

		try (FileReader reader = new FileReader(dataURL)) {
			Type listType = new TypeToken<List<PierreStoreRecipeData>>(){}.getType();
			recipes = gson.fromJson(reader, listType);
			for (PierreStoreRecipeData a : recipes) {
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

	public ArrayList<Integer> getPrice() {
		return price;
	}

	public String getRequired() {
		return required;
	}

	public int getDailyLimit() {
		return dailyLimit;
	}


	public static PierreStoreRecipeData getRecipeData(String name) {
		for (PierreStoreRecipeData a : recipes)
			if (a.getName().equals(name))
				return a;
		return null;
	}
}
