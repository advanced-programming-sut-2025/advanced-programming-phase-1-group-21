package data.items;

import com.google.gson.annotations.SerializedName;
import data.Data;

import java.util.ArrayList;

public class PlaceableData implements Data, ItemData {
	private static final String dataURL = "data/Items/Placeable.json";
	private static ArrayList<PlaceableData> items = null;

	@SerializedName("name")
	private String name;
	@SerializedName("location")
	private String location;
	@SerializedName("empty-texture")
	private String emptyTexture;
	@SerializedName("processing-texture")
	private String processingTexture;
	@SerializedName("done-texture")
	private String doneTexture;

	public static String getDataURL() {
		return dataURL;
	}

	public static void setData(ArrayList<PlaceableData> items) {
		PlaceableData.items = items;
	}

	public void fullConstruct() {
//		System.out.println(name + "\n" + type + "\n------------");
	}

	public String getName() {
		return name;
	}

	public String getLocation() {
		return location;
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

	public static PlaceableData getData(String name) {
		for (PlaceableData a : items)
			if (a.getName().equalsIgnoreCase(name))
				return a;
		return null;
	}
}
