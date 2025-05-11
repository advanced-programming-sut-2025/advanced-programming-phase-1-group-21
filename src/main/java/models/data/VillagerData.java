package models.data;

import com.google.gson.annotations.SerializedName;
import models.time.Season;

import java.util.ArrayList;
import java.util.List;

public class VillagerData implements Data {
	private static final String dataURL = "data/Villagers.json";
	private static ArrayList<VillagerData> data = null;

	@SerializedName("name")
	private String name;
	@SerializedName("favorites")
	private List<String> favorites;
	@SerializedName("tasks")
	private List<VillagerTask> tasks;

	public static String getDataURL() {
		return dataURL;
	}

	public static void setData(ArrayList<VillagerData> data) {
		VillagerData.data = data;
	}

	public void fullConstruct() {
		if (tasks == null) {
			throw new NullPointerException("tasks (in VillagerDate) is null");
		}
		System.out.println("name: " + name);
		System.out.println("favorites: " + favorites);

		for (VillagerTask task : tasks) {
			System.out.println("task: " + task.getRequestItem() + " " + task.getRequestAmount() + " " + task.getRewardItem() + " " + task.getRewardAmount());
		}
	}

	public String getName() {
		return name;
	}

	public List<String> getFavorites() {
		return favorites;
	}

	public List<VillagerTask> getTasks() {
		return tasks;
	}

	public static VillagerData getData(String villagerName) {
		for (VillagerData a : data)
			if (a.getName().equalsIgnoreCase(villagerName))
				return a;
		return null;
	}
}

class VillagerTask {
	@SerializedName("request-item")
	private String requestItem;

	@SerializedName("request-amount")
	private int requestAmount;

	@SerializedName("reward-item")
	private String rewardItem;

	@SerializedName("reward-amount")
	private int rewardAmount;

	public String getRequestItem() {
		return requestItem;
	}

	public int getRequestAmount() {
		return requestAmount;
	}

	public String getRewardItem() {
		return rewardItem;
	}

	public int getRewardAmount() {
		return rewardAmount;
	}


}