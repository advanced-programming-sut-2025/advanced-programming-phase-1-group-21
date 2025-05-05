package models.data;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;

public class ForagingCropData implements Data {
	private static final String dataURL = "data/Crops.json";
	private static List<ForagingCropData> cropData = null;

	@SerializedName("name")
	private String name;

	@SerializedName("price")
	private int price;

	@SerializedName("is-edible")
	private boolean isEdible;

	@SerializedName("energy")
	private int energy;

	@SerializedName("seasons")
	private List<String> seasons;

	public static void load() {
		Gson gson = new Gson();

		try (FileReader reader = new FileReader(dataURL)) {
			Type listType = new TypeToken<List<ForagingCropData>>(){}.getType();
			cropData = gson.fromJson(reader, listType);
			for (ForagingCropData a : cropData) {
				a.fullConstruct();
				System.out.println(a.name + " " + a.price);
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

	public int getEnergy() {
		return energy;
	}

	public List<String> getSeasons() {
		return seasons;
	}

	public int getPrice() {
		return price;
	}

	public boolean isEdible() {
		return isEdible;
	}

	public static ForagingCropData getCropData(String name) {
		for (ForagingCropData a : cropData)
			if (a.getName().equals(name))
				return a;
		return null;
	}
}
