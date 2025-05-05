package models.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ItemData implements Data {
	private static final String dataURL = "data/Items.json";
	private static ArrayList<ItemData> items = null;

	@SerializedName("name")
	private String name;
	@SerializedName("type")
	private String type;

	protected static String getDataURL() {
		return dataURL;
	}

	protected static void setData(ArrayList<ItemData> items) {
		ItemData.items = items;
	}

	public void fullConstruct() {
//		System.out.println(name + "\n" + type + "\n------------");
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public static ItemData getRecipeData(String name) {
		for (ItemData a : items)
			if (a.getName().equals(name))
				return a;
		return null;
	}
}
