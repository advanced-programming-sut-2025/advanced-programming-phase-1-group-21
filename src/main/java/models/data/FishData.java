package models.data;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;

public class FishData implements Data {
	private static final String dataURL = "data/Fishes.json";
	private static List<FishData> fishes = null;

	@SerializedName("name")
	private String name;
	@SerializedName("type")
	private String type;
	@SerializedName("season")
	private String season;
	@SerializedName("price")
	private int price;

	public static void load() {
		Gson gson = new Gson();

		try (FileReader reader = new FileReader(dataURL)) {
			Type listType = new TypeToken<List<FishData>>(){}.getType();
			fishes = gson.fromJson(reader, listType);
			for (FishData a : fishes) {
				a.fullConstruct();
				System.out.println(a.name + " " + a.type + " " + a.season + " " + a.price);
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

	public String getType() {
		return type;
	}

	public String getSeason() {
		return season;
	}

	public int getPrice() {
		return price;
	}

	public static FishData getFishData(String name) {
		for (FishData a : fishes)
			if (a.getName().equals(name))
				return a;
		return null;
	}
}
