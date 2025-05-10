package models.data.items;

import com.google.gson.annotations.SerializedName;
import models.data.Data;

import java.util.ArrayList;

public class SalableData implements Data, ItemData {
	private static final String dataURL = "data/Items/Salable.json";
	private static ArrayList<SalableData> items = null;

	@SerializedName("name")
	private String name;
	@SerializedName("price")
	private int price;

	public static String getDataURL() {
		return dataURL;
	}

	public static void setData(ArrayList<SalableData> items) {
		SalableData.items = items;
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

	public static SalableData getData(String name) {
		for (SalableData a : items)
			if (a.getName().equalsIgnoreCase(name))
				return a;
		return null;
	}
}
