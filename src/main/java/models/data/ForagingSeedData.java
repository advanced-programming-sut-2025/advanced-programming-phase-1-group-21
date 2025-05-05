package models.data;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;

public class ForagingSeedData implements Data {
	private static final String dataURL = "data/ForagingSeeds.json";
	private static List<ForagingSeedData> cropData = null;

	@SerializedName("name")
	private String name;

	@SerializedName("seasons")
	private List<String> seasons;

	public static void load() {
		Gson gson = new Gson();

		try (FileReader reader = new FileReader(dataURL)) {
			Type listType = new TypeToken<List<ForagingSeedData>>(){}.getType();
			cropData = gson.fromJson(reader, listType);
			for (ForagingSeedData a : cropData) {
				a.fullConstruct();
				System.out.println(a.name);
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

	public List<String> getSeasons() {
		return seasons;
	}

	public static ForagingSeedData getSeedData(String name) {
		for (ForagingSeedData a : cropData)
			if (a.getName().equals(name))
				return a;
		return null;
	}
}
