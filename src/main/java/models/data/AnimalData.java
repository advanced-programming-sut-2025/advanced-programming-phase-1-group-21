package models.data;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AnimalData implements Data {
	private static final String dataURL = "data/Animals.json";
	private static List<AnimalData> animals = null;

	@SerializedName("name")
	private String name;
	@SerializedName("house")
	private String house;
	@SerializedName("house-type")
	private List<String> houseType;
	@SerializedName("products")
	private List<String> products;
	@SerializedName("product-time")
	private int productTime;
	@SerializedName("product-seasons")
	private List<String> productSeasons;
	@SerializedName("tool")
	private String toolName;
	@SerializedName("price")
	private int price;

	public static void load() {
		Gson gson = new Gson();

		try (FileReader reader = new FileReader(dataURL)) {
			Type listType = new TypeToken<List<AnimalData>>(){}.getType();
			animals = gson.fromJson(reader, listType);
			for (AnimalData a : animals) {
				a.fullConstruct();
//				System.out.println(a.name + " lives in a " + a.house + " and costs " + a.price);
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

	public String getHouse() {
		return house;
	}

	public List<String> getHouseType() {
		return houseType;
	}

	public List<String> getProducts() {
		return products;
	}

	public int getProductTime() {
		return productTime;
	}

	public List<String> getProductSeasons() {
		return productSeasons;
	}

	public String getToolName() {
		return toolName;
	}

	public int getPrice() {
		return price;
	}

	public static AnimalData getAnimalData(String name) {
		for (AnimalData a : animals)
			if (a.getName().equals(name))
				return a;
		return null;
	}
}
