package models.data;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;

public class MineralData implements Data {
	private static final String dataURL = "data/Minerals.json";
	private static List<MineralData> mineralData = null;

	@SerializedName("name")
	private String name;

	@SerializedName("description")
	private String description;

	@SerializedName("price")
	private int price;

	public static void load() {
		Gson gson = new Gson();

		try (FileReader reader = new FileReader(dataURL)) {
			Type listType = new TypeToken<List<MineralData>>(){}.getType();
			mineralData = gson.fromJson(reader, listType);
			for (MineralData a : mineralData) {
				a.fullConstruct();
				System.out.println(a.name + " " + a.price + " " + a.description);
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

	public String getDescription() {
		return description;
	}

	public int getPrice() {
		return price;
	}

	public static MineralData getMineralData(String name) {
		for (MineralData a : mineralData)
			if (a.getName().equals(name))
				return a;
		return null;
	}
}
