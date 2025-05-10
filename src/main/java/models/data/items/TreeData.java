package models.data.items;

import com.google.gson.annotations.SerializedName;
import models.data.Data;

import java.util.ArrayList;

public class TreeData implements Data, ItemData {
	private static final String dataURL = "data/Items/Sapling.json";
	private static ArrayList<TreeData> treeData = null;

	@SerializedName("name")
	private String name;
	@SerializedName("seed-name")
	private String seedName;
	@SerializedName("stages")
	private ArrayList<Integer> stages;

	@SerializedName("total-harvest-time")
	private int totalHarvestTime;

	@SerializedName("fruit")
	private String fruit;

	@SerializedName("harvest-cycle")
	private int harvestCycle;

	@SerializedName("seasons")
	private ArrayList<String> seasons;

	public static String getDataURL() {
		return dataURL;
	}

	public static void setData(ArrayList<TreeData> treeData) {
		TreeData.treeData = treeData;
	}

	public void fullConstruct() {
//		System.out.println(name + "\n" + seedName + "\n" + stages + "\n" + totalHarvestTime + "\n" + fruit + "\n" + harvestCycle + "\n" + price + "\n" + isEdible + "\n" + energy + "\n" + health + "\n" + seasons + "\n---------");
	}

	public String getName() {
		return name;
	}

	public String getSeedName() {
		return seedName;
	}

	public ArrayList<Integer> getStages() {
		return stages;
	}

	public int getTotalHarvestTime() {
		return totalHarvestTime;
	}

	public String getFruit() {
		return fruit;
	}

	public int getHarvestCycle() {
		return harvestCycle;
	}

	public ArrayList<String> getSeasons() {
		return seasons;
	}

	public static TreeData getData(String name) {
		for (TreeData a : treeData)
			if (a.getName().equals(name))
				return a;
		return null;
	}
}
