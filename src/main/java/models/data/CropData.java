package models.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CropData implements Data {
	private static final String dataURL = "data/Crops.json";
	private static ArrayList<CropData> cropData = null;

	@SerializedName("name")
	private String name;
	@SerializedName("seed-name")
	private String seedName;
	@SerializedName("stages")
	private ArrayList<Integer> stages;

	@SerializedName("total-harvest-time")
	private int totalHarvestTime;

	@SerializedName("one-time")
	private boolean oneTime;

	@SerializedName("regrowth-time")
	private int regrowthTime;

	@SerializedName("price")
	private int price;

	@SerializedName("is-edible")
	private boolean isEdible;

	@SerializedName("energy")
	private int energy;

	@SerializedName("health")
	private int health;

	@SerializedName("seasons")
	private ArrayList<String> seasons;

	@SerializedName("can-become-giant")
	private boolean canBecomeGiant;

	protected static String getDataURL() {
		return dataURL;
	}

	protected static void setData(ArrayList<CropData> cropData) {
		CropData.cropData = cropData;
	}

	public void fullConstruct() {
//		System.out.println(name + "\n" + seedName + "\n" + stages + "\n" + totalHarvestTime + "\n" + oneTime + "\n" + regrowthTime + "\n" + price + "\n" + isEdible + "\n" + energy + "\n" + health + "\n" + seasons + "\n" + canBecomeGiant + "\n------------------");
	}

	public String getName() {
		return name;
	}

	public String getSeedName() {
		return seedName;
	}

	public ArrayList<Integer> getStages() {
		return stages;
	}

	public int getTotalHarvestTime() {
		return totalHarvestTime;
	}

	public boolean getOneTime() {
		return oneTime;
	}

	public int getRegrowthTime() {
		return regrowthTime;
	}

	public int getPrice() {
		return price;
	}

	public boolean isEdible() {
		return isEdible;
	}

	public int getEnergy() {
		return energy;
	}

	public int getHealth() {
		return health;
	}

	public ArrayList<String> getSeasons() {
		return seasons;
	}

	public boolean isCanBecomeGiant() {
		return canBecomeGiant;
	}

	public static CropData getCropData(String name) {
		for (CropData a : cropData)
			if (a.getName().equals(name))
				return a;
		return null;
	}
}
