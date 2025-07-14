package data.items;

import com.google.gson.annotations.SerializedName;
import data.Data;

import java.util.ArrayList;

public class ConsumableData implements Data, ItemData {
	private static final String dataURL = "data/Items/Consumable.json";
	private static ArrayList<ConsumableData> items = null;

	@SerializedName("name")
	private String name;
	@SerializedName("price")
	private int price;
	@SerializedName("energy")
	private int energy;

	public static String getDataURL() {
		return dataURL;
	}

	public static void setData(ArrayList<ConsumableData> items) {
		ConsumableData.items = items;
	}

	public void fullConstruct() {
//		System.out.println(name + "\n" + type + "\n------------");
	}

	public String getName() {
		return name;
	}

	public int getPrice() {
		return price;
	}

	public int getEnergy() {
		return energy;
	}

	public static ConsumableData getData(String name) {
		for (ConsumableData a : items)
			if (a.getName().equalsIgnoreCase(name))
				return a;
		return null;
	}
}
