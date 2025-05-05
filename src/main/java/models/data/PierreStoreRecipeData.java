package models.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PierreStoreRecipeData implements Data {
	private static final String dataURL = "data/Shops - Pierre's General Store.json";
	private static ArrayList<PierreStoreRecipeData> recipes = null;

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

	protected static String getDataURL() {
		return dataURL;
	}

	protected static void setData(ArrayList<PierreStoreRecipeData> recipes) {
		PierreStoreRecipeData.recipes = recipes;
	}

	public void fullConstruct() {
//		System.out.println(name + "\n" + description + "\n" + required + "\n" + dailyLimit + "\n" + price + "\n-----------");
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
