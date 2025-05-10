package models.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ForagingSeedData implements Data {
	private static final String dataURL = "data/ForagingSeeds.json";
	private static ArrayList<ForagingSeedData> seedData = null;

	@SerializedName("name")
	private String name;

	@SerializedName("seasons")
	private ArrayList<String> seasons;

	public static String getDataURL() {
		return dataURL;
	}

	public static void setData(ArrayList<ForagingSeedData> seedData) {
		ForagingSeedData.seedData = seedData;
	}

	public void fullConstruct() {
//		System.out.println(name + "\n" + seasons + "\n----------");
	}

	public String getName() {
		return name;
	}

	public ArrayList<String> getSeasons() {
		return seasons;
	}

	public static ForagingSeedData getSeedData(String name) {
		for (ForagingSeedData a : seedData)
			if (a.getName().equalsIgnoreCase(name))
				return a;
		return null;
	}
}
