package models.data;

import com.google.gson.annotations.SerializedName;
import models.data.items.ItemData;

import java.util.ArrayList;
import java.util.Map;

public class ArtisanGoodsData implements Data, ItemData {
	private static final String dataURL = "data/ArtisanGoods.json";
	private static ArrayList<ArtisanGoodsData> recipes = null;

	@SerializedName("artisan")
	private String artisan;
	@SerializedName("recipes")
	private ArrayList <ArtisanRecipeData> artisanRecipes;

	public static String getDataURL() {
		return dataURL;
	}

	public static void setData(ArrayList<ArtisanGoodsData> recipes) {
		ArtisanGoodsData.recipes = recipes;
	}

	public void printData() {
		System.out.println("----------------------------------");
		System.out.println("artisan: " + artisan);
		System.out.println("recipes: ");
		for (ArtisanRecipeData recipe : artisanRecipes) {
			recipe.printData();
		}
		System.out.println("----------------------------------");
	}

	public void fullConstruct() {
		for (ArtisanRecipeData recipe : artisanRecipes) {
			recipe.fullConstruct();
		}
//		printData();
	}

	public String getName() {
		return artisan;
	}

	public ArrayList<ArtisanRecipeData> getRecipes() {
		return artisanRecipes;
	}

	public static ArtisanGoodsData getRecipeData(String name) {
		for (ArtisanGoodsData a : recipes)
			if (a.getName().equalsIgnoreCase(name))
				return a;
		return null;
	}
}
