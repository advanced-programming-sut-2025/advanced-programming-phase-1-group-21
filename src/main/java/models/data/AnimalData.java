package models.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AnimalData implements Data {
	private static final String dataURL = "data/Animals.json";
	private static ArrayList<AnimalData> animals = null;

	@SerializedName("name")
	private String name;
	@SerializedName("house")
	private String house;
	@SerializedName("house-type")
	private ArrayList<String> houseType;
	@SerializedName("products")
	private ArrayList<String> products;
	@SerializedName("product-time")
	private int productTime;
	@SerializedName("product-seasons")
	private ArrayList<String> productSeasons;
	@SerializedName("tool")
	private String toolName;
	@SerializedName("price")
	private int price;

	protected static String getDataURL() {
		return dataURL;
	}

	protected static void setData(ArrayList<AnimalData> animals) {
		AnimalData.animals = animals;
	}

	public void fullConstruct() {

	}

	public String getName() {
		return name;
	}

	public String getHouse() {
		return house;
	}

	public ArrayList<String> getHouseType() {
		return houseType;
	}

	public ArrayList<String> getProducts() {
		return products;
	}

	public int getProductTime() {
		return productTime;
	}

	public ArrayList<String> getProductSeasons() {
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
			if (a.getName().equalsIgnoreCase(name))
				return a;
		return null;
	}
}
