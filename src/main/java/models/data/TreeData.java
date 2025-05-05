package models.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TreeData implements Data {
	private static final String dataURL = "data/Trees.json";
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

	@SerializedName("price")
	private int price;

	@SerializedName("is-edible")
	private boolean isEdible;

	@SerializedName("energy")
	private int energy;

	@SerializedName("health")
	private int health;

	@SerializedName("seasons")
	private ArrayList<String> seasons;

	protected static String getDataURL() {
		return dataURL;
	}

	protected static void setData(ArrayList<TreeData> treeData) {
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

	public int getPrice() {
		return price;
	}

	public boolean isEdible() {
		return isEdible;
	}

	public int getEnergy() {
		return energy;
	}

	public int getHealth() {
		return health;
	}

	public ArrayList<String> getSeasons() {
		return seasons;
	}

	public static TreeData getTreeData(String name) {
		for (TreeData a : treeData)
			if (a.getName().equals(name))
				return a;
		return null;
	}
}
