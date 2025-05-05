package models.data;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;

public class MixedSeedData implements Data {
	private static final String dataURL = "data/MixedSeeds.json";
	private static List<MixedSeedData> seedData = null;

	@SerializedName("season")
	private String season;

	@SerializedName("possible-seeds")
	private List<String> possibleSeeds;

	public static void load() {
		Gson gson = new Gson();

		try (FileReader reader = new FileReader(dataURL)) {
			Type listType = new TypeToken<List<MixedSeedData>>(){}.getType();
			seedData = gson.fromJson(reader, listType);
			for (MixedSeedData a : seedData) {
				a.fullConstruct();
				System.out.println(a.season);
			}
		} catch (Exception e) {
			System.out.println("Can't open " + dataURL);
			e.printStackTrace();
		}
	}

	private void fullConstruct() {

	}

	public String getSeason() {
		return season;
	}

	public List<String> getPossibleSeeds() {
		return possibleSeeds;
	}

	public static MixedSeedData getSeedData(String season) {
		for (MixedSeedData a : seedData)
			if (a.getSeason().equals(season))
				return a;
		return null;
	}
}
