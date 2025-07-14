package io.github.StardewValley.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ForagingTreeData implements Data {
	private static final String dataURL = "data/ForagingTrees.json";
	private static ArrayList<ForagingTreeData> treeData = null;

	@SerializedName("name")
	private String saplingName;

	@SerializedName("seasons")
	private ArrayList<String> seasons;

	protected static String getDataURL() {
		return dataURL;
	}

	protected static void setData(ArrayList<ForagingTreeData> treeData) {
		ForagingTreeData.treeData = treeData;
	}

	public void fullConstruct() {
//		System.out.println(name + "\n" + seasons + "\n----------");
	}

	public String getSaplingName() {
		return saplingName;
	}

	public ArrayList<String> getSeasons() {
		return seasons;
	}

	public static ForagingTreeData getTreeData(String name) {
		for (ForagingTreeData a : treeData)
			if (a.getSaplingName().equalsIgnoreCase(name))
				return a;
		return null;
	}
}
