package models.data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DataLoader {
	public static void load() {
		ItemData.setData(loadEach(ItemData.getDataURL(), new TypeToken<List<ItemData>>(){}.getType()));
		AnimalData.setData(loadEach(AnimalData.getDataURL(), new TypeToken<List<AnimalData>>(){}.getType()));
		AnimalHouseData.setData(loadEach(AnimalHouseData.getDataURL(), new TypeToken<List<AnimalHouseData>>(){}.getType()));
		BlackSmithRecipeData.setData(loadEach(BlackSmithRecipeData.getDataURL(), new TypeToken<List<BlackSmithRecipeData>>(){}.getType()));
		BombRecipeData.setData(loadEach(BombRecipeData.getDataURL(), new TypeToken<List<BombRecipeData>>(){}.getType()));
		CarpenterRecipeData.setData(loadEach(CarpenterRecipeData.getDataURL(), new TypeToken<List<CarpenterRecipeData>>(){}.getType()));
		CookingRecipeData.setData(loadEach(CookingRecipeData.getDataURL(), new TypeToken<List<CookingRecipeData>>(){}.getType()));
		CraftingRecipeData.setData(loadEach(CraftingRecipeData.getDataURL(), new TypeToken<List<CraftingRecipeData>>(){}.getType()));
		CropData.setData(loadEach(CropData.getDataURL(), new TypeToken<List<CropData>>(){}.getType()));
		FishData.setData(loadEach(FishData.getDataURL(), new TypeToken<List<FishData>>(){}.getType()));
		FishShopRecipeData.setData(loadEach(FishShopRecipeData.getDataURL(), new TypeToken<List<FishShopRecipeData>>(){}.getType()));
		ForagingCropData.setData(loadEach(ForagingCropData.getDataURL(), new TypeToken<List<ForagingCropData>>(){}.getType()));
		ForagingSeedData.setData(loadEach(ForagingSeedData.getDataURL(), new TypeToken<List<ForagingSeedData>>(){}.getType()));
		ForagingTreeData.setData(loadEach(ForagingTreeData.getDataURL(), new TypeToken<List<ForagingTreeData>>(){}.getType()));
		JojaMartRecipeData.setData(loadEach(JojaMartRecipeData.getDataURL(), new TypeToken<List<JojaMartRecipeData>>(){}.getType()));
		MarniesRanchRecipeData.setData(loadEach(MarniesRanchRecipeData.getDataURL(), new TypeToken<List<MarniesRanchRecipeData>>(){}.getType()));
		MineralData.setData(loadEach(MineralData.getDataURL(), new TypeToken<List<MineralData>>(){}.getType()));
		MixedSeedData.setData(loadEach(MixedSeedData.getDataURL(), new TypeToken<List<MixedSeedData>>(){}.getType()));
		PierreStoreRecipeData.setData(loadEach(PierreStoreRecipeData.getDataURL(), new TypeToken<List<PierreStoreRecipeData>>(){}.getType()));
		ScarecrowRecipeData.setData(loadEach(ScarecrowRecipeData.getDataURL(), new TypeToken<List<ScarecrowRecipeData>>(){}.getType()));
		SprinklerRecipeData.setData(loadEach(SprinklerRecipeData.getDataURL(), new TypeToken<List<SprinklerRecipeData>>(){}.getType()));
		StardropSaloonRecipeData.setData(loadEach(StardropSaloonRecipeData.getDataURL(), new TypeToken<List<StardropSaloonRecipeData>>(){}.getType()));
		TreeData.setData(loadEach(TreeData.getDataURL(), new TypeToken<List<TreeData>>(){}.getType()));

	}

	public static <T extends Data> ArrayList<T> loadEach(String URL, Type typeOfT) {
		Gson gson = new Gson();
		ArrayList<T> data = null;

		try (FileReader reader = new FileReader(URL)) {
			data = gson.fromJson(reader, typeOfT);
			for (T a : data) {
				a.fullConstruct();
			}
		} catch (Exception e) {
			System.out.println("Can't open Animals.json");
			e.printStackTrace();
		}

		return data;
	}
}
