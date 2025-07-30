package data;

import com.google.gson.annotations.SerializedName;
import data.items.ItemData;

import java.util.ArrayList;

public class ArtisanGoodsData implements Data, ItemData {
	private static final String dataURL = "data/ArtisanGoods.json";
	private static ArrayList<ArtisanGoodsData> recipes = null;

	@SerializedName("artisan")
	private String artisan;
	@SerializedName("recipes")
	private ArrayList <ArtisanRecipeData> artisanRecipes;
	@SerializedName("empty-texture")
	private String emptyTexture;
	@SerializedName("processing-texture")
	private String processingTexture;
	@SerializedName("done-texture")
	private String doneTexture;


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

	public String getEmptyTexture() {
		return emptyTexture;
	}

	public String getProcessingTexture() {
		return processingTexture;
	}

	public String getDoneTexture() {
		return doneTexture;
	}

	public static ArtisanGoodsData getRecipeData(String name) {
		for (ArtisanGoodsData a : recipes)
			if (a.getName().equalsIgnoreCase(name))
				return a;
		return null;
	}
}
