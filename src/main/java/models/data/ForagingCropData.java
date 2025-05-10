package models.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ForagingCropData implements Data {
	private static final String dataURL = "data/ForagingCrops.json";
	private static ArrayList<ForagingCropData> cropData = null;

	@SerializedName("name")
	private String name;

	@SerializedName("price")
	private int price;

	@SerializedName("is-edible")
	private boolean isEdible;

	@SerializedName("energy")
	private int energy;

	@SerializedName("seasons")
	private ArrayList<String> seasons;

	public static String getDataURL() {
		return dataURL;
	}

	public static void setData(ArrayList<ForagingCropData> cropData) {
		ForagingCropData.cropData = cropData;
	}

	public void fullConstruct() {
//		System.out.println(name + "\n" + price + "\n" + isEdible + "\n" + energy + "\n" + seasons);
	}

	public String getName() {
		return name;
	}

	public int getEnergy() {
		return energy;
	}

	public ArrayList<String> getSeasons() {
		return seasons;
	}

	public int getPrice() {
		return price;
	}

	public boolean isEdible() {
		return isEdible;
	}

	public static ForagingCropData getCropData(String name) {
		for (ForagingCropData a : cropData)
			if (a.getName().equalsIgnoreCase(name))
				return a;
		return null;
	}
}
