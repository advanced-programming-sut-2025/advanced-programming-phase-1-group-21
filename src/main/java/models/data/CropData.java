package models.data;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;

public class CropData implements Data {
	private static final String dataURL = "data/Crops.json";
	private static List<CropData> cropData = null;

	@SerializedName("name")
	private String name;
	@SerializedName("seed-name")
	private String seedName;
	@SerializedName("stages")
	private List<Integer> stages;

	@SerializedName("total-harvest-time")
	private int totalHarvestTime;

	@SerializedName("one-time")
	private boolean oneTime;

	@SerializedName("regrowth-time")
	private int regrowthTime;

	@SerializedName("price")
	private int price;

	@SerializedName("is-edible")
	private boolean isEdible;

	@SerializedName("energy")
	private int energy;

	@SerializedName("health")
	private int health;

	@SerializedName("seasons")
	private List<String> seasons;

	@SerializedName("can-become-giant")
	private boolean canBecomeGiant;

	public static void load() {
		Gson gson = new Gson();

		try (FileReader reader = new FileReader(dataURL)) {
			Type listType = new TypeToken<List<CropData>>(){}.getType();
			cropData = gson.fromJson(reader, listType);
			for (CropData a : cropData) {
				a.fullConstruct();
				System.out.println(a.name + " " + a.seedName + " " + a.price);
			}
		} catch (Exception e) {
			System.out.println("Can't open Animals.json");
			e.printStackTrace();
		}
	}

	private void fullConstruct() {

	}

	public String getName() {
		return name;
	}

	public String getSeedName() {
		return seedName;
	}

	public List<Integer> getStages() {
		return stages;
	}

	public int getTotalHarvestTime() {
		return totalHarvestTime;
	}

	public boolean getOneTime() {
		return oneTime;
	}

	public int getRegrowthTime() {
		return regrowthTime;
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

	public List<String> getSeasons() {
		return seasons;
	}

	public boolean isCanBecomeGiant() {
		return canBecomeGiant;
	}

	public static CropData getCropData(String name) {
		for (CropData a : cropData)
			if (a.getName().equals(name))
				return a;
		return null;
	}
}
