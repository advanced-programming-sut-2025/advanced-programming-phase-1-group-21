package models.data.items;

import com.google.gson.annotations.SerializedName;
import models.data.Data;

import java.util.ArrayList;

public class SeedData implements Data {
	private static final String dataURL = "data/Items/Seed.json";
	private static ArrayList<SeedData> seedData = null;

	@SerializedName("name")
	private String name;
	@SerializedName("result")
	private String result;
	@SerializedName("stages")
	private ArrayList<Integer> stages;

	@SerializedName("total-harvest-time")
	private int totalHarvestTime;

	@SerializedName("one-time")
	private boolean oneTime;

	@SerializedName("regrowth-time")
	private int regrowthTime;

	@SerializedName("seasons")
	private ArrayList<String> seasons;

	@SerializedName("can-become-giant")
	private boolean canBecomeGiant;

	public static String getDataURL() {
		return dataURL;
	}

	public static void setData(ArrayList<SeedData> seedData) {
		SeedData.seedData = seedData;
	}

	public void fullConstruct() {
//		System.out.println(name + "\n" + seedName + "\n" + stages + "\n" + totalHarvestTime + "\n" + oneTime + "\n" + regrowthTime + "\n" + price + "\n" + isEdible + "\n" + energy + "\n" + health + "\n" + seasons + "\n" + canBecomeGiant + "\n------------------");
	}

	public String getName() {
		return name;
	}

	public String getResultName() {
		return result;
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

	public ArrayList<String> getSeasons() {
		return seasons;
	}

	public boolean isCanBecomeGiant() {
		return canBecomeGiant;
	}

	public static SeedData getSeedData(String name) {
		for (SeedData a : seedData)
			if (a.getName().equals(name))
				return a;
		return null;
	}
}
