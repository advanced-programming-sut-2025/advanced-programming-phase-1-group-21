package data.items;

import com.google.gson.annotations.SerializedName;
import data.Data;
import models.Item.ItemType;
import models.time.Season;

import java.util.ArrayList;

public class TreeData implements Data, ItemData {
	private static final String dataURL = "data/Items/Sapling.json";
	private static ArrayList<TreeData> treeData = null;

	@SerializedName("name")
	private String name;
	@SerializedName("sapling-name")
	private String saplingName;
	@SerializedName("stages")
	private ArrayList<Integer> stages;

	@SerializedName("total-harvest-time")
	private int totalHarvestTime;

	@SerializedName("result")
	private String result;

	@SerializedName("harvest-cycle")
	private int harvestCycle;

	@SerializedName("seasons")
	private ArrayList<String> seasons;
	private ArrayList<Season> cseasons;

	@SerializedName("stage-textures")
	private ArrayList<String> stageTextures;

	@SerializedName("harvest-texture")
	private String harvestTexture;

	@SerializedName("season-texture")
	private ArrayList<String> seasonTexture;

	public static String getDataURL() {
		return dataURL;
	}

	public static void setData(ArrayList<TreeData> treeData) {
		TreeData.treeData = treeData;
	}

	public void fullConstruct() {
		cseasons = new ArrayList<>();
		for (String season : seasons)
			cseasons.add(Season.valueOf(season.toUpperCase()));
//		System.out.println("name: " + name);
//		System.out.println("sapling-name: " + saplingName);
//		System.out.println("stages: " + stages);
//		System.out.println("total-harvest-time: " + totalHarvestTime);
//		System.out.println("result: " + result);
//		System.out.println("harvest-cycle: " + harvestCycle);
//		System.out.println("cseasons: " + cseasons);
	}

	public String getName() {
		return name;
	}

	public String getSaplingName() {
		return saplingName;
	}

	public ArrayList<Integer> getStages() {
		return stages;
	}

	public int getStage(int day) {
		int sum = 0, result = 0;
		for (int stage: stages) {
			sum += stage;
			if (sum < day)
				result++;
		}
		return result;
	}

	public int getTotalHarvestTime() {
		return totalHarvestTime;
	}

	public String getResultName() {
		return result;
	}

	public int getHarvestCycle() {
		return harvestCycle;
	}

	public ArrayList<String> getSeasons() {
		return seasons;
	}

	public ArrayList<String> getStageTextures() {
		return stageTextures;
	}

	public String getStageTexture(int stage) {
		return stageTextures.get(stage);
	}

	public String getHarvestTexture() {
		return harvestTexture;
	}

	public ArrayList<String> getSeasonTexture() {
		return seasonTexture;
	}

	public static TreeData getData(String name) {
		for (TreeData a : treeData)
			if (a.getName().equalsIgnoreCase(name) || a.getSaplingName().equalsIgnoreCase(name))
				return a;
		return null;
	}

	public static TreeData getData(int id) {
		if (id < 0 || id >= treeData.size()) {
			return null;
		}
		return treeData.get(id);
	}

	@Override
	public String toString() {
		String stringResult = "Crop{" +
				"Tree Name: " + name + "\n" +
				"Sapling Name: " + saplingName + "\n" +
				"stages: ";

		for (int i = 0; i < stages.size(); i++) {
			stringResult += stages.get(i);
			if (i != stages.size() - 1) stringResult += "-";
		}

		stringResult += "\nTotal Harvest Time: " + totalHarvestTime +
				"\nHarvest Cycle: " + harvestCycle;
		AllItemsData itemData = AllItemsData.getData(this.result);
		if (itemData == null) {
			throw new NullPointerException("Item " + this.result + " does not exist!");
		}
		ItemType itemType = itemData.getType();
		if (itemType == ItemType.CONSUMABLE) {
			ConsumableData consumableData = ConsumableData.getData(this.result);
			stringResult += "\nbaseSellPrice: " + consumableData.getPrice() +
					"\nisEdible: true" +
					"\nenergy: " + consumableData.getEnergy();
		}
		else if (itemType == ItemType.SALABLE) {
			SalableData salableData = SalableData.getData(this.result);
			stringResult += "\nbaseSellPrice: " + salableData.getPrice() +
					"\nisEdible: false";
		}

		stringResult += "\nseasons: ";
		for (Season season: cseasons)
			stringResult += season.toString().toLowerCase() + " ";

		return stringResult;
	}

	public static int getSize() {
		return treeData.size();
	}
}
