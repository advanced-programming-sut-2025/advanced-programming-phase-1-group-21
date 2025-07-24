package data.items;

import com.google.gson.annotations.SerializedName;
import data.Data;
import models.Item.ItemType;
import models.time.Season;

import java.util.ArrayList;

public class SeedData implements Data, ItemData {
	private static final String dataURL = "data/Items/Seed.json";
	private static ArrayList<SeedData> seedData = null;

	@SerializedName("name")
	private String name;
	@SerializedName("result")
	private String result;
	@SerializedName("stages")
	private ArrayList<Integer> stages;

	@SerializedName("total-harvest-time")
	private int totalHarvestTime;

	@SerializedName("one-time")
	private boolean oneTime;

	@SerializedName("regrowth-time")
	private int regrowthTime;

	@SerializedName("seasons")
	private ArrayList<String> seasons;
	private ArrayList<Season> cseasons = new ArrayList<>();

	@SerializedName("can-become-giant")
	private boolean canBecomeGiant;

	@SerializedName("stage-textures")
	private ArrayList<String> stageTextures;

	@SerializedName("harvest-texture")
	private String harvestTexture;

	@SerializedName("regrowth-texture")
	private String regrowthTexture;

	public static String getDataURL() {
		return dataURL;
	}

	public static void setData(ArrayList<SeedData> seedData) {
		SeedData.seedData = seedData;
	}

	public void fullConstruct() {
		for (String seasonName : seasons) {
			cseasons.add(Season.valueOf(seasonName.toUpperCase()));
		}
//		System.out.println(name + "\n" + seedName + "\n" + stages + "\n" + totalHarvestTime + "\n" + oneTime + "\n" + regrowthTime + "\n" + price + "\n" + isEdible + "\n" + energy + "\n" + health + "\n" + seasons + "\n" + canBecomeGiant + "\n------------------");
	}

	public String getName() {
		return name;
	}

	public String getResultName() {
		return result;
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

	public boolean isOneTime() {
		return oneTime;
	}

	public int getRegrowthTime() {
		return regrowthTime;
	}

	public ArrayList<Season> getSeasons() {
		return cseasons;
	}

	public boolean canBecomeGiant() {
		return canBecomeGiant;
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

	public String getRegrowthTexture() {
		return regrowthTexture;
	}

	public static SeedData getData(String name) {
		for (SeedData a : seedData)
			if (a.getName().equalsIgnoreCase(name))
				return a;
		return null;
	}

	@Override
	public String toString() {
		String result = "Crop{" +
				"Name: " + this.result + "\n" +
				"Source: " + name + "\n" +
				"stages: ";

		for (int i = 0; i < stages.size(); i++) {
			result += stages.get(i);
			if (i != stages.size() - 1) result += "-";
		}

		result += "\ntotalHarvestTime: " + totalHarvestTime +
				"\noneTime: " + oneTime +
				"\nregrowthTime: " + regrowthTime;
		AllItemsData itemData = AllItemsData.getData(this.result);
		if (itemData == null) {
			throw new NullPointerException("Item " + result + " does not exist!");
		}
		ItemType itemType = itemData.getType();
		if (itemType == ItemType.CONSUMABLE) {
			ConsumableData consumableData = ConsumableData.getData(this.result);
			result += "\nbaseSellPrice: " + consumableData.getPrice() +
					"\nisEdible: true" +
					"\nenergy: " + consumableData.getEnergy();
		}
		else if (itemType == ItemType.SALABLE) {
			SalableData salableData = SalableData.getData(this.result);
			result += "\nbaseSellPrice: " + salableData.getPrice() +
					"\nisEdible: false";
		}
		result += "\nseasons: ";

		for (Season season: cseasons)
			result += season.toString().toLowerCase() + " ";
		result += "\ncanBecomeGiant: " + canBecomeGiant;

		return result;
	}
}
