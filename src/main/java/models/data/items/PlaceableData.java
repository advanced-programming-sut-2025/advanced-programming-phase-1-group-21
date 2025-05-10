package models.data.items;

import com.google.gson.annotations.SerializedName;
import models.data.Data;

import java.util.ArrayList;

public class PlaceableData implements Data, ItemData {
	private static final String dataURL = "data/Items/Placeable.json";
	private static ArrayList<PlaceableData> items = null;

	@SerializedName("name")
	private String name;
	@SerializedName("location")
	private String location;

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

	public static PlaceableData getData(String name) {
		for (PlaceableData a : items)
			if (a.getName().equalsIgnoreCase(name))
				return a;
		return null;
	}
}
