package models.data;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;

public class TreeData implements Data {
	private static final String dataURL = "data/Trees.json";
	private static List<TreeData> treeData = null;

	@SerializedName("name")
	private String name;
	@SerializedName("seed-name")
	private String seedName;
	@SerializedName("stages")
	private List<Integer> stages;

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
	private List<String> seasons;

	public static void load() {
		Gson gson = new Gson();

		try (FileReader reader = new FileReader(dataURL)) {
			Type listType = new TypeToken<List<TreeData>>(){}.getType();
			treeData = gson.fromJson(reader, listType);
			for (TreeData a : treeData) {
				a.fullConstruct();
				System.out.println(a.name + " " + a.seedName + " " + a.price);
			}
		} catch (Exception e) {
			System.out.println("Can't open " + dataURL);
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

	public List<String> getSeasons() {
		return seasons;
	}

	public static TreeData getTreeData(String name) {
		for (TreeData a : treeData)
			if (a.getName().equals(name))
				return a;
		return null;
	}
}
