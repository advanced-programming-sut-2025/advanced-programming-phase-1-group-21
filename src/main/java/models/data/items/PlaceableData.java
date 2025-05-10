package models.data.items;

import com.google.gson.annotations.SerializedName;
import models.data.Data;

import java.util.ArrayList;

public class PlaceableData implements Data {
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

	public static PlaceableData getItemData(String name) {
		for (PlaceableData a : items)
			if (a.getName().equals(name))
				return a;
		return null;
	}
}
