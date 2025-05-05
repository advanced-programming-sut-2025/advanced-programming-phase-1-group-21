package models.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MixedSeedData implements Data {
	private static final String dataURL = "data/MixedSeeds.json";
	private static ArrayList<MixedSeedData> seedData = null;

	@SerializedName("season")
	private String season;

	@SerializedName("possible-seeds")
	private ArrayList<String> possibleSeeds;

	protected static String getDataURL() {
		return dataURL;
	}

	protected static void setData(ArrayList<MixedSeedData> seedData) {
		MixedSeedData.seedData = seedData;
	}

	public void fullConstruct() {
//		System.out.println(season + "\n" + possibleSeeds + "\n---------");
	}

	public String getSeason() {
		return season;
	}

	public ArrayList<String> getPossibleSeeds() {
		return possibleSeeds;
	}

	public static MixedSeedData getSeedData(String season) {
		for (MixedSeedData a : seedData)
			if (a.getSeason().equals(season))
				return a;
		return null;
	}
}
