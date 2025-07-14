package data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ScarecrowData implements Data {
	private static final String dataURL = "data/Scarecrows.json";
	private static ArrayList<ScarecrowData> data = null;

	@SerializedName("name")
	private String name;
	@SerializedName("description")
	private String description;
	@SerializedName("radius")
	private int radius;

	public static String getDataURL() {
		return dataURL;
	}

	public static void setData(ArrayList<ScarecrowData> data) {
		ScarecrowData.data = data;
	}

	public void fullConstruct() {
//		System.out.println("name: " + name);
//		System.out.println("description: " + description);
//		System.out.println("radius: " + radius);
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public int getRadius() {
		return radius;
	}

	public static ScarecrowData getData(String name) {
		for (ScarecrowData a : data)
			if (a.getName().equalsIgnoreCase(name))
				return a;
		return null;
	}
}
