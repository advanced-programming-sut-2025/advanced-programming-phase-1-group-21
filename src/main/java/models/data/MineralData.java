package models.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MineralData implements Data {
	private static final String dataURL = "data/Minerals.json";
	private static ArrayList<MineralData> mineralData = null;

	@SerializedName("name")
	private String name;

	@SerializedName("description")
	private String description;

	@SerializedName("price")
	private int price;

	protected static String getDataURL() {
		return dataURL;
	}

	protected static void setData(ArrayList<MineralData> mineralData) {
		MineralData.mineralData = mineralData;
	}

	public void fullConstruct() {
//		System.out.println(name + "\n" + description + "\n" + price + "\n---------");
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public int getPrice() {
		return price;
	}

	public static MineralData getMineralData(String name) {
		for (MineralData a : mineralData)
			if (a.getName().equals(name))
				return a;
		return null;
	}
}
