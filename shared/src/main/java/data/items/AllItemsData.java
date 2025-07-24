package data.items;

import com.google.gson.annotations.SerializedName;
import data.Data;
import models.Item.ItemType;

import java.util.ArrayList;

public class AllItemsData implements Data {
	private static final String dataURL = "data/Items/Items.json";
	private static ArrayList<AllItemsData> items = null;

	@SerializedName("name")
	private String name;
	@SerializedName("type")
	private String type;
	private ItemType ctype;
	@SerializedName("texture-address")
	private String textureAddress;

	public static String getDataURL() {
		return dataURL;
	}

	public static void setData(ArrayList<AllItemsData> items) {
		AllItemsData.items = items;
	}

	public void fullConstruct() {
		ctype = ItemType.valueOf(type.toUpperCase());
//		System.out.println(name + "\n" + ctype + "\n------------");
	}

	public String getName() {
		return name;
	}

	public ItemType getType() {
		return ctype;
	}

	public String getTextureAddress() {
		return textureAddress;
	}

	public static AllItemsData getData(String name) {
		for (AllItemsData a : items)
			if (a.getName().equalsIgnoreCase(name))
				return a;
		return null;
	}

	@Override
	public String toString() {
		return name + " (" + ctype + ")";
	}
}
