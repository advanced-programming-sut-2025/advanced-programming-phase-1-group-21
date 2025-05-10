package models.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FishData implements Data {
	private static final String dataURL = "data/Fishes.json";
	private static ArrayList<FishData> fishes = null;

	@SerializedName("name")
	private String name;
	@SerializedName("type")
	private String type;
	@SerializedName("season")
	private String season;
	@SerializedName("price")
	private int price;

	public static String getDataURL() {
		return dataURL;
	}

	public static void setData(ArrayList<FishData> fishes) {
		FishData.fishes = fishes;
	}

	public void fullConstruct() {
//		System.out.println(name + " " + type + " " + season + " " + price);
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public String getSeason() {
		return season;
	}

	public int getPrice() {
		return price;
	}

	public static FishData getFishData(String name) {
		for (FishData a : fishes)
			if (a.getName().equals(name))
				return a;
		return null;
	}
}
