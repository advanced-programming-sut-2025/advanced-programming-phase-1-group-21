package data;

import com.google.gson.annotations.SerializedName;
import models.time.Season;

import java.util.ArrayList;

public class ForagingCropData implements Data {
	private static final String dataURL = "data/ForagingCrops.json";
	private static ArrayList<ForagingCropData> cropData = null;

	@SerializedName("name")
	private String name;

	@SerializedName("seasons")
	private ArrayList<String> seasons;
	private ArrayList<Season> cseasons;

	public static String getDataURL() {
		return dataURL;
	}

	public static void setData(ArrayList<ForagingCropData> cropData) {
		ForagingCropData.cropData = cropData;
	}

	public void fullConstruct() {
		cseasons = new ArrayList<>();
		for (String seasonName : seasons)
			cseasons.add(Season.valueOf(seasonName.toUpperCase()));
//		System.out.println(name + "\n" + price + "\n" + isEdible + "\n" + energy + "\n" + seasons);
	}

	public String getName() {
		return name;
	}

	public ArrayList<Season> getSeasons() {
		return cseasons;
	}

	public static ForagingCropData getData(String name) {
		for (ForagingCropData a : cropData)
			if (a.getName().equalsIgnoreCase(name))
				return a;
		return null;
	}

	public static ForagingCropData getData(int id) {
		return cropData.get(id);
	}

	public static int getSize() {
		return cropData.size();
	}
}
