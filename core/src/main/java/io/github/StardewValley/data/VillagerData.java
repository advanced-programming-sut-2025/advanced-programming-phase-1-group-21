package io.github.StardewValley.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class VillagerData implements Data {
	private static final String dataURL = "data/Villagers.json";
	private static ArrayList<VillagerData> data = null;

	@SerializedName("name")
	private String name;
	@SerializedName("favorites")
	private ArrayList<String> favorites;
	@SerializedName("tasks")
	private ArrayList<VillagerTask> tasks;

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
//		System.out.println("name: " + name);
//		System.out.println("favorites: " + favorites);
//
//		for (VillagerTask task : tasks) {
//			System.out.println("task: " + task.getRequestItem() + " " + task.getRequestAmount() + " " + task.getRewardItem() + " " + task.getRewardAmount());
//		}
	}

	public String getName() {
		return name;
	}

	public ArrayList<String> getFavorites() {
		return favorites;
	}

	public ArrayList<VillagerTask> getTasks() {
		return tasks;
	}

	public static VillagerData getData(String villagerName) {
		for (VillagerData a : data)
			if (a.getName().equalsIgnoreCase(villagerName))
				return a;
		return null;
	}
}
