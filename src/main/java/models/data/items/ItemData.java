package models.data.items;

import com.google.gson.annotations.SerializedName;
import models.data.Data;
import models.game.ItemType;

import java.util.ArrayList;

public class ItemData implements Data {
	private static final String dataURL = "data/Items/Items.json";
	private static ArrayList<ItemData> items = null;

	@SerializedName("name")
	private String name;
	@SerializedName("type")
	private String type;
	private ItemType ctype;

	public static String getDataURL() {
		return dataURL;
	}

	public static void setData(ArrayList<ItemData> items) {
		ItemData.items = items;
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

	public static ItemData getItemData(String name) {
		for (ItemData a : items)
			if (a.getName().equals(name))
				return a;
		return null;
	}
}
