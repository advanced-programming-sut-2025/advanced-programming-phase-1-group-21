package models.data;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;

public class ForagingTreeData implements Data {
	private static final String dataURL = "data/ForagingTrees.json";
	private static List<ForagingTreeData> treeData = null;

	@SerializedName("name")
	private String name;

	@SerializedName("seasons")
	private List<String> seasons;

	public static void load() {
		Gson gson = new Gson();

		try (FileReader reader = new FileReader(dataURL)) {
			Type listType = new TypeToken<List<ForagingTreeData>>(){}.getType();
			treeData = gson.fromJson(reader, listType);
			for (ForagingTreeData a : treeData) {
				a.fullConstruct();
				System.out.println(a.name);
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

	public List<String> getSeasons() {
		return seasons;
	}

	public static ForagingTreeData getTreeData(String name) {
		for (ForagingTreeData a : treeData)
			if (a.getName().equals(name))
				return a;
		return null;
	}
}
